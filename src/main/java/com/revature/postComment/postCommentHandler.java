package com.revature.postComment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;

public class postCommentHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Handler method for Post Comment lambda. Handles the request it's given and returns an appropriate response
     *
     * @param requestEvent - the proxy event from AWS API Gateway
     * @param context - the context of the request
     * @return - a response with a 400 status code for invalid requests, a 201 status code and the saved item for valid requests
     *
     * @author - Luna Haines
     */
    @SneakyThrows
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {

        LambdaLogger logger = context.getLogger();
        PostCommentService service = new PostCommentService(logger);
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        Comment input = mapper.fromJson(requestEvent.getBody(), Comment.class);

        boolean saved = service.addComment(input);

        if (saved) {
            logger.log("saved to database: " + input.toString());

            response.setStatusCode(201);

            return response;
        } else {
            logger.log("failed to save requested item to database");
            response.setStatusCode(400);

            return response;
        }
    }

}
