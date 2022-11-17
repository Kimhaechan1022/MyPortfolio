package com.myportfolio.personalpage.controller;

import com.myportfolio.personalpage.model.AddPortfolioInput;
import com.myportfolio.personalpage.model.EditIntroductionInput;
import com.myportfolio.personalpage.model.Portfolio;
import com.myportfolio.personalpage.service.PersonalIntroductionService;
import com.myportfolio.personalpage.service.PersonalPortfolioService;
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
import java.util.List;


@RequiredArgsConstructor
@Controller
public class PersonalPageController {

    private final PersonalIntroductionService personalIntroductionService;
    private final PersonalPortfolioService personalPortfolioService;

    @GetMapping("/personal/home")
    public String personalHome(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        boolean isExistAnyPersonalIntroductionData = personalIntroductionService.isExistAnyPersonalIntroduction(currentUserId);
        if (!isExistAnyPersonalIntroductionData) {
            personalIntroductionService.initPersonalIntroduction(currentUserId);
        }

        List<HashMap<String,Object>> portfolioList = personalPortfolioService.getPersonalPortfolioList();

        HashMap<String,Object> IntroductionData = personalIntroductionService.getPersonalIntroductionData(currentUserId);
        HashMap<String,Object> emailAndPhone = personalIntroductionService.getEmailAndPhone(currentUserId);

        String filePathPrefix = "/userprofile/";
        String fileName = currentUserId + "profile.png";

        model.addAttribute("filepath",filePathPrefix + fileName);
        model.addAttribute("introduction",IntroductionData);
        model.addAttribute("emailandphone",emailAndPhone);
        model.addAttribute("portfolioList", portfolioList);



        return "/personal/home";
    }


    @GetMapping("/personal/deletePortfolio")
    public String deleteAllPortfolio(Model model) {
        personalPortfolioService.deleteAll();
        return "redirect:/personalPage/portfolio";
    }

    @GetMapping("/personal/addPortfolio")
    public String addPortfolio(){
        return "personal/addPortfolio";
    }
    @PostMapping("/personal/addPortfolio")
    public String addPortfolio2(AddPortfolioInput addPortfolioInput, MultipartFile file){



        personalPortfolioService.addPortfolio(addPortfolioInput,file);


        return "redirect:/personalPage/portfolio";
    }


    @GetMapping("/personalPage/portfolio")
    public String portfolio(Model model){


        if(personalPortfolioService.isAnyPortfolioExist()){
            model.addAttribute("isExist",true);

            model.addAttribute("portfolioList",personalPortfolioService.getPersonalPortfolioList());}
        else{
            model.addAttribute("isExist",false);
        }

        return "personal/portfolio";
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
