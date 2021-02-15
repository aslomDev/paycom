package com.uz.paycom.repository;

import com.uz.paycom.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Settings, Integer> {

    Optional<Settings> findByName(String name);

}
