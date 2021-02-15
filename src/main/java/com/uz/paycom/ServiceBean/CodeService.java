package com.uz.paycom.ServiceBean;

import com.uz.paycom.entity.Code;
import com.uz.paycom.entity.Settings;
import com.uz.paycom.payload.ApiResponse;
import com.uz.paycom.repository.CodeRepository;
import com.uz.paycom.repository.SettingRepository;
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
import java.util.List;
import java.util.Objects;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Transactional
    public ApiResponse sms(Code code){
        Code codes = codeRepository.findByPhoneNumber(code.getPhoneNumber());

       if (codes.getCode().equals(code.getCode())){
           codes.setBo(true);
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


    public void sendToProvider(String phoneNumber, String msg) {
        phoneNumber=phoneNumber.replaceAll("[^0-9]", ""); // удалить все кроме цифр
        try {


            URL targetUrl = null;
            String user = "";
            String password = "";
            List<Settings> settings = settingRepository.findAll();
            for (Settings setting : settings) {
                if (setting.getName().equals("URL")) targetUrl = new URL(setting.getValue());
                if (setting.getName().equals("USERNAME")) user = setting.getValue();
                if (setting.getName().equals("PASSWORD")) password = setting.getValue();
            }
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString(
                    (user + ":" + password).getBytes(StandardCharsets.UTF_8));
            HttpURLConnection httpConnection =
                    (HttpURLConnection) Objects.requireNonNull(targetUrl).openConnection();

            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Authorization", basicAuth);
            httpConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpConnection.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = httpConnection.getOutputStream();
            String mssg = " -      Registratsiya polzovatelya v Dark Net. Nekomu ne soobshayte kod ";
            String string = "{\"messages\":[{\"recipient\":\""+phoneNumber+"\",\"message-id\":\"TYS000000901\",\"timing\":{\"localtime\":0,\"start-datetime\":\"\",\"end-datetime\":\"\",\"allowed-starttime\":\"\",\"allowed-endTime\":\"\",\"send-evenly\":1},\"sms\":{\"originator\":\"3700\",\"content\":{\"text\":\""+msg+mssg+msg+"\"}}}]}";
            outputStream.write(string.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                if (httpConnection.getResponseCode() == 400) {
                    InputStreamReader inputStreamReader = new InputStreamReader((httpConnection.getErrorStream()));
                    BufferedReader responseBuffer = new BufferedReader(inputStreamReader);
                    String output;
                    while ((output = responseBuffer.readLine()) != null) {
                        System.out.println(output);
                    }
                }
            }
                // временно
                System.out.println("info " + string);

                httpConnection.disconnect();
            } catch(IOException e){
            System.out.println("error " + e);
            }
    }

}
