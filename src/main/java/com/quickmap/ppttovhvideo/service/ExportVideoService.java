package com.quickmap.ppttovhvideo.service;


import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.TrackBox;
import com.quickmap.ppttovhvideo.entity.*;
import com.quickmap.ppttovhvideo.exception.FileStorageException;
import com.quickmap.ppttovhvideo.property.AIServerProperties;
import com.quickmap.ppttovhvideo.repository.*;
import com.quickmap.utils.Constant;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Service
public class ExportVideoService {

    @Autowired
    private ExportVideoRepository exportVideoRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private VoiceRepository voiceRepository;

    @Autowired
    private DigitHumanRepository digitHumanRepository;




    @Autowired
    private PageRepository pageRepository;
    
    @Autowired
    private FileStorageService fileStorageService;


    @Autowired
    private AIServerProperties aiServerProperties;



    private static ExcuteThread excuteThread;


    public ExportVideoService(){

        if(excuteThread==null){
            excuteThread=new ExcuteThread();

            excuteThread.start();
        }

    }




    public List<ExportVideoEntity> save(Integer id,  Integer priority) {

        Optional<FileEntity> file=fileRepository.findById(id);

        if(file.isPresent()){
            FileEntity fileEntity=file.get();

            if(priority==null){
                priority=1;
            }


            ExportVideoEntity exportVideoEntity=new ExportVideoEntity(id,fileEntity.getVideocount(),0,priority,0);


            exportVideoRepository.save(exportVideoEntity);

        }




        return  exportVideoRepository.findExportVideoEntitiesByPptid(id);

    }


    public List<ExportVideoEntity> deleteById(Integer id) {
        Optional<ExportVideoEntity> fileOptional = exportVideoRepository.findById(id);
        Integer pptid=0;
        if (fileOptional.isPresent()) {
            ExportVideoEntity file = fileOptional.get();

            fileStorageService.deleteExportFile(file.getId()+"");

            pptid=file.getPptid();


            exportVideoRepository.delete(file);

        }

        return  exportVideoRepository.findExportVideoEntitiesByPptid(pptid);

    }


    public Optional<ExportVideoEntity> findById(Integer id) {
        return exportVideoRepository.findById(id);
    }


    public List<ExportVideoEntity> findByPPTId(Integer id) {

        return exportVideoRepository.findExportVideoEntitiesByPptid(id);
    }





    private static final String GREENCOLOR="0x299530";

    public   String imageToGreen(String file){
        try {
            File originfile=new File(file);
            String name= originfile.getName();

            int ind=name.lastIndexOf(".");

            String ext=name.substring(ind+1);
            String nameonly=name.substring(0,ind);

            String desname=nameonly+"_green."+ext;




            String des=fileStorageService.getDigitHumanStorageLocation().resolve( desname).toAbsolutePath().toString();
            System.out.println("generator iamge:"+des);

            File desFile=new File(des);

            if(!desFile.exists()) {
                BufferedImage bufferedImage= ImageIO.read(new File(file));
                BufferedImage bufferedImage1=new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D=bufferedImage1.createGraphics();
                graphics2D.setColor(Color.decode(GREENCOLOR));
                graphics2D.fillRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
                graphics2D.drawImage(bufferedImage,0,0,bufferedImage.getWidth(),bufferedImage.getHeight(),null);
                ImageIO.write(bufferedImage1, ext, desFile);
            }
            return  des;
        } catch (Exception e) {
            return file;
        }
    }
    class ExcuteThread extends  Thread{

        @Override
        public void run() {


            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                 e.printStackTrace();
            }

            while(true) {

                System.out.println("导出视频现线程开始启动");

                List<ExportVideoEntity> exportVideoEntities = exportVideoRepository.findNoDoneTask();
                if (exportVideoEntities.size() > 0) {

                    ExportVideoEntity exportVideoEntity = exportVideoEntities.remove(0);


                    List<PageEntity> pageEntities=pageRepository.findByPptid(exportVideoEntity.getPptid());

                    System.out.println("开始导出"+exportVideoEntity.getId()+" "+exportVideoEntity.getPptid()+"任务,页面数："+pageEntities.size()+" 时长:"+exportVideoEntity.getVideo_length()+" 剩余任务数："+exportVideoEntities.size());


                    Optional<FileEntity> fileEntity=fileRepository.findById(exportVideoEntity.getPptid());


                    if(fileEntity.isPresent()){
                        FileEntity fileEntity1 = fileEntity.get();
                        String url = fileEntity1.getFileurl();
                        String name = url.substring(url.indexOf("/"), url.lastIndexOf("."));



                        Path path=fileStorageService.getGeneratorVideoStorageLocation().resolve(exportVideoEntity.getId()+"");


                        try {
                            Files.createDirectories(path);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        System.out.println("ppt urlid:"+name+" save path:"+path.toAbsolutePath().toString());


                        boolean isallsuc=true;
                        StringBuffer sb=new StringBuffer();
                        for(int i=0;i<pageEntities.size();i++) {
                            PageEntity pageEntity = pageEntities.get(i);

                            String content=pageEntity.getPagecontent();

                            String picurl=pageEntity.getPageurl();

                            String imagefile=fileStorageService.getFileStorageLocation().resolve(picurl.substring(picurl.indexOf("/")+1)).toAbsolutePath().normalize().toString();




                            boolean issuc=true;

                           Optional<VoiceEntity> voiceEntity=voiceRepository.findById(pageEntity.getSoundid());


                           if(voiceEntity.isPresent()){
                               VoiceEntity voiceEntity1=voiceEntity.get();

                               String voiceurl=voiceEntity1.getAudio_url();
                               String promtcontent=voiceEntity1.getAudio_content();

                               String voicefile=fileStorageService.getVoiceStorageLocation().resolve(voiceurl.substring(voiceurl.indexOf("/")+1)).toAbsolutePath().normalize().toString();

                               String saveFile=path.resolve(pageEntity.getPageindex()+".mp3").toAbsolutePath().toString();


                               File audiofile=new File(saveFile);
                               if(!audiofile.exists()) {
                                   System.out.println(i + " 生成音频文件 " + aiServerProperties.getSoundserver() + " " + voicefile + " " + promtcontent + " " + content + " " + saveFile);

                                   String audiourl = generatorAudio(aiServerProperties.getSoundserver(), voicefile, promtcontent, content, saveFile);

                                   if(!audiourl.equals(saveFile)){
                                       issuc=false;
                                   }

                               }


                               Optional<DigitHumanEntity> digitHumanEntity=digitHumanRepository.findById(pageEntity.getDigitalhunmanid());

                               if(digitHumanEntity.isPresent()){
                                   DigitHumanEntity digitHumanEntity1=digitHumanEntity.get();
                                   String img=digitHumanEntity1.getPic_url();

                                   String imgpath=fileStorageService.getDigitHumanStorageLocation().resolve(img.substring(img.indexOf("/")+1)).toAbsolutePath().normalize().toString();

                                   double len =0;
                                   String vedourl=null;
                                   int isbgtrans = 0;
                                   if(digitHumanEntity1.getId()>0) {   //如果生成数字人

                                       //设置数字人背景绿色幕布

                                       if (pageEntity.getIsbgtransprent() != null && pageEntity.getIsbgtransprent() == 1) {
                                           imgpath = imageToGreen(imgpath);
                                           isbgtrans = 1;
                                       }
                                         vedourl = path.resolve(pageEntity.getPageindex() + ".mp4").toAbsolutePath().toString();


                                       File videofile = new File(vedourl);

                                       //根据数字人图片和音频生成数字人讲解小视频
                                       if (!videofile.exists()) {
                                           System.out.println(i + " 生成视频文件 " + aiServerProperties.getDigithumanserver() + " " + imgpath + " " + saveFile + " " + vedourl);

                                          String video= generatorVideo(aiServerProperties.getDigithumanserver() + "generator_video", imgpath, saveFile, vedourl);
                                          if(!video.equals(vedourl)){
                                              issuc=false;

                                          }
                                       }


                                         len = getMp4Duration(vedourl);

                                   }else{
                                       len=getMp3Duration(saveFile);
                                   }



                                   //如果ppt是图片，转视频 如果是视频不转换
                                   String bigvideo=imagefile;

                                   int[] dimion= {3840,2160};

                                   if(imagefile.indexOf(".png")>-1||imagefile.indexOf(".jpg")>-1) {
                                           bigvideo= path.resolve(pageEntity.getPageindex() + "b.mp4").toAbsolutePath().toString();

                                       Map<String, Object> params = new HashMap<>();
                                       params.put("image_path", imagefile);
                                       params.put("output_path", bigvideo);
                                       params.put("length", len);

                                       File bigVideo = new File(bigvideo);

                                       if (!bigVideo.exists()) {
                                           String res = sendHttpRequest(aiServerProperties.getDigithumanserver() + "img_video", params);
                                           System.out.println(i + " 生成大视频文件 " + aiServerProperties.getDigithumanserver() + " " + imagefile + " " + bigvideo + " " + len);
                                           if(!res.equals(bigvideo)){
                                               issuc=false;
                                           }
                                       }
                                   }else{
                                       dimion=getMp4Dimsion(bigvideo);
                                   }

                                   String desvideo=path.resolve(pageEntity.getPageindex()+"m.mp4").toAbsolutePath().toString();




                                   if(digitHumanEntity1.getId()>0) {

                                       double scalex=dimion[0]*1.0/3840;
                                       double scaley=dimion[1]*1.0/2160;
                                       Map params = new HashMap<>();
                                       params.put("big_video", bigvideo);
                                       params.put("video", vedourl);
                                       params.put("savevideo", desvideo);
                                       params.put("bgtrans", isbgtrans);  //数字人是否透明

                                       params.put("width", (int)(pageEntity.getDh_width()*scalex * 2));
                                       params.put("height", (int)(pageEntity.getDh_height()*scaley * 2));
                                       params.put("left", (int)(pageEntity.getDh_posleft()*scalex * 2));
                                       params.put("top", (int)(pageEntity.getDh_postop() *scaley* 2));
                                       File mergeVideo = new File(desvideo);

                                       if (!mergeVideo.exists()) {


                                           String res = sendHttpRequest(aiServerProperties.getDigithumanserver() + "video_merge", params);
                                           System.out.println(i + " 合并视频文件 " + aiServerProperties.getDigithumanserver() + " " + bigvideo + " " + vedourl + " " + desvideo
                                                   + " " + pageEntity.getDh_width() * 2 + " " + pageEntity.getDh_height() * 2 + " " + pageEntity.getDh_posleft() * 2 + " " + pageEntity.getDh_postop() * 2);

                                           if(!res.equals(desvideo)){
                                               issuc=false;
                                           }
                                        }

                                       if(issuc) {
                                           sb.append("file '" + pageEntity.getPageindex() + "m.mp4'\n");
                                       }
                                   }else{  //视频和音频合并
                                       Map params = new HashMap<>();
                                       params.put("big_video", bigvideo);
                                       params.put("audio", saveFile);
                                       params.put("savevideo", desvideo);

                                       File mergeVideo = new File(desvideo);

                                       if (!mergeVideo.exists()) {

                                           String res = sendHttpRequest(aiServerProperties.getDigithumanserver() + "video_audiomerge", params);
                                           System.out.println(i + " 合并视频文件 " + aiServerProperties.getDigithumanserver() + " " + bigvideo + " " + vedourl + " " + desvideo
                                                   + " " + pageEntity.getDh_width() * 4 + " " + pageEntity.getDh_height() * 4 + " " + pageEntity.getDh_posleft() * 4 + " " + pageEntity.getDh_postop() * 4);
                                           if(!res.equals(desvideo)){
                                               issuc=false;
                                           }
                                       }

                                       if(issuc) {
                                           sb.append("file '" + pageEntity.getPageindex() + "m.mp4'\n");
                                       }
                                   }


                               }
                           }



                           if(issuc) {

                               float len = fileEntity1.getVideocount();
                               int per = (int) ((pageEntity.getPagecontent().length() / 4.0) / len);
                               exportVideoEntity.setProcess(exportVideoEntity.getProcess() + per);
                               exportVideoRepository.save(exportVideoEntity);

                           }else{
                               isallsuc=false;
                           }


                        }


                        if(isallsuc) {
                            String videofile = path.resolve("video.txt").toAbsolutePath().toString();

                            try {
                                Files.write(Paths.get(videofile), sb.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String exportvideo = path.resolve("export.mp4").toAbsolutePath().toString();


                            Map params = new HashMap<>();
                            params.put("video_file", videofile);
                            params.put("savevideo", exportvideo);


                            File exportFile = new File(exportvideo);

                            boolean ishsuc=true;

                            if (!exportFile.exists()) {
                                String res = sendHttpRequest(aiServerProperties.getDigithumanserver() + "video_concat", params);
                                System.out.println(" 生成视频文件 " + aiServerProperties.getDigithumanserver() + " " + videofile + " " + exportvideo);
                                if(!res.equals(exportvideo)){

                                    ishsuc=false;
                                }
                            }

                            if(ishsuc) {
                                exportVideoEntity.setVideo_url(Constant.GENERATOR_VIDEO_URL + exportVideoEntity.getId() + "/export.mp4");

                                exportVideoEntity.setVideo_length((float) getMp4Duration(exportvideo));

                                exportVideoEntity.setDonedate(LocalDateTime.now());

                                exportVideoEntity.setProcess(100);
                                exportVideoRepository.save(exportVideoEntity);
                            }
                        }

                    }else{
                            exportVideoEntity.setProcess(1000);
                            exportVideoRepository.save(exportVideoEntity);
                            System.out.println("导出任务参数不正确, 导出下一个");

                    }

                } else {
                    System.out.println("没有导出任务,休息20秒重试");
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }


        }
    }



    /**
     * 获取视频文件的播放长度(mp4、mov格式)
     * @param videoPath
     * @return 单位为秒
     */
    public static double getMp4Duration(String videoPath) {
        try {
            IsoFile isoFile = new IsoFile(videoPath);


            long lengthInSeconds =
                    isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                            isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
            isoFile.close();
            return lengthInSeconds ;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }


    public static int[] getMp4Dimsion(String videoPath) {

        int width = 3840;
        int height = 2160;
        try {
            IsoFile isoFile = new IsoFile(videoPath);


            List<Box> boxes = isoFile.getMovieBox().getBoxes();
// 宽高时长获取


            for (Box box : boxes) {
                if (box instanceof TrackBox) {
                    TrackBox tBbx = (TrackBox) box;
                    width = (int) tBbx.getTrackHeaderBox().getWidth();
                    height = (int) tBbx.getTrackHeaderBox().getHeight();
                    break;
                }
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }

        int [] dimion={width,height};
        return dimion;
    }



    /**
     * 获取mp3语音文件播放时长(秒) mp3
     * @param filePath
     * @return
     */
    public static Integer getMp3Duration(String filePath){

        try {
            File mp3File = new File(filePath);
            MP3File f = (MP3File) AudioFileIO.read(mp3File);
            MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();
            return Integer.parseInt(audioHeader.getTrackLength()+"");
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }




    public static String generatorAudio(String urlStr, String audio_file,String promt,String content,String savefile ) {
        String res = "";
        HttpURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000000);
            conn.setReadTimeout(3000000);
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



            System.out.println("generator audio:"+res);

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



    public static String generatorVideo(String urlStr, String image_path,String audio_path, String output_path ) {
        String res = "";
        HttpURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(50000000);
            conn.setReadTimeout(300000000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");


            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=----footfoodapplicationrequestnetwork");

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text

            StringBuffer buffer = new StringBuffer();


                buffer.append("------footfoodapplicationrequestnetwork\r\n");
                buffer.append("Content-Disposition: form-data; name=\"");
                buffer.append("image_path");
                buffer.append("\"\r\n\r\n");
                buffer.append(image_path);
                buffer.append("\r\n");

            buffer.append("------footfoodapplicationrequestnetwork\r\n");
            buffer.append("Content-Disposition: form-data; name=\"");
            buffer.append("audio_path");
            buffer.append("\"\r\n\r\n");
            buffer.append(audio_path);
            buffer.append("\r\n");


            buffer.append("------footfoodapplicationrequestnetwork\r\n");
            buffer.append("Content-Disposition: form-data; name=\"");
            buffer.append("output_path");
            buffer.append("\"\r\n\r\n");
            buffer.append(output_path);
            buffer.append("\r\n");


            buffer.append("------footfoodapplicationrequestnetwork\r\n");
            buffer.append("Content-Disposition: form-data; name=\"");
            buffer.append("crop");
            buffer.append("\"\r\n\r\n");
            buffer.append(1);
            buffer.append("\r\n");

            buffer.append("------footfoodapplicationrequestnetwork\r\n");
            buffer.append("Content-Disposition: form-data; name=\"");
            buffer.append("dynamic_scale");
            buffer.append("\"\r\n\r\n");
            buffer.append(1);
            buffer.append("\r\n");


            buffer.append("------footfoodapplicationrequestnetwork--\r\n");



            out.write(buffer.toString().getBytes());

            // file




            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            res = builder.toString();



            reader.close();

            System.out.println("get res:"+res);

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



    public static String sendHttpRequest(String urlStr, Map<String,Object> params ) {
        String res = "";
        HttpURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(50000000);
            conn.setReadTimeout(300000000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");


            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=----footfoodapplicationrequestnetwork");

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text

            StringBuffer buffer = new StringBuffer();


            for (String key : params.keySet()) {

                Object value=params.get(key);

                buffer.append("------footfoodapplicationrequestnetwork\r\n");
                buffer.append("Content-Disposition: form-data; name=\"");
                buffer.append(key);
                buffer.append("\"\r\n\r\n");
                buffer.append(value);
                buffer.append("\r\n");

            }


            buffer.append("------footfoodapplicationrequestnetwork--\r\n");



            out.write(buffer.toString().getBytes());

            // file




            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            res = builder.toString();



            reader.close();

            System.out.println("get res:"+res);

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





    public  static  void  main(String[] args){


        generatorAudio("http://localhost:50000/genrator_audio","F:\\TraeWork\\PPTToVideoServer\\voicedir\\8bcbcb10-a001-4569-95ee-e45348712f6a.wav",
                "AI数字人 AI数字人","人文经济成为新增长点，常住人口预计破 370 万，技能人才增 7.8%  。文旅活动火爆，旅游收入和接待游客量大幅增长， “ 两新 ” 政策带动消费品销售额超 40 亿。工业投资高位增长，占固投比重升至 44% ，超 7 成规上工业企业数智化转型。 “3+3” 新型产业集群有 276 个项目落地。"
        ,"F:\\TraeWork\\PPTToVideoServer\\genrator-vedio-dir\\14\\22.mp3");
//        generatorVideo("http://localhost:40000/generator_video","F:\\TraeWork\\PPTToVideoServer\\digithumandir\\6dff3547-41d6-4c4a-b2cf-15e6481de711.png",
//                "F:\\TraeWork\\PPTToVideoServer\\voicedir\\11.mp3","F:\\TraeWork\\PPTToVideoServer\\genrator-vedio-dir\\1.mp4"
//                );
    }






}