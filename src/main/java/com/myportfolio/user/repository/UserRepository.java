package com.myportfolio.user.repository;

import com.myportfolio.user.model.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInformation, String> {
    Optional<UserInformation> findByEmailAuthenticationKey(String emailAuthenticationKey);
    Optional<UserInformation> findByUserIdAndUserName(String userId, String userName);
    Optional<UserInformation> findByResetPasswordKey(String resetPasswordKey);
}
