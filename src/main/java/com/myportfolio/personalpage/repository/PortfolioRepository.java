package com.myportfolio.personalpage.repository;


import com.myportfolio.personalpage.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, String> {

    Optional<Portfolio> findByUserId(String userId);

}
