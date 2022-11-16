package com.myportfolio.personalpage.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
public class PersonalIntroduction {

    @Id
    private String userId;

    private String userName;

    private String position;

    private String skill;

    private String introduction;
}
