package com.quickmap.ppttovhvideo.controller;

import com.quickmap.ppttovhvideo.entity.FileEntity;
import com.quickmap.ppttovhvideo.entity.PageEntity;
import com.quickmap.ppttovhvideo.service.FileService;
import com.quickmap.ppttovhvideo.service.FileStorageService;
import com.quickmap.utils.Constant;
import com.quickmap.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;



    @PostMapping("/upload")
    public ResponseEntity<Map> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "name", required = false) String description, HttpSession session) {


        int userid= Util.getSessionUserId(session);
        System.out.println("get upload request name:"+description+" file:"+file.getSize());
        Map pageEntities = fileService.saveFile(file, description,userid);
        
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/files/download/")
//                .path(savedFile.getId().toString())
//                .toUriString();
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("id", savedFile.getId());
//        response.put("fileName", savedFile.getName());
//        response.put("fileType", savedFile.getFileurl());
//        response.put("fileSize", savedFile.getPagecount());
//
//        response.put("description", savedFile.getVideocount());

        return ResponseEntity.ok(pageEntities);
    }

    @PostMapping("/pageupload")
    public ResponseEntity<Map> uploadPage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "type", required = true) Integer type,
            @RequestParam(value = "pageid", required = true) Integer pageid) {




        System.out.println("get upload image request type:"+type+" pageid:"+pageid);
        Map pageEntities = fileService.savePage(file, type,pageid);


        return ResponseEntity.ok(pageEntities);
    }
    
    @GetMapping
    public List<FileEntity> getAllFiles(HttpSession session ) {

        Integer userid=Util.getSessionUserId(session);
        System.out.println("get userid:"+userid);

        if(userid==null){
            userid=0;
        }

        List<FileEntity>  fileEntities=fileService.findByUserId(userid);
        if(userid!=0){
            List<FileEntity>  fileEntities0=fileService.findByUserId(0);

            fileEntities.addAll(fileEntities0);
        }
        return fileEntities;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FileEntity> getFileById(@PathVariable Integer id) {
        Optional<FileEntity> file = fileService.getFileById(id);
        return file.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public List<FileEntity> searchFiles(@RequestParam String fileName) {
        return fileService.searchFiles(fileName);
    }


    @GetMapping("/pages")
    public List<PageEntity> searchPages(@RequestParam Integer pptid) {


        return fileService.searchPages(pptid);
    }


    @PutMapping("/{id}")
    public ResponseEntity<FileEntity> updateFile(
            @PathVariable Integer id,
            @RequestParam String name) {
        
        FileEntity updatedFile = fileService.updateFile(id, name);
        if (updatedFile != null) {
            return ResponseEntity.ok(updatedFile);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/page/{id}")
    public ResponseEntity<Map> updatePage(
            @PathVariable Integer id,
            @RequestParam String content) {

        System.out.println("get  id:"+id+" "+content);

        Map updatedFile = fileService.updatePage(id, content);

        if (updatedFile != null) {
            return ResponseEntity.ok(updatedFile);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/page/param/{id}")
    public ResponseEntity<Map> updatePageParams(
            @PathVariable Integer id,
            @RequestParam(required = false) Integer voiceid,
            @RequestParam Boolean voiceforall,
            @RequestParam(required = false) Integer dgid,
            @RequestParam Integer left,
            @RequestParam Integer top,
            @RequestParam Integer width,
            @RequestParam Integer height,
            @RequestParam Boolean dgforall,
            @RequestParam(required = false)  Boolean bgtrans) {

        System.out.println(" update page params  id:"+id+" "+voiceid+" "+voiceforall+" "+dgid+" "+left+" "+top+" "+width+" "+height+" "+dgforall);

        Map updatedFile = fileService.updatePageParam(id, voiceid,voiceforall,dgid,left,top,width,height,dgforall,bgtrans);

        if (updatedFile != null) {
            return ResponseEntity.ok(updatedFile);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<List<FileEntity>> deleteFile(@PathVariable Integer id,HttpSession session) {


        int userid= Util.getSessionUserId(session);

        Optional<FileEntity> fileEntity=fileService.getFileById(id);
        if(fileEntity.isPresent()){
            FileEntity fileEntity1=fileEntity.get();
            if((userid!=0&&fileEntity1.getUserid()==userid)||(userid==1&&fileEntity1.getUserid()==0)){  //超级管理员1
                System.out.println("delete id:"+id);
                boolean deleted = fileService.deleteFile(id);

            }
        }
        List<FileEntity> fileEntities=fileService.findByUserId(userid);
        if(userid!=0){
            List<FileEntity> fileEntities1=fileService.findByUserId(0);
            fileEntities.addAll(fileEntities1);
        }
        return ResponseEntity.ok(fileEntities);

    }

    @DeleteMapping("/page/{id}")
    public ResponseEntity<FileEntity> deletePage(@PathVariable Integer id,HttpSession session) {


        FileEntity deleted = fileService.deletePage(id);
        if (deleted!=null) {
            return ResponseEntity.ok(deleted);
        }
        return ResponseEntity.notFound().build();
    }
    

}