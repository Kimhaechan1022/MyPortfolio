package com.myportfolio.personalpage.service;

import com.myportfolio.personalpage.model.AddPortfolioInput;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface PersonalPortfolioService {
    boolean isAnyPortfolioExist();
    List<HashMap<String, Object>> getPersonalPortfolioList();

    void deleteAll();

    void addPortfolio(AddPortfolioInput addPortfolioInput, MultipartFile file);


}
