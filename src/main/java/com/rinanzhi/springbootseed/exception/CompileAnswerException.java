package com.rinanzhi.springbootseed.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompileAnswerException extends Exception {

    public CompileAnswerException(String message) {
        super(message);
    }
}
