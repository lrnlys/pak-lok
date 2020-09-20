package com.rinanzhi.springbootseed.domain.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Pageable;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindAllRequest<CONDITIONS> {
    private CONDITIONS conditions;
    private Pageable pageable;
}
