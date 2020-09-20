package com.rinanzhi.springbootseed.service.validator;

import com.rinanzhi.springbootseed.model.AnswerResult;

abstract public class AnswerValidator {

    protected String getAnswer(Long questionId, String answer) {
        return answer;
    }

    public AnswerResult validate(Long questionId, String excepted, String answer) {
        String actual = this.getAnswer(questionId, answer);
        boolean correct = false;
        String message = "";
        try {
            correct = this.doValidate(excepted, actual);
        } catch (Exception e) {
            message = e.getMessage();
        }
        return AnswerResult.builder().isCorrect(correct).message(message).build();
    }

    protected boolean doValidate(String excepted, String actual) {
        return excepted.equals(actual);
    }

}
