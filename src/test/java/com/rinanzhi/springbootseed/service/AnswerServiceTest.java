package com.rinanzhi.springbootseed.service;

import com.rinanzhi.springbootseed.entity.Answer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AnswerServiceTest {

    @Autowired
    private AnswerService service;

    @Test
    public void answerChoiceQuestionTest() {
        assertTrue(service.answer(Answer.builder().questionId(1l).answer("A").build()).getIsCorrect());
    }

    @Test
    public void answerJavaProgramQuestionTest() {
        assertTrue(service.answer(Answer.builder().questionId(2l).answer("public class TestQuestion{ public String answer(){return \"Hello world\";}}").build()).getIsCorrect());
    }
}
