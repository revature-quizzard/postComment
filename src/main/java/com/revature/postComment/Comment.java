package com.revature.postComment;

import lombok.Data;

import java.util.List;

@Data
public class Comment {

    private String id;
    private int date_created;
    private List<String> ancestors;
    private String description;
    private String parent;
    private String subject;

}
