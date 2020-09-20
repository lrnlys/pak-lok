package com.rinanzhi.springbootseed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResult {

    private Boolean isCorrect = false;
    private String message;
}
