package com.myportfolio.user.model;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterInput {

    private String userId;

    private String password;

    private String userName;

    private String birth;

    private String phone;

    private String email;
}
