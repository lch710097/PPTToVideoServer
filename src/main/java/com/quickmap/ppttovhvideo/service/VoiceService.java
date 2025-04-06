package com.quickmap.ppttovhvideo.service;


import com.quickmap.ppttovhvideo.entity.FileEntity;
import com.quickmap.ppttovhvideo.entity.VoiceEntity;

import com.quickmap.ppttovhvideo.repository.VoiceRepository;
import com.quickmap.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VoiceService {

    @Autowired
    private VoiceRepository voiceRepository;
    
    @Autowired
    private FileStorageService fileStorageService;




    public  List<VoiceEntity> saveFile(MultipartFile file, String name,String content,int userid) {



        String fileName = fileStorageService.storeVoiceFile(file);



         VoiceEntity voiceEntity=new VoiceEntity(name, Constant.VOICE_URL+fileName,content,userid);

        voiceRepository.save(voiceEntity);



        List<VoiceEntity> voiceEntities=voiceRepository.findByUserid(userid);

        if(userid!=0){
            List<VoiceEntity>  voiceEntities1=voiceRepository.findByUserid(0);

            voiceEntities.addAll(voiceEntities1);

        }


      return  voiceEntities;


    }


    public  List<VoiceEntity> updateFile(MultipartFile file, String name,String content,int id,int userid) {


        Optional<VoiceEntity> voiceEntity=voiceRepository.findById(id);

        if(voiceEntity.isPresent()){
            VoiceEntity voiceEntity1=voiceEntity.get();

            if(file!=null){
                String fileName = fileStorageService.storeVoiceFile(file);

                voiceEntity1.setAudio_url(Constant.VOICE_URL+fileName);

            }

            voiceEntity1.setName(name);
            voiceEntity1.setAudio_content(content);
            voiceRepository.save(voiceEntity1);

        }


        List<VoiceEntity> voiceEntities=voiceRepository.findByUserid(userid);

        if(userid!=0){
            List<VoiceEntity>  voiceEntities1=voiceRepository.findByUserid(0);

            voiceEntities.addAll(voiceEntities1);

        }

        return  voiceEntities;


    }


    public List<VoiceEntity> deleteById(Integer id,Integer userid) {
        Optional<VoiceEntity> fileOptional = voiceRepository.findById(id);
        if (fileOptional.isPresent()) {
            VoiceEntity file = fileOptional.get();

            if((userid!=0&&file.getUserid()==userid)||(userid==1&&file.getUserid()==0)){

                fileStorageService.deleteVoiceFile(file.getAudio_url());

                voiceRepository.delete(file);
            }

        }
        List<VoiceEntity> voiceEntities=voiceRepository.findByUserid(userid);

        if(userid!=0){
            List<VoiceEntity> voiceEntities1=voiceRepository.findByUserid(0);
            voiceEntities.addAll(voiceEntities1);
        }



        return  voiceEntities;

    }


    public List<VoiceEntity> findByUserId(Integer userid){
        return  voiceRepository.findByUserid(userid);
    }
    public Optional<VoiceEntity> findById(Integer id) {
        return voiceRepository.findById(id);
    }


    

    

}