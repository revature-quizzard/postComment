package com.revature.postComment;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.time.LocalDateTime;

public class PostCommentService {

    private NodeRepository nodeRepo;
    private LambdaLogger logger;

    public PostCommentService(LambdaLogger logger) {
        this.nodeRepo = new NodeRepository();
        this.logger = logger;
    }

    // used to mack NodeRepository in test suite
    public PostCommentService(NodeRepository nodeRepo, LambdaLogger logger) {
        this.nodeRepo = nodeRepo;
        this.logger = logger;
    }

    /**
     * This method takes in a comment and attempts to save that comment to the database.
     *
     * @param comment - the comment being added to the database
     * @return - boolean indicating whether or not the comment was valid / saved
     * @author - Luna Haines
     */
    public boolean addComment(Comment comment) {
        boolean valid;
        if (comment == null) {
            logger.log("no request body provided");
            return false;
        }

        comment.setDate_created(LocalDateTime.now().toString());

        valid = isValid(comment);

        if (!valid) { return false; }

        Comment parentThread = nodeRepo.getThread(comment.getParent());
        if (parentThread == null) {
            logger.log("the specified parent thread doesn't  exist");
            valid = false;
        }
        if (valid) {
            nodeRepo.addComment(comment);
            parentThread.setChild_count(parentThread.getChild_count() + 1);
            nodeRepo.updateChild_count(parentThread);
        }
        return valid;
    }

    /**
     * This method checks to see that a comment is valid.
     *
     * @param comment - the comment to check validation on
     * @return - boolean indicating whether or not a comment is valid
     * @author - Luna Haines
     */
    public boolean isValid(Comment comment) {
        LocalDateTime commentDate = LocalDateTime.parse(comment.getDate_created());

        // if any required fields are null, empty, or of an invalid size
        if (comment == null || comment.getDescription() == null || comment.getDescription().trim().equals("") ||
                comment.getOwner() == null || comment.getOwner().trim().equals("") || comment.getAncestors() == null ||
                comment.getAncestors().size() != 2 || comment.getParent() == null ||
                comment.getParent().trim().equals("")) {
            logger.log("provided Comment contains a null or empty required field or an invalid number or ancestors");
            return false;
        }

        return true;
    }

}
