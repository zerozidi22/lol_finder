package com.example.watcher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class admin {

    @Id
    @Column
    private long seq;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String auth;


}
