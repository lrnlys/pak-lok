package com.rinanzhi.springbootseed.configuration;

import com.rinanzhi.springbootseed.enums.CategoryType;
import com.rinanzhi.springbootseed.enums.QuestionType;
import com.rinanzhi.springbootseed.service.validator.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.transaction.NotSupportedException;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public AnswerValidator answerValidator(CategoryType categoryType, QuestionType questionType) throws NotSupportedException {
        switch (questionType) {
            case CHOICE:
                return new ChoiceAnswerValidator();
            case MULTIPLE_CHOICE:
                return new MultipleChoiceAnswerValidator();
            case PROGRAM:
                return this.getProgramValidator(categoryType);
            default:
                throw new NotSupportedException();
        }
    }

    AnswerValidator getProgramValidator(CategoryType categoryType) throws NotSupportedException {
        switch (categoryType) {
            case JAVA:
                return new JavaProgramAnswerValidator();
            case JAVASCRIPT:
                return new JavascriptProgramAnswerValidator();
            case PYTHON:
                return new PythonProgramAnswerValidator();
            default:
                throw new NotSupportedException();
        }
    }
}
