package com.rinanzhi.springbootseed.service;

import com.rinanzhi.springbootseed.entity.Question;
import com.rinanzhi.springbootseed.enums.CategoryType;
import com.rinanzhi.springbootseed.enums.QuestionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class QuestionServiceTest {

    @Autowired
    private QuestionService service;

    @Test
    public void insertChoiceQuestionTest() {
        Question question = Question.builder().question("Do Java Choice").
                categoryType(CategoryType.JAVA).questionType(QuestionType.CHOICE).answer("A").build();
        service.save(question);
    }

    @Test
    public void insertJavaProgramQuestionTest() {
        Question question = Question.builder().question("Do Java Program").
                categoryType(CategoryType.JAVA).questionType(QuestionType.PROGRAM).answer("Hello world").build();
        service.save(question);
    }

    @Test
    public void findAllTest() {
        System.out.println(service.findAll());
    }

    @Test
    public void deleteAllTest() {
        service.deleteAll();
    }
}
