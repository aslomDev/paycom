package com.uz.paycom.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    public String message;

    public Object object;

    public boolean result;

    public ApiResponse(String message,  boolean result, Object object) {
        this.message = message;
        this.result = result;
        this.object = object;
    }

    public ApiResponse(String message, boolean result) {
        this.message = message;
        this.result = result;
    }

    ApiResponse(boolean result) {
        this.result = result;
    }
}
