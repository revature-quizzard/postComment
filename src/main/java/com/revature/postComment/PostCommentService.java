package com.revature.postComment;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class PostCommentService {

    private NodeRepository nodeRepo;

    public PostCommentService() {
        this.nodeRepo = new NodeRepository();
    }

    public PostCommentService(NodeRepository nodeRepo) {
        this.nodeRepo = nodeRepo;
    }

    public boolean addComment(Comment comment) {
        boolean valid;
        try {
            if (comment.getDate_created().length() != 26 || comment.getDate_created().equals(LocalDateTime.MIN.toString())) {
                comment.setDate_created(LocalDateTime.now().toString());
            }
            valid = isValid(comment);
        } catch (Exception e) {
            e.printStackTrace();
            valid = false;
        }
        Comment parentThread = nodeRepo.getThread(comment.getParent());
        if (parentThread == null) {
            valid = false;
        }
        if (valid) {
            nodeRepo.addComment(comment);
        }
        return valid;
    }

    public boolean isValid(Comment comment) {
        LocalDateTime commentDate = LocalDateTime.parse(comment.getDate_created());

        // if any required fields are null, empty, or of an invalid size
        if (comment == null || comment.getDescription() == null || comment.getDescription().trim().equals("") ||
                comment.getOwner() == null || comment.getOwner().trim().equals("") || comment.getAncestors() == null ||
                comment.getAncestors().size() != 2 || comment.getParent() == null ||
                comment.getParent().trim().equals("")) {
            return false;
        }
        // if comment isn't new (within ten minutes of .now)
        if (commentDate.isBefore(LocalDateTime.now().minusMinutes(10))) {
            return false;
        }
        return true;
    }

}
