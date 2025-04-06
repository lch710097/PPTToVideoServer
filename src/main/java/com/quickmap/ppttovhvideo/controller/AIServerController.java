package com.quickmap.ppttovhvideo.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.quickmap.ppttovhvideo.entity.VoiceEntity;
import com.quickmap.ppttovhvideo.property.AIServerProperties;
import com.quickmap.ppttovhvideo.service.FileStorageService;
import com.quickmap.ppttovhvideo.service.VoiceService;
import com.quickmap.utils.Constant;
import org.json.JSONArray;
import org.json.JSONObject;
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
@RequestMapping("/api/aiserver")
public class AIServerController {



    @Autowired
    private AIServerProperties aiServerProperties;


    @PostMapping("/generator")
    public ResponseEntity<Map> getContentFromAI(
            @RequestParam(value = "content", required = true) String content,
            @RequestParam(value = "type", required = true) Integer type) {

        System.out.println("get  ai server request name:"+content+" "+type);

        Map map=sendToDeekSeek(content,type);
        return ResponseEntity.ok(map);
    }



    public Map sendToDeekSeek(String content ,int type){

        if(type==1){
            content+=" 完善上面一段文字";
        }else{
            content+=" 简化上面一段文字到"+content.length()/2+"字以内";
        }
        Map map=new HashMap();
            Unirest.setTimeouts(0, 0);
            try {

                JSONObject params=new JSONObject();

                params.put("model","deepseek-chat");
                params.put("max_tokens",2048);

                params.put("temperature",0.2);
                JSONArray mess=new JSONArray();

                JSONObject role=new JSONObject();

                role.put("content","You are a helpful assistant");
                role.put("role","system");

                mess.put(role);

                role=new JSONObject();

                role.put("content",content);
                role.put("role","user");

                mess.put(role);

                params.put("messages",mess);


                HttpResponse<String> response = Unirest.post(aiServerProperties.getDeepseekserver())
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer " +aiServerProperties.getDeepseekkey() )
                        .body(
                                params.toString()
                        )
                        .asString();


                String res=response.getBody();

                System.out.println("get res from server:"+res);
                JSONObject jsonObject=new JSONObject(res);

                boolean hascontent=false;
                if(jsonObject.has("choices")) {
                    JSONArray choices = jsonObject.getJSONArray("choices");
                    if(choices.length()>0){
                        JSONObject jsonObject1=choices.getJSONObject(0);
                        String aicontent=jsonObject1.getJSONObject("message").getString("content");

                        hascontent=true;
                        map.put("content",aicontent);


                    }
                }
                if(!hascontent){
                    map.put("msg","返回结果不正确"+res);
                }




            }catch (Exception ex){
                ex.printStackTrace();
                map.put("msg",ex.getMessage());
            }


            return  map;



    }




    public  static  void main(String[] args){

        AIServerController aiServerController=new AIServerController();


        aiServerController.sendToDeekSeek("国防动员办公室信息化系统",1);

    }



}