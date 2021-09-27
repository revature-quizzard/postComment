package com.revature.postComment;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class NodeRepository {

    private final DynamoDBMapper dbmapper;

    public NodeRepository() {
        this.dbmapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public void addComment(Comment comment) {
        dbmapper.save(comment);
    }
}
