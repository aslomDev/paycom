package com.uz.paycom.repository;

import com.uz.paycom.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByPhoneNumber(String number);

    Users getByPhoneNumber(String number);


}
