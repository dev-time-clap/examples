package de.devtime.app.example.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.devtime.app.example.persistence.entity.LoanActivityEntity;

public interface LoanRepository extends JpaRepository<LoanActivityEntity, String> {

}
