package com.myportfolio.user.service;

import com.myportfolio.user.model.RegisterInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean register(RegisterInput registerInput);
    boolean emailAuthentication(String emailAuthenticationKey);
}
