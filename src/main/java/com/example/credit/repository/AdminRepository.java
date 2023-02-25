package com.example.credit.repository;

import com.example.credit.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> getAdminByAuthId(String authId);
}
