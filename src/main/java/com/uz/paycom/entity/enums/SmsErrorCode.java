package com.uz.paycom.entity.enums;


import com.sun.istack.Nullable;

public enum SmsErrorCode {

    ERROR100(100),
    ERROR101(101),
    ERROR102(102),
    ERROR103(103),
    ERROR104(104),
    ERROR105(105),
    ERROR202(202),
    ERROR204(204),
    ERROR205(205),
    ERROR206(206),
    ERROR301(301),
    ERROR302(302),
    ERROR303(303),
    ERROR304(304),
    ERROR305(305),
    ERROR306(306),
    ERROR401(401),
    ERROR402(402),
    ERROR403(403),
    ERROR404(404),
    ERROR405(405),
    ERROR406(406),
    ERROR407(407),
    ERROR408(408),
    ERROR410(410),
    ERROR411(411),
    ;

    private Integer id;

    SmsErrorCode(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static SmsErrorCode fromId(Integer id) {
        for (SmsErrorCode at : SmsErrorCode.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}