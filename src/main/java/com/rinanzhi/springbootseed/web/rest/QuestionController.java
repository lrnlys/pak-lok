package com.rinanzhi.springbootseed.web.rest;

import com.rinanzhi.springbootseed.entity.Question;
import com.rinanzhi.springbootseed.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@RestController
@RequestMapping("/api/question")
public class QuestionController extends CrudController<Question, Long, QuestionService> {

    @GetMapping("/exam/{examId}")
    private List<Question> findByExamId(@PathVariable("examId") Long examId) {
        try {
            return service.findByExamId(examId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getMessage(e));
        }
    }
}
