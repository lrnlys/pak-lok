package com.rinanzhi.springbootseed.service;

import com.rinanzhi.springbootseed.entity.Exam;
import com.rinanzhi.springbootseed.repository.ExamRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamService extends CrudService<Exam, Long, ExamRepository> {
}
