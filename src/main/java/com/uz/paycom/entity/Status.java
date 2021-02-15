package com.uz.paycom.entity;

import com.uz.paycom.entity.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "status")
public class Status {

    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private UserStatus type;


}
