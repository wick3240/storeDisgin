package com.wick.store.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
    private String id;
    private String name;
    private String password;
}
