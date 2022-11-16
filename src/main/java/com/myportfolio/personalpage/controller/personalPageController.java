package com.myportfolio.personalpage.controller;

import com.myportfolio.personalpage.model.EditIntroductionInput;
import com.myportfolio.personalpage.service.PersonalIntroductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;


@RequiredArgsConstructor
@Controller
public class personalPageController {

    private final PersonalIntroductionService personalIntroductionService;

    @GetMapping("/personal/home")
    public String personalHome(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        boolean isExistAnyPersonalIntroductionData = personalIntroductionService.isExistAnyPersonalIntroduction(currentUserId);
        if (!isExistAnyPersonalIntroductionData) {
            personalIntroductionService.initPersonalIntroduction(currentUserId);
        }

        String filePathPrefix = "/userprofile/";
        String fileName = currentUserId + "profile.png";

        model.addAttribute("filepath",filePathPrefix + fileName);

        HashMap<String,Object> IntroductionData = personalIntroductionService.getPersonalIntroductionData(currentUserId);
        model.addAttribute("introduction",IntroductionData);
        HashMap<String,Object> emailAndPhone = personalIntroductionService.getEmailAndPhone(currentUserId);
        model.addAttribute("emailandphone",emailAndPhone);


        return "/personal/home";
    }
    @GetMapping("/personalPage/profile")
    public String profile(){
        return "personal/profileConfigPage";
    }

    @PostMapping ("/personalPage/profile")
    public String editProfile(Model model, MultipartFile file){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        if(file!= null){
            String filePathPrefix = "C:/javaPJ/MyPortfolio/src/main/resources/static/userprofile/";
            String fileName = currentUserId + "profile.png";
            try {
                File userProfile = new File(filePathPrefix + fileName);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(userProfile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/personal/home";

    }

    @GetMapping("/personalPage/introduction")
    public String introduction() {
        return "personal/introductionConfigPage";
    }


    @PostMapping("/personalPage/introduction")
    public String editIntroduction(EditIntroductionInput editIntroductionInput) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        personalIntroductionService.editPersonalIntroduction(currentUserId, editIntroductionInput);

        return "redirect:/personal/home";
    }

}
