package com.myportfolio.user.exceptions;

public class PasswordNotSameException extends RuntimeException{
    public PasswordNotSameException(String msg){
        super(msg);
    }
}

