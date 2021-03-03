package com.uz.paycom.ServiceBean;

import com.google.gson.Gson;
import com.uz.paycom.entity.Code;
import com.uz.paycom.entity.Settings;
import com.uz.paycom.payload.ApiResponse;
import com.uz.paycom.payload.ReqActivate;
import com.uz.paycom.repository.CodeRepository;
import com.uz.paycom.repository.SettingRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private SettingRepository settingRepository;
    @Autowired
    private RepoUz repoUz;

    @Transactional
    public ApiResponse sms(Code code){
        Code codes = codeRepository.findByPhoneNumber(code.getPhoneNumber());

       if (codes.getCode().equals(code.getCode())){
           codes.setBo(true);
           repoUz.save(codes);
           codeRepository.save(codes);
               return new ApiResponse("ok", true, codes);
           }else {
           return new ApiResponse("false", false);
       }
    }


    public ApiResponse getAllCode(){
        List<Code> code = codeRepository.findAll();


        return new ApiResponse("codes", true, code);
    }



    @Transactional
    public ApiResponse sendToPravoider(ReqActivate activate){
        String number = activate.getPhone().replaceAll("[^0-9]", ""); // удалить все кроме цифр
        if (number.startsWith("998")){
            if (number.length() == 12){
                activate.setPhone(number);
                try {
                    URL targetUrl = new URL("http://localhost:8080/sms/get");
                    HttpURLConnection httpConnection =
                            (HttpURLConnection) Objects.requireNonNull(targetUrl).openConnection();

                    httpConnection.setDoOutput(true);
                    httpConnection.setRequestMethod("POST");
                    httpConnection.setRequestProperty("Accept-Charset", "UTF-8");
                    httpConnection.setRequestProperty("Content-Type", "application/json");

                    OutputStream outputStream = httpConnection.getOutputStream();
                    Gson gson = new Gson();
                    String request = gson.toJson(activate);
                    outputStream.write(request.getBytes());
                    outputStream.flush();

                    if (httpConnection.getResponseCode() != 200) {
                        return new ApiResponse("Nomer notug'ri!", false);
                    }
                    httpConnection.disconnect();
                    return new ApiResponse("SUCCESS", true);
                } catch(IOException e){
                    System.out.println("error " + e);
                }
            }
            return new ApiResponse("ERROR", false);
        }

        return new ApiResponse("ERROR", false);
    }

}
