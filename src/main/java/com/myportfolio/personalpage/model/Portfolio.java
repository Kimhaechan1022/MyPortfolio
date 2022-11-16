package com.myportfolio.personalpage.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    @Id
    private String portfolioId;
    private String title;
    private String content;
    private String userId;
    private String fileName;
}
