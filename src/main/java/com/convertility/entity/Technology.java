package com.convertility.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Technology {
    @Id
    private int id;
    private String name;
}
