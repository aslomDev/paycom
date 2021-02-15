package com.uz.paycom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "code")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "code")
    private String code;

    @Column(name = "phoneNumber")
    private String phoneNumber;


    @Column(name = "isBo")
    private boolean isBo = false;


}
