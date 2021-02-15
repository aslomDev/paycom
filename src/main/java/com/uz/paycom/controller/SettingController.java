package com.uz.paycom.controller;

import com.uz.paycom.ServiceBean.SettingService;
import com.uz.paycom.entity.Settings;
import com.uz.paycom.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class SettingController {

    @Autowired private SettingService settingService;


    @PostMapping("/setting")
    public HttpEntity<?> addSettings(@RequestBody Settings settings){
        ApiResponse response = settingService.addSetting(settings);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/setting")
    public HttpEntity<?> getAll(){
        ApiResponse response = settingService.getAll();
        return ResponseEntity.ok(response);
    }


    @PutMapping("/edset/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody Settings settings){
        ApiResponse response = settingService.editSettings(id, settings);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/set/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse response = settingService.delete(id);

        return ResponseEntity.ok(response);
    }



}
