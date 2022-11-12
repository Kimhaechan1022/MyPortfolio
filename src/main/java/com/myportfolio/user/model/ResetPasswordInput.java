package com.myportfolio.user.model;

import lombok.Data;

@Data
public class ResetPasswordInput {
    private String userId;
    private String userName;
    private String resetPasswordKey;
    private String password;
    private String RePassword;

}
