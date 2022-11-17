package com.myportfolio.personalpage.service.impl;


import com.myportfolio.personalpage.model.AddPortfolioInput;
import com.myportfolio.personalpage.model.Portfolio;
import com.myportfolio.personalpage.repository.PortfolioRepository;
import com.myportfolio.personalpage.service.PersonalPortfolioService;
import com.myportfolio.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PersonalPortfolioServiceImpl implements PersonalPortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public boolean isAnyPortfolioExist() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        List<Optional<Portfolio>> optionalList = portfolioRepository.findAllByUserId(currentUserId);
        if(optionalList.size()<=0){
            return false;
        }
        return true;
    }

    @Override
    public List<HashMap<String, Object>> getPersonalPortfolioList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        List<Optional<Portfolio>> optionalList = portfolioRepository.findAllByUserId(currentUserId);
        if(optionalList.size()<=0){
            throw new UserNotFoundException("userId Not Found");
        }


        List<HashMap<String, Object>> resultMapList = new ArrayList<HashMap<String, Object>>();

        for(Optional<Portfolio> optionalPortfolio: optionalList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("userId",optionalPortfolio.get().getUserId());
            map.put("portfolioId",optionalPortfolio.get().getPortfolioId());
            map.put("title",optionalPortfolio.get().getTitle());
            map.put("content",optionalPortfolio.get().getContent());
            map.put("fileName",optionalPortfolio.get().getFileName());
            resultMapList.add(map);
        }

        return resultMapList;
    }

    @Override
    public void deleteAll() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        List<Optional<Portfolio>> optionalList = portfolioRepository.findAllByUserId(currentUserId);
        if(optionalList.size()<=0){
            throw new UserNotFoundException("userId Not Found");
        }


        List<HashMap<String, Object>> resultMapList = new ArrayList<HashMap<String, Object>>();

        for(Optional<Portfolio> optionalPortfolio: optionalList) {
            Portfolio delTarget = optionalPortfolio.get();
            portfolioRepository.delete(delTarget);
        }

    }

    @Override
    public void addPortfolio(AddPortfolioInput addPortfolioInput, MultipartFile file) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        String portfolioId = UUID.randomUUID().toString();

        Portfolio newPortfolio = Portfolio.builder()
                .userId(currentUserId)
                .content(addPortfolioInput.getContent())
                .title(addPortfolioInput.getTitle())
                .portfolioId(portfolioId)
                .fileName("/userPortfolio/" + currentUserId + portfolioId + "portfolio.png")
                .build();

        portfolioRepository.save(newPortfolio);


        if(file!= null){
            String filePathPrefix = "C:/javaPJ/MyPortfolio/src/main/resources/static/userPortfolio/";
            String fileName = currentUserId + portfolioId + "portfolio.png";
            try {
                File userProfile = new File(filePathPrefix + fileName);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(userProfile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
