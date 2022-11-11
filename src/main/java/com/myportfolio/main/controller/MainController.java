package com.myportfolio.main.controller;

import com.myportfolio.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MailComponents mailComponents;


    @GetMapping("/")
    public String index(){
        return "user/startPage";
    }

    @GetMapping("/user/test")
    @ResponseBody
    public String testSecurity(){
        return "test";
    }


}
