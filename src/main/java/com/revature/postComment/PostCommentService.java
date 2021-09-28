package com.revature.postComment;

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
        if (comment == null) { return false; }

        comment.setDate_created(LocalDateTime.now().toString());

        valid = isValid(comment);

        if (!valid) { return false; }

        Comment parentThread = nodeRepo.getThread(comment.getParent());
        if (parentThread == null) {
            valid = false;
        }
        if (valid) {
            nodeRepo.addComment(comment);
            parentThread.setChild_count(parentThread.getChild_count() + 1);
            nodeRepo.updateChild_count(parentThread);
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

        return true;
    }

}
