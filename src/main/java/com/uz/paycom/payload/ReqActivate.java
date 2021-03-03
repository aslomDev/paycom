package com.uz.paycom.payload;

import lombok.Data;

@Data
public class ReqActivate {

    private String phone;

    private String password;

    private String code;

}
