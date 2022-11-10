package com.myportfolio.user.service;

import com.myportfolio.user.model.RegisterInput;
import com.myportfolio.user.model.User;
import com.myportfolio.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    @Override
    public boolean register(RegisterInput registerInput) {

        Optional<User> optionalUser = userRepository.findById(registerInput.getUserId());
        if(optionalUser.isPresent()){
            return false;
        }

        User newUser = User.builder()
                .userId(registerInput.getUserId())
                .password(registerInput.getPassword())
                .userName(registerInput.getUserName())
                .birth(registerInput.getBirth())
                .phone(registerInput.getPhone())
                .email(registerInput.getEmail())
                .build();
        userRepository.save(newUser);
        return true;
    }
}
