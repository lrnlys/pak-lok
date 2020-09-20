package com.rinanzhi.springbootseed.service;

import com.rinanzhi.springbootseed.entity.Exam;
import com.rinanzhi.springbootseed.entity.Question;
import com.rinanzhi.springbootseed.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService extends CrudService<Question, Long, QuestionRepository> {

    @Autowired
    private ExamService examService;

    public List<Question> findByExamId(Long examId) {
        Optional<Exam> examOptional = examService.findById(examId);
        return repository.findAllById(null);
    }
}
