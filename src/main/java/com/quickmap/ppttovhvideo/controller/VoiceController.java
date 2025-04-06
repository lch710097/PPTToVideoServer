package com.quickmap.ppttovhvideo.controller;

import com.quickmap.ppttovhvideo.entity.FileEntity;
import com.quickmap.ppttovhvideo.entity.PageEntity;
import com.quickmap.ppttovhvideo.entity.VoiceEntity;
import com.quickmap.ppttovhvideo.property.AIServerProperties;
import com.quickmap.ppttovhvideo.service.FileService;
import com.quickmap.ppttovhvideo.service.FileStorageService;
import com.quickmap.ppttovhvideo.service.VoiceService;
import com.quickmap.utils.Constant;
import com.quickmap.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/api/voices")
public class VoiceController {

    @Autowired
    private VoiceService voiceService;


    @Autowired
    private  FileStorageService fileStorageService;
    @Autowired
    private AIServerProperties aiServerProperties;

    @PostMapping("/upload")
    public ResponseEntity<List<VoiceEntity>> uploadFile(
            @RequestParam(value = "file",required = false) MultipartFile file,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "id", required = false) Integer id, HttpSession session) {


        int userid= Util.getSessionUserId(session);

        System.out.println("get voice upload request name:"+name+" "+content);
        List<VoiceEntity> voiceEntities=null;
        if(id!=null){
            voiceEntities = voiceService.updateFile(file, name, content,id,userid);
        }else {
             voiceEntities = voiceService.saveFile(file, name, content,userid);

        }

        return ResponseEntity.ok(voiceEntities);
    }


    @GetMapping("/clone")
    public ResponseEntity<Map> cloneVoice(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "text", required = true) String text) {


        String server=aiServerProperties.getSoundserver();

        System.out.println("get voice clone request  :"+id+" "+text+" "+server );
        Map map=new HashMap();
        Optional<VoiceEntity> voiceEntity=voiceService.findById(id);
        if(voiceEntity.isPresent()){
            VoiceEntity voiceEntity1=voiceEntity.get();

           String promt= voiceEntity1.getAudio_content();
           String url=voiceEntity1.getAudio_url();

           url=url.substring(url.indexOf("/")+1);

           String audio_file=fileStorageService.getVoiceStorageLocation().resolve(url).toAbsolutePath().toString();


           String filename=UUID.randomUUID()+".mp3";

           String savefile=fileStorageService.getVoiceStorageLocation().resolve(filename).toAbsolutePath().toString();


           String content=formUpload(server,audio_file,promt,text,savefile);


           if(content.length()>1){
               map.put("url", Constant.VOICE_URL+filename);
           }



        }





        return ResponseEntity.ok(map);
    }

    
    @GetMapping
    public List<VoiceEntity> getAll(HttpSession session)
    {
        int userid=Util.getSessionUserId(session);
        List <VoiceEntity> voiceEntities=voiceService.findByUserId(userid);
        if(userid!=0){
            List<VoiceEntity> voiceEntities1=voiceService.findByUserId(0);
            voiceEntities.addAll(voiceEntities1);
        }
        return voiceEntities;
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<List<VoiceEntity>> deleteFile(@PathVariable Integer id,HttpSession session) {

        int userid=Util.getSessionUserId(session);

        List<VoiceEntity> voiceEntities= voiceService.deleteById(id,userid);

            return ResponseEntity.ok(voiceEntities) ;
    }

    public static String formUpload(String urlStr, String audio_file,String promt,String content,String savefile ) {
        String res = "";
        HttpURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text

                StringBuffer strBuf = new StringBuffer();


                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                              +"tts_text\"\r\n\r\n");
                    strBuf.append(content);

                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            +"prompt_text\"\r\n\r\n");
                    strBuf.append(promt);


                strBuf.append("\r\n").append("--").append(BOUNDARY)
                        .append("\r\n");
                strBuf.append("Content-Disposition: form-data; name=\""
                        +"savefile\"\r\n\r\n");
                strBuf.append(savefile);
                out.write(strBuf.toString().getBytes());

            // file

                    File file = new File(audio_file);
                    String filename = file.getName();

                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    //contentType非空采用filename匹配默认的图片类型
                    if(!"".equals(contentType)){
                        if (filename.endsWith(".png")) {
                            contentType = "image/png";
                        }else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".jpe")) {
                            contentType = "image/jpeg";
                        }else if (filename.endsWith(".gif")) {
                            contentType = "image/gif";
                        }else if (filename.endsWith(".ico")) {
                            contentType = "image/image/x-icon";
                        }
                    }
                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }
                      strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + "prompt_wav\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(
                            new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据



            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            res = builder.toString();



            reader.close();

        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }


    public  static  void main(String[] args){

        formUpload("http://localhost:50000/genrator_audio","F:\\TraeWork\\PPTToVideoServer\\voicedir\\c9c48db6-dda6-4e3e-9fa5-0cc2baf2f43a.mp3",
                "专业从事指挥信息通讯技术的国家级高新技术企业","拥有国家甲级涉密资质和计算机系统集成及军工安防壹级企业","F:\\TraeWork\\PPTToVideoServer\\voicedir\\clone.mp3");
    }



}