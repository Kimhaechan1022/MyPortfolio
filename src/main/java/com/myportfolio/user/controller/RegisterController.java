package com.myportfolio.user.controller;


import com.myportfolio.user.model.RegisterInput;
import com.myportfolio.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class RegisterController {


    private final UserService userService;

    @PostMapping("/user/register")
    public String registerSubmit(Model model, RegisterInput registerInput) {

        boolean isRegisterSuccessFull = userService.register(registerInput);
        model.addAttribute("isRegisterSuccessFull", isRegisterSuccessFull);

        return "user/register_success";
    }

}
