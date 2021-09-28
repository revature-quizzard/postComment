package com.revature.postComment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void addComment_returnsSuccessfully_givenValidInformation() {
        // Arrange
        Comment mockComment = Comment.builder()
                                     .id("12345")
                                     .parent("threadId")
                                     .ancestors(new ArrayList<>(Arrays.asList("subforumId","threadId")))
                                     .description("Description goes here")
                                     .owner("user")
                                     .build();
        Comment mockParentThread = Comment.builder()
                                          .id("threadId")
                                          .parent("subforumId")
                                          .ancestors(new ArrayList<>(Arrays.asList("subforumId")))
                                          .description("Thread description")
                                          .child_count(2)
                                          .date_created(LocalDateTime.now().minusDays(12).toString())
                                          .subject("Core Java")
                                          .owner("user2")
                                          .build();
        doNothing().when(mockNodeRepo).addComment(any());
        when(mockNodeRepo.getThread(any())).thenReturn(mockParentThread);
        doNothing().when(mockNodeRepo).updateChild_count(any());

        // Act
        boolean result = sut.addComment(mockComment);

        // Assert
        verify(mockNodeRepo, times(1)).getThread("threadId");
        verify(mockNodeRepo, times(1)).addComment(any());
        verify(mockNodeRepo, times(1)).updateChild_count(any());
        assertEquals(mockParentThread.getChild_count(), 3);
        assertNotNull(mockComment.getDate_created());
        assertTrue(result);

    }

    @Test
    public void addComment_returnsFalse_whenGivenNullComment() {
        // Act
        boolean result = sut.addComment(null);

        // Assert
        verify(mockNodeRepo, times(0)).addComment(any());
        verify(mockNodeRepo, times(0)).updateChild_count(any());
        verify(mockNodeRepo, times(0)).getThread(any());
        assertFalse(result);

    }

    @Test
    public void addComment_returnsFalse_whenGivenInvalidFields() {
        // Arrange
        Comment mockComment = Comment.builder()
                                     .id("12345")
                                     .parent("threadId")
                                     .ancestors(new ArrayList<>(Arrays.asList("subforumId","threadId")))
                                     .owner("")
                                     .build();

        // Act
        boolean result = sut.addComment(mockComment);

        // Assert
        verify(mockNodeRepo, times(0)).addComment(any());
        verify(mockNodeRepo, times(0)).updateChild_count(any());
        verify(mockNodeRepo, times(0)).getThread(any());
        assertFalse(result);

    }

    @Test
    public void addComment_returnsFalse_whenGivenInvalidParent() {
        // Arrange
        Comment mockComment = Comment.builder()
                                     .id("12345")
                                     .parent("threadId")
                                     .ancestors(new ArrayList<>(Arrays.asList("subforumId","threadId")))
                                     .description("Description goes here")
                                     .owner("user")
                                     .build();
        when(mockNodeRepo.getThread(any())).thenReturn(null);

        // Act
        boolean result = sut.addComment(mockComment);

        // Assert
        verify(mockNodeRepo, times(0)).addComment(any());
        verify(mockNodeRepo, times(0)).updateChild_count(any());
        verify(mockNodeRepo, times(1)).getThread(any());
        assertFalse(result);

    }


}
