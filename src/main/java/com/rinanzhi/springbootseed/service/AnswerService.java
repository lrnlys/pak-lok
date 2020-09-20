package com.rinanzhi.springbootseed.service;


import com.rinanzhi.springbootseed.entity.Answer;
import com.rinanzhi.springbootseed.entity.Question;
import com.rinanzhi.springbootseed.model.AnswerResult;
import com.rinanzhi.springbootseed.service.validator.AnswerValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class AnswerService {

    private ObjectProvider<AnswerValidator> answerValidatorProvider;
    private QuestionService questionService;

    public AnswerService(ObjectProvider<AnswerValidator> answerValidatorProvider, QuestionService questionService) {
        this.answerValidatorProvider = answerValidatorProvider;
        this.questionService = questionService;
    }

    private Question getQuestionById(Long id) {
        Optional<Question> questionOptional = questionService.findById(id);
        if (!questionOptional.isPresent()) {
            throw new RuntimeException("Question do not exist");
        }
        return questionOptional.get();
    }

    public AnswerResult answer(Answer answer) {
        Question question = this.getQuestionById(answer.getQuestionId());
        AnswerValidator validator = answerValidatorProvider.getObject(question.getCategoryType(), question.getQuestionType());
        return validator.validate(question.getId(), question.getAnswer(), answer.getAnswer());
    }

}
