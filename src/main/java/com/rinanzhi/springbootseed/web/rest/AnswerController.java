package com.rinanzhi.springbootseed.web.rest;


import com.rinanzhi.springbootseed.entity.Answer;
import com.rinanzhi.springbootseed.model.AnswerResult;
import com.rinanzhi.springbootseed.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    private AnswerService service;

    @GetMapping("/question")
    private AnswerResult answer(@RequestBody Answer answer) {
        return service.answer(answer);
    }

}
