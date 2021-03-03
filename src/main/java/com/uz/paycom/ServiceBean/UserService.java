package com.uz.paycom.ServiceBean;

import com.uz.paycom.entity.Users;
import com.uz.paycom.payload.ApiResponse;
import com.uz.paycom.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(Users users) {
        Optional<Users> login = usersRepository.findByPhoneNumber(users.getPhoneNumber());
        if (login.isPresent() && passwordEncoder.matches(users.getPassword(), login.get().getPassword())){
            return true;
        }
        return false;

    }

    public ApiResponse getAllUser(){
        List<Users> users = usersRepository.findAll();
//        List<List<Users>> users1 = new LinkedList<>();
//        users1.add(users);
        return new ApiResponse("userlar", true, users);
    }



}
