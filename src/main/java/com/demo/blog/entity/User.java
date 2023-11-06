package com.demo.blog.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserTable")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    private String about;

}
