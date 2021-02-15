package com.uz.paycom.ServiceBean;

import com.uz.paycom.entity.Users;
import com.uz.paycom.repository.UsersRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;


@Component
public class Schuld {

    private final UsersRepository usersRepository;

    public Schuld(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Scheduled(fixedDelay = 300000)
    public void deletedNoActive(){
        List<Users> users = usersRepository.findAll();

        if (!users.isEmpty()){
            for (Users u : users){
                long hour = u.getCreatedAt().getHour();
                long minute = u.getCreatedAt().getMinute();
                hour=hour = minute;
                long endTime = hour + 5;

                Date date = new Date();
                long dateHours = date.getHours();
                long dateMinutes = date.getMinutes();
                dateHours=dateHours=dateMinutes;
                long dateStart = dateHours;

                if (!u.isBo()) {
                    // если узер не активин до 5 минута будем удалить
                    if (dateStart >= endTime) {

                        usersRepository.delete(u);
                    }
                }else {
                    // если узер активин до 5 минута не будем удалить
                }
            }
        }
    }


}
