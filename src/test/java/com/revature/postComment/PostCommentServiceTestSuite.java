package com.revature.postComment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;

public class PostCommentServiceTestSuite {

    PostCommentService sut;
    NodeRepository mockNodeRepo;

    @BeforeEach
    public void caseSetUp() {
        mockNodeRepo = mock(NodeRepository.class);
        sut = new PostCommentService(mockNodeRepo);
    }

    @AfterEach
    public void caseCleanUp() {
        sut = null;
        reset(mockNodeRepo);
    }


}
