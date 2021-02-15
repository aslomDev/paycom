package com.uz.paycom.repository;

import com.uz.paycom.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {
    Code findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);
}
