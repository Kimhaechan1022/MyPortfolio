package com.myportfolio.user.controller;


import com.myportfolio.user.model.RegisterInput;
import com.myportfolio.user.model.User;
import com.myportfolio.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class RegisterController {


    private final UserRepository userRepository;

    @PostMapping("/user/register")
    public String  registerSubmit(RegisterInput registerInput){

        User newUser = User.builder()
                .userId(registerInput.getUserId())
                .password(registerInput.getPassword())
                .userName(registerInput.getUserName())
                .birth(registerInput.getBirth())
                .phone(registerInput.getPhone())
                .email(registerInput.getEmail())
                .build();
        userRepository.save(newUser);

        return "user/register";
    }

}
