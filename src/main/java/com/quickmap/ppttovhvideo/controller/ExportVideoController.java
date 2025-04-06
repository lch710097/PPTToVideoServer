package com.quickmap.ppttovhvideo.controller;

import com.quickmap.ppttovhvideo.entity.ExportVideoEntity;
import com.quickmap.ppttovhvideo.entity.VoiceEntity;
import com.quickmap.ppttovhvideo.property.AIServerProperties;
import com.quickmap.ppttovhvideo.service.ExportVideoService;
import com.quickmap.ppttovhvideo.service.FileStorageService;
import com.quickmap.ppttovhvideo.service.VoiceService;
import com.quickmap.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/api/export")
public class ExportVideoController {

    @Autowired
    private ExportVideoService exportVideoService;



    @PostMapping("/save")
    public ResponseEntity<List<ExportVideoEntity>> saveExport(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "priority", required = false ) Integer priority
            ) {


        List<ExportVideoEntity> exportVideoEntities=exportVideoService.save(id, priority);

        return ResponseEntity.ok(exportVideoEntities);
    }




    


    @GetMapping("/findbypptid/{id}")
    public List<ExportVideoEntity> findByPPT(@PathVariable Integer id) {
        return exportVideoService.findByPPTId(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<List<ExportVideoEntity>> deleteFile(@PathVariable Integer id) {
        List<ExportVideoEntity> voiceEntities= exportVideoService.deleteById(id);

            return ResponseEntity.ok(voiceEntities) ;
    }





}