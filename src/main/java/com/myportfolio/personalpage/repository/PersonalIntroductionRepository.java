package com.myportfolio.personalpage.repository;

import com.myportfolio.personalpage.model.PersonalIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalIntroductionRepository extends JpaRepository<PersonalIntroduction, String> {

}