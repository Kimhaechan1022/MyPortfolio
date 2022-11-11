package com.myportfolio.user.model;

import lombok.Data;

@Data
public class ResetPasswordInput {
    private String userId;
    private String userName;
}
