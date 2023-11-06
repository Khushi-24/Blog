package com.demo.blog.common.entity;

import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public class BaseEntity {

    private Date createdTimeStamp;

    private Date updatedTimeStamp;

    private Date deletedTimeStamp;
}
