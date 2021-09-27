package com.revature.postComment;

import java.time.LocalDateTime;

public class PostCommentService {

    private NodeRepository nodeRepo = new NodeRepository();

    public boolean addComment(Comment comment) {
        if (comment.getDate_created().equals(null) || comment.getDate_created().length() != 26) {
            comment.setDate_created(LocalDateTime.now().toString());
        }
        boolean valid = isValid(comment);
        if (valid) {
            nodeRepo.addComment(comment);
        }
        return valid;
    }

    public boolean isValid(Comment comment) {
        LocalDateTime commentDate = LocalDateTime.parse(comment.getDate_created());

        if (commentDate.isBefore(LocalDateTime.now().minusMinutes(10))) {
            // if comment isn't new (within ten minutes of .now)
            return false;
        }
        return true;
    }

}
