package com.myportfolio.user.controller;


import com.myportfolio.user.model.ResetPasswordInput;
import com.myportfolio.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final UserService userService;

    @RequestMapping("/user/login")
    public String loginPage(){
        return "user/login";
    }

    @GetMapping("/user/findPassword")
    public String findPassword(){
        System.out.println("paswdasf");
        return "user/find_password";
    }
    @PostMapping
    public String findPasswordSubmit(Model model, ResetPasswordInput resetPasswordInput){
        boolean isPasswordResetSuccess = userService.sendResetPassword(resetPasswordInput);
        model.addAttribute("isPasswordResetSuccess", isPasswordResetSuccess);
        return "user/find_password_result";
    }
}
