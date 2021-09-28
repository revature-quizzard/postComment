package com.revature.postComment;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

public class NodeRepository {

    private final DynamoDBMapper dbMapper;

    public NodeRepository() {
        this.dbMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public void addComment(Comment comment) {
        dbMapper.save(comment);
    }

    public Comment getThread(String id) {
        Comment queryItem = Comment.builder().id(id).build();

        return dbMapper.query(Comment.class,new DynamoDBQueryExpression<Comment>().withHashKeyValues(queryItem)).get(0);
    }

    public void updateChild_count(Comment thread) {
        dbMapper.save(thread);
    }
}
