package com.myportfolio.user.controller;


import com.myportfolio.user.model.RegisterInput;
import com.myportfolio.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class RegisterController {


    private final UserService userService;

    @GetMapping("/user/register")
    public String registerPage(){
        return "user/register";
    }

    @PostMapping("/user/register")
    public String registerSubmit(Model model, RegisterInput registerInput) {

        boolean isRegisterSuccessFull = userService.register(registerInput);
        model.addAttribute("isRegisterSuccessFull", isRegisterSuccessFull);
        return "user/register_success";
    }
    @GetMapping("/user/email_auth")
    public String emailAuthentication(Model model,HttpServletRequest request){
        String emailAuthenticationKey = request.getParameter("key");
        boolean isSuccessFull = userService.emailAuthentication(emailAuthenticationKey);

        model.addAttribute("isSuccessFull", isSuccessFull);

        return "user/email_auth";
    }

}
