package com.revature.postComment;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

public class NodeRepository {

    private final DynamoDBMapper dbmapper;

    public NodeRepository() {
        this.dbmapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public void addComment(Comment comment) {
        dbmapper.save(comment);
    }

    public Comment getThread(String id) {
        Comment queryItem = Comment.builder().id(id).build();

        return dbmapper.query(Comment.class,new DynamoDBQueryExpression<Comment>().withHashKeyValues(queryItem)).get(0);
    }
}
