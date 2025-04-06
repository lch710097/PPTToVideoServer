package com.quickmap.ppttovhvideo.service;


import com.quickmap.ppttovhvideo.entity.DigitHumanEntity;
import com.quickmap.ppttovhvideo.entity.VoiceEntity;
import com.quickmap.ppttovhvideo.repository.DigitHumanRepository;
import com.quickmap.ppttovhvideo.repository.VoiceRepository;
import com.quickmap.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class DigitHumanService {

    @Autowired
    private DigitHumanRepository digitHumanRepository;
    
    @Autowired
    private FileStorageService fileStorageService;




    public  List<DigitHumanEntity> saveFile(MultipartFile file, String name,int userid) {



        String fileName = fileStorageService.storeImageFile(file);



         DigitHumanEntity voiceEntity=new DigitHumanEntity(name, Constant.DIGITHUMAN_URL+fileName,userid);

        digitHumanRepository.save(voiceEntity);


        List<DigitHumanEntity> voiceEntities=digitHumanRepository.findByUserid(userid);
        if(userid!=0){
            List<DigitHumanEntity> digitHumanEntities1=digitHumanRepository.findByUserid(0);
            voiceEntities.addAll(digitHumanEntities1);
        }


      return  voiceEntities;


    }


    public  List<DigitHumanEntity> updateFile(MultipartFile file, String name ,int id,int userid) {


        Optional<DigitHumanEntity> voiceEntity=digitHumanRepository.findById(id);

        if(voiceEntity.isPresent()){
            DigitHumanEntity voiceEntity1=voiceEntity.get();

            if(file!=null){
                String fileName = fileStorageService.storeImageFile(file);

                voiceEntity1.setPic_url(Constant.DIGITHUMAN_URL+fileName);

            }

            voiceEntity1.setName(name);

            digitHumanRepository.save(voiceEntity1);

        }


        List<DigitHumanEntity> voiceEntities=digitHumanRepository.findByUserid(userid);
        if(userid!=0){
            List<DigitHumanEntity> digitHumanEntities1=digitHumanRepository.findByUserid(0);
            voiceEntities.addAll(digitHumanEntities1);
        }



        return  voiceEntities;


    }


    public List<DigitHumanEntity> deleteById(Integer id,int userid) {
        Optional<DigitHumanEntity> fileOptional = digitHumanRepository.findById(id);
        if (fileOptional.isPresent()) {
            DigitHumanEntity file = fileOptional.get();

            if((userid!=0&&file.getUserid()==userid)||(userid==1&&file.getUserid()==0)){  //超级管理员1
                fileStorageService.deleteImageFile(file.getPic_url());
                digitHumanRepository.delete(file);
            }

        }
        List<DigitHumanEntity> digitHumanEntities=digitHumanRepository.findByUserid(userid);
        if(userid!=0){
            List<DigitHumanEntity> digitHumanEntities1=digitHumanRepository.findByUserid(0);
            digitHumanEntities.addAll(digitHumanEntities1);
        }

        return  digitHumanEntities;

    }


    public List<DigitHumanEntity> findByUserId(Integer id){
        return  digitHumanRepository.findByUserid(id);
    }


    public Optional<DigitHumanEntity> findById(Integer id) {
        return digitHumanRepository.findById(id);
    }



    

}