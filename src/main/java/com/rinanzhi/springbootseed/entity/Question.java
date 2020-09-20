package com.rinanzhi.springbootseed.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rinanzhi.springbootseed.enums.CategoryType;
import com.rinanzhi.springbootseed.enums.QuestionType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"hibernateLazyInitializer", "handler"})
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@DynamicUpdate
@Table(name = "PL_QUESTION")
public class Question extends AuditEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private CategoryType categoryType;
    private QuestionType questionType;
    private String question;
    private String answer;


    @Override
    public Long getPrimaryKey() {
        return id;
    }

    @Override
    public void setPrimaryKey(Long id) {
        this.id = id;
    }
}
