package com.myportfolio.user.service;

import com.myportfolio.user.model.RegisterInput;

public interface UserService {
    boolean register(RegisterInput registerInput);
}
