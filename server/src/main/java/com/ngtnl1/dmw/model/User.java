package com.ngtnl1.dmw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class User extends BaseModel {
    @Column(unique = true, nullable = false)
    private String email = "";
    @Column(nullable = false)
    private String name = "";
    @Column(nullable = false)
    private String password = "";
}
