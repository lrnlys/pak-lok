package com.rinanzhi.springbootseed.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
abstract public class BaseEntity<PRIMARY_KEY> implements Serializable {

    private static final long serialVersionUID = 1L;

    abstract public PRIMARY_KEY getPrimaryKey();

    abstract public void setPrimaryKey(PRIMARY_KEY primaryKey);
}
