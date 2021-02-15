package com.uz.paycom.security;

import com.uz.paycom.ServiceBean.CodeService;
import com.uz.paycom.entity.Users;
import com.uz.paycom.payload.ApiResponse;
import com.uz.paycom.repository.CodeRepository;
import com.uz.paycom.repository.RoleRepository;
import com.uz.paycom.repository.UsersRepository;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    private final UsersRepository userRepository;
    private final RoleRepository roleRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final CodeService codeService;
    private final CodeRepository codeRepository;
//    private final Schuld schuld;

    public AuthService(UsersRepository userRepository, RoleRepository roleRepository, MessageSource messageSource, PasswordEncoder passwordEncoder, CodeService codeService, CodeRepository codeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
        this.codeService = codeService;
        this.codeRepository = codeRepository;

//        this.schuld = schuld;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users byLogin = userRepository.getByPhoneNumber(login);
        Optional<Users> findByLogin = userRepository.findByPhoneNumber(login);
        return userRepository.findByPhoneNumber(login).orElseThrow(() -> new UsernameNotFoundException(login));
    }


    UserDetails loadUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User id not found: " + userId));
    }



    public ApiResponse register(Users request) {
        Optional<Users> optionalUser = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (optionalUser.isPresent()) {
            return new ApiResponse("xato", false);
        } else {
            Users users = new Users();

            users.setPhoneNumber(request.getPhoneNumber());
            users.setPassword(passwordEncoder.encode(request.getPassword()));
            users.setFirstName(request.getFirstName());
            users.setLastName(request.getLastName());
            users.setCode(activeUser());

            /**
             * userni telefoniga active codni junatadi
             */
            codeService.sendToProvider(request.getPhoneNumber(), users.getCode().toString());

                userRepository.save(users);
                
                return new ApiResponse("Foydalanuvchi ro'yxatdan o'tdi!", true);


        }
    }

    @Transactional
    public ApiResponse activateUser(Users users){
        Users isUser = userRepository.findByPhoneNumber(users.getPhoneNumber()).get();
        if (isUser.getPhoneNumber().equals(users.getPhoneNumber())){
            if (isUser.getCode().equals(users.getCode())){
                isUser.setBo(true);
                isUser.setCode(null);
                userRepository.save(isUser);
                return new ApiResponse("user Activated", true, isUser);
            }else {
                return new ApiResponse("kod", false);
            }
        }else {
            return new ApiResponse("user topilmadi", false);
        }

    }


    public String activeUser(){
//        Random random = new Random();
//        Integer ran = (random.nextInt(10000));
//        return ran;
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        return String.format("%05d", num);

    }


}

