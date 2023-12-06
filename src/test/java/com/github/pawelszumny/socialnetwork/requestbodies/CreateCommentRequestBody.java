package com.github.pawelszumny.socialnetwork.requestbodies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequestBody {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;
}
