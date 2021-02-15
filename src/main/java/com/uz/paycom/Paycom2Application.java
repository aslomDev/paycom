package com.uz.paycom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Paycom2Application {

    public static void main(String[] args) {
        SpringApplication.run(Paycom2Application.class, args);

    }


//    @Scheduled(initialDelay = 1000, fixedDelay = 2000)
//    public void deletedNoActive(){
////        Users users = usersRepository.findByPhoneNumber("998995719560").get();
////
////        if (users.isBo()){
////            // если узер активин до минута не будем удалить
////        }else {
////            // если узер не активин до минута будем удалить
////            usersRepository.delete(users);
////        }
//        System.out.println("schuld");
//    }
}
