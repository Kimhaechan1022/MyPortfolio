package com.myportfolio.personalpage.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditIntroductionInput {
    private String name;
    private String position;
    private String skill;
    private String introduction;
}
