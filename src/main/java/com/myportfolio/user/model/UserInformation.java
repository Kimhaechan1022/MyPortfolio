package com.myportfolio.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserInformation {

    @Id
    private String userId;

    private String password;

    private String userName;

    private String birth;

    private String phone;

    private String email;

    private boolean isEmailAuthentication;
    private String emailAuthenticationKey;


}
