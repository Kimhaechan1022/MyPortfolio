package com.myportfolio.user.service;

import com.myportfolio.user.model.RegisterInput;
import com.myportfolio.user.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean register(RegisterInput registerInput);
    boolean emailAuthentication(String emailAuthenticationKey);

    boolean sendResetPassword(ResetPasswordInput resetPasswordInput);
}
