package com.myportfolio.personalpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class personalPageController {

    @GetMapping("/personal/home")
    public String personalHome() {
        return "/personal/home";
    }

    @GetMapping("/personalPage/introduction")
    public String introduction(){
        return "personal/introductionConfigPage";
    }
}
