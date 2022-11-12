package com.myportfolio.user.controller;


import com.myportfolio.user.model.ResetPasswordInput;
import com.myportfolio.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final UserService userService;

    @RequestMapping("/user/login")
    public String loginPage() {
        return "user/login";
    }

    @GetMapping("/user/findPassword")
    public String findPassword() {
        return "user/find_password";
    }

    @PostMapping("/user/findPassword")
    public String findPasswordSubmit(Model model, ResetPasswordInput resetPasswordInput) {

        boolean isPasswordResetSuccess = false;

        try {
            isPasswordResetSuccess = userService.sendResetPassword(resetPasswordInput);
        } catch (Exception e) {
        }
        model.addAttribute("isPasswordResetSuccess", isPasswordResetSuccess);
        return "user/find_password_result";
    }

    @GetMapping("user/resetPassword")
    public String resetPassword(Model model, HttpServletRequest request) {

        String resetPasswordKey = request.getParameter("resetPasswordKey");
        model.addAttribute("resetPasswordKey", resetPasswordKey);

        return "user/resetPassword";
    }

    @PostMapping("user/resetPassword")
    public String resetPasswordSubmit(Model model, ResetPasswordInput resetPasswordInput) {

        String key = resetPasswordInput.getResetPasswordKey();
        boolean isPasswordResetSuccess = false;
        try {
            isPasswordResetSuccess = userService.resetPassword(resetPasswordInput.getResetPasswordKey(),
                    resetPasswordInput.getPassword(),
                    resetPasswordInput.getRePassword());
        }catch (Exception e){
            model.addAttribute("errorMassage",e.getMessage());
        }
        model.addAttribute("isPasswordResetSuccess",isPasswordResetSuccess);

        return "user/resetPassword_result";
    }
}
