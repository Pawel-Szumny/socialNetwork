package com.github.pawelszumny.socialnetwork.requestbodies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPostRequestBody {
    private String body;
}
