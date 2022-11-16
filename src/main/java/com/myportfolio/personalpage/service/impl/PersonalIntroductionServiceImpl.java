package com.myportfolio.personalpage.service.impl;

import com.myportfolio.personalpage.model.EditIntroductionInput;
import com.myportfolio.personalpage.model.PersonalIntroduction;
import com.myportfolio.personalpage.repository.PersonalIntroductionRepository;
import com.myportfolio.personalpage.service.PersonalIntroductionService;
import com.myportfolio.user.exceptions.UserNotFoundException;
import com.myportfolio.user.model.UserInformation;
import com.myportfolio.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalIntroductionServiceImpl implements PersonalIntroductionService {

    private final PersonalIntroductionRepository personalIntroductionRepository;

    private final UserRepository userRepository;

    @Override
    public HashMap<String, Object> getEmailAndPhone(String userId) {
        Optional<UserInformation> optionalUserInformation = userRepository.findById(userId);
        if(!optionalUserInformation.isPresent()){
            throw new UserNotFoundException("user Not Found");
        }
        HashMap<String ,Object> resultMap = new HashMap<String, Object>();

        resultMap.put("email", optionalUserInformation.get().getEmail());
        resultMap.put("phone", optionalUserInformation.get().getPhone());

        return resultMap;
    }

    @Override
    public boolean isExistAnyPersonalIntroduction(String userId) {
        Optional<PersonalIntroduction> optionalPersonalIntroduction = personalIntroductionRepository.findById(userId);
        if (!optionalPersonalIntroduction.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public void initPersonalIntroduction(String userId) {

        PersonalIntroduction defaultIntroductionData = PersonalIntroduction.builder()
                .userId(userId)
                .userName("name")
                .position("position")
                .skill("skill")
                .introduction("introduction")
                .build();
        personalIntroductionRepository.save(defaultIntroductionData);

    }

    @Override
    public HashMap<String, Object> getPersonalIntroductionData(String userId) {
        Optional<PersonalIntroduction> optionalPersonalIntroduction = personalIntroductionRepository.findById(userId);
        if (!optionalPersonalIntroduction.isPresent()) {
            throw new UserNotFoundException("userId not Exist");
        }

        HashMap<String, Object> personalIntroductionMap = new HashMap<String, Object>();
        personalIntroductionMap.put("username", optionalPersonalIntroduction.get().getUserName());
        personalIntroductionMap.put("position", optionalPersonalIntroduction.get().getPosition());
        personalIntroductionMap.put("skill", optionalPersonalIntroduction.get().getSkill());
        personalIntroductionMap.put("introduction", optionalPersonalIntroduction.get().getIntroduction());

        return personalIntroductionMap;
    }

    @Override
    public void editPersonalIntroduction(String userId,EditIntroductionInput editIntroductionInput) {

        Optional<PersonalIntroduction> optionalPersonalIntroduction = personalIntroductionRepository.findById(userId);
        if (!optionalPersonalIntroduction.isPresent()) {
            throw new UserNotFoundException("userId not Exist");
        }
        PersonalIntroduction updatePersonalIntroduction = optionalPersonalIntroduction.get();
        updatePersonalIntroduction.setUserName(editIntroductionInput.getName());
        updatePersonalIntroduction.setPosition(editIntroductionInput.getPosition());
        updatePersonalIntroduction.setSkill(editIntroductionInput.getSkill());
        updatePersonalIntroduction.setIntroduction(editIntroductionInput.getIntroduction());
        personalIntroductionRepository.save(updatePersonalIntroduction);

    }
}
