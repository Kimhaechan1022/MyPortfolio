package com.myportfolio.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User{

    @Id
    private String userId;

    private String password;

    private String userName;

    private String birth;

    private String phone;

    private String email;


}
