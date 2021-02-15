package com.uz.paycom.ServiceBean;

import com.uz.paycom.entity.Settings;
import com.uz.paycom.payload.ApiResponse;
import com.uz.paycom.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;


    public ApiResponse addSetting(Settings settings) {
        Optional<Settings> newSett = settingRepository.findByName(settings.getName());

        if (newSett.isPresent()) {
            return new ApiResponse("xato", false);
        }

        Settings isSet = new Settings();
        isSet.setName(settings.getName());
        isSet.setValue(settings.getValue());
        settingRepository.save(isSet);
        return new ApiResponse("ok", true);
    }

    public ApiResponse editSettings(Integer id, Settings settings) {
        Settings newSett = settingRepository.findById(id).get();

        newSett.setName(settings.getName());
        newSett.setValue(settings.getValue());
        settingRepository.save(newSett);
        return new ApiResponse("update", true);
    }

    public ApiResponse delete(Integer id){
        Settings settings = settingRepository.findById(id).get();
        settingRepository.delete(settings);
        return new ApiResponse("deleted", true);
    }

    public ApiResponse getAll(){
        List<Settings> settings = settingRepository.findAll();

        return new ApiResponse("deleted", true, settings);
    }

}
