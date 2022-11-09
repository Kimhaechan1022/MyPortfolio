package com.myportfolio;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @PostMapping("/user/register")
    public String  registerSubmit(){
        System.out.println("plz...");
        return "user/register";
    }
}
