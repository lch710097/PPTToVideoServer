package com.quickmap.ppttovhvideo.controller;

import com.quickmap.ppttovhvideo.entity.DigitHumanEntity;
import com.quickmap.ppttovhvideo.entity.VoiceEntity;
import com.quickmap.ppttovhvideo.property.AIServerProperties;
import com.quickmap.ppttovhvideo.service.DigitHumanService;
import com.quickmap.ppttovhvideo.service.FileStorageService;
import com.quickmap.ppttovhvideo.service.VoiceService;
import com.quickmap.utils.Constant;
import com.quickmap.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/api/digithumans")
public class DigitHumanController {

    @Autowired
    private DigitHumanService digitHumanService;


    @Autowired
    private  FileStorageService fileStorageService;


    @PostMapping("/upload")
    public ResponseEntity<List<DigitHumanEntity>> uploadFile(
            @RequestParam(value = "file",required = false) MultipartFile file,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "id", required = false) Integer id, HttpSession session) {


        int userid= Util.getSessionUserId(session);

        System.out.println("get digithuman upload request name:"+name);
        List<DigitHumanEntity> voiceEntities=null;
        if(id!=null){
            voiceEntities = digitHumanService.updateFile(file, name,id,userid);
        }else {
             voiceEntities = digitHumanService.saveFile(file, name,userid);

        }

        return ResponseEntity.ok(voiceEntities);
    }




    
    @GetMapping
    public List<DigitHumanEntity> getAll(HttpSession session) {
        int userid=Util.getSessionUserId(session);

        List<DigitHumanEntity> digitHumanEntities=digitHumanService.findByUserId(userid);

        if(userid!=0){
            List<DigitHumanEntity> digitHumanEntities1=digitHumanService.findByUserId(0);
            digitHumanEntities.addAll(digitHumanEntities1);
        }


        return digitHumanEntities;
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<List<DigitHumanEntity>> deleteFile(@PathVariable Integer id,HttpSession session) {

        int userid=Util.getSessionUserId(session);
        List<DigitHumanEntity> voiceEntities= digitHumanService.deleteById(id,userid);

            return ResponseEntity.ok(voiceEntities) ;
    }





}