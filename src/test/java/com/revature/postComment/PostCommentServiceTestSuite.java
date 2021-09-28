package com.revature.postComment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class PostCommentServiceTestSuite {

    PostCommentService sut;

    @BeforeEach
    public void caseSetUp() {
        sut = new PostCommentService();
    }

    @AfterEach
    public void caseCleanUp() {
        sut = null;
    }
}
