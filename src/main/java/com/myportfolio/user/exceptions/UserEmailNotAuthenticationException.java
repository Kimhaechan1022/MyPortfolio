package com.myportfolio.user.exceptions;

public class UserEmailNotAuthenticationException extends RuntimeException{
    public UserEmailNotAuthenticationException(String msg){
        super(msg);
    }
}
