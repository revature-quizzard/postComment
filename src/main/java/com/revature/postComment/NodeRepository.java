package com.revature.postComment;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

public class NodeRepository {

    private final DynamoDBMapper dbMapper;

    public NodeRepository() {
        this.dbMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    /**
     * This method saves a comment to DynamoDB.
     *
     * @param comment - the comment to be saved
     *
     * @author - Luna Haines
     */
    public void addComment(Comment comment) {
        dbMapper.save(comment);
    }

    /**
     * This method queries DynamoDB to find a Thread by id.
     *
     * @param id - the id of the thread being found
     * @return - the Thread that was found (as a Comment, because the have similar shape)
     *
     * @author - Luna Haines
     */
    public Comment getThread(String id) {
        Comment queryItem = Comment.builder().id(id).build();

        return dbMapper.query(Comment.class,new DynamoDBQueryExpression<Comment>().withHashKeyValues(queryItem)).get(0);
    }

    /**
     * Saves a thread after its child_count has been incremented.
     *
     * @param thread - the thread to be updated (including all of the thread's fields and their values)
     *
     * @author - Luna Haines
     */
    public void updateChild_count(Comment thread) {
        dbMapper.save(thread);
    }
}
