package com.myportfolio.personalpage.service;

import com.myportfolio.personalpage.model.EditIntroductionInput;

import java.util.HashMap;

public interface PersonalIntroductionService {

    boolean isExistAnyPersonalIntroduction(String userId);
    void initPersonalIntroduction(String userId);

    HashMap<String,Object> getPersonalIntroductionData(String userId);

    void editPersonalIntroduction(String userId, EditIntroductionInput editIntroductionInput);

}
