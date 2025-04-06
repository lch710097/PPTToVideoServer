package com.quickmap.ppttovhvideo.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.quickmap.ppttovhvideo.bean.PPTBody;
import com.quickmap.ppttovhvideo.property.AIServerProperties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/tools")
public class PPTAIServerController {



    @Autowired
    private AIServerProperties aiServerProperties;




    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(600, TimeUnit.SECONDS)//设置连接超时时间
            .readTimeout(600, TimeUnit.SECONDS)//设置读取超时时间
            .build();


    @PostMapping("/aippt_outline")
    public ResponseEntity<StreamingResponseBody> getPPTStructFromAI(@RequestBody PPTBody pptBody) {

        System.out.println("get  aippt_outline server request name:"+pptBody.getContent()+" "+pptBody.getLanguage()+" "+pptBody.getModel()+" "+pptBody.getStream());

        String content=pptBody.getContent()+" 生成ppt目录 以#开头主题，以##开头为章    以###开头为节  以-开头为内容  ";
        StreamingResponseBody stream = pptOutLineDeekSeek(content,pptBody.getLanguage(),pptBody.getModel(),pptBody.getStream(),false);


        return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).contentType(MediaType.parseMediaType("text/plain;charset=UTF-8")).body(stream);

    }

    @PostMapping("/aippt")
    public ResponseEntity<StreamingResponseBody> getPPTPageFromAI(@RequestBody PPTBody pptBody) {

        System.out.println("get  aippt server request name:"+pptBody.getContent().substring(0,20)+" "+pptBody.getLanguage()+" "+pptBody.getModel()+" "+pptBody.getStream());
        String content=pptBody.getContent()+" 以#开头为PPT主题，以##开头为PPT章节    以###开头为PPT节  以-开头为PPT内容 生成ppt,每个PPT页返回JSON对象, 主题的type为cover,章节的type为transition 节的类型为content, 目录的type为contents 结束的type是end" +
                " 主题的data内容为 title 和 text title是标题 text 是内容  目录的data内容是items,items为目录字符串数组,章节的data内容为 title 和 text title是标题 text 是内容,节的data内容为 title 和items title是标题 items是数组，数组内是title 和text    输入的章节和内容都要返回,不要减少。返回纯json字符串  ";

        StreamingResponseBody stream = pptOutLineDeekSeek(content,pptBody.getLanguage(),pptBody.getModel(),pptBody.getStream(),true);


        return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM) .contentType(MediaType.parseMediaType("text/plain;charset=UTF-8")).body(stream);

    }


    public StreamingResponseBody pptOutLineDeekSeek(String content ,String language,String model ,boolean stream,boolean needjson) {



        if(language.equals("en")){
            content+=" 输出为英文";
        }else{
            content+=" 输出为中文";
        }


        String realmodel=aiServerProperties.getDeepseekmodel();

        if(!model.equals(realmodel)){
            System.out.println("不支持的模型："+model+" 使用"+realmodel);
        }


        JSONObject params=new JSONObject();

        params.put("model",realmodel);
        params.put("max_tokens",8192);

        params.put("temperature",0.6);
        JSONArray mess=new JSONArray();

        JSONObject role=new JSONObject();

        role.put("content","");
        role.put("role","system");

        mess.put(role);




        role=new JSONObject();

        role.put("content",content);
        role.put("role","user");

        mess.put(role);

        params.put("stream",true);

        params.put("messages",mess);

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");

        okhttp3.RequestBody  requestBody=okhttp3.RequestBody.create(mediaType,params.toString());

        Request request = new Request.Builder()
                .url(aiServerProperties.getDeepseekserver())
                .post(  requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer "+aiServerProperties.getDeepseekkey())
                .build();



        return outputStream2 -> {

            BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(outputStream2, StandardCharsets.UTF_8));

            String lastdata="";
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println(" response error:"+response.message());
                    outputStream.flush();
                    outputStream.close();
                }

                okhttp3.ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    BufferedSource source = responseBody.source();
                    while (!source.exhausted()) {
                        String line = source.readUtf8Line();

                        if (line != null && !line.isEmpty()) {
                            if (line.startsWith("data: ")) {
                                String data = line.substring(6).trim();
                                if (!"[DONE]".equals(data)) {
                                    JSONObject jsonObject= new JSONObject(data);

                                    if(jsonObject.has("choices")) {
                                        JSONArray choices = jsonObject.getJSONArray("choices");
                                        if(choices.length()>0){
                                            JSONObject jsonObject1=choices.getJSONObject(0);
                                            if(jsonObject1.has("delta")) {
                                                String aicontent = jsonObject1.getJSONObject("delta").getString("content");


                                                if(needjson) {
                                                    lastdata+=aicontent.replaceAll("\n"," ");


                                                    int startind=lastdata.indexOf("```json");
                                                    int endind=-1;
                                                    if(startind>=0&&lastdata.length()>8) {
                                                        endind=lastdata.indexOf("```", startind + 7);
                                                    }

                                               //      System.out.println("get ai data:"+aicontent+" sind:"+startind+" eind:"+endind+" alldata:"+lastdata);

                                                    while (startind>=0&&endind>startind){

                                                        String page=lastdata.substring(startind+7,endind).replaceAll("第[一二三四五六七八九十百千万零]+章", "");;


                                                        System.out.println("get ppt page data:"+page );
                                                        outputStream.write(page);
                                                        outputStream.flush();
                                                        if(lastdata.length()>endind+4){
                                                            lastdata = lastdata.substring(endind + 4);
                                                        }else {
                                                            lastdata="";

                                                        }
                                                        startind=lastdata.indexOf("```json");
                                                        endind=-1;
                                                        if(startind>=0&&lastdata.length()>8) {
                                                            endind=lastdata.indexOf("```", startind + 7);
                                                        }


                                                    }

                                                }else{
                                                    outputStream.write(aicontent);
                                                    outputStream.flush();
                                                }

                                            }
                                        }
                                    }
                                }else{
                                    outputStream.flush();
                                    outputStream.close();
                                    System.out.println("接收数据完成:"+data);
                                }
                            }
                        }
                    }
                }

            }catch (Exception ex){

                System.out.println("error:"+ex.getMessage());
                ex.printStackTrace();
                outputStream.close();
            }
        };
    }




    public List<String> pptOutLineDeekSeek_NoUse(String content ,String language,String model ,boolean stream){

          List<String> list=new ArrayList<>();



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


//                HttpResponse<String> response = Unirest.post(aiServerProperties.getDeepseekserver())
//                        .header("Content-Type", "application/json")
//                        .header("Accept", "application/json")
//                        .header("Authorization", "Bearer " +aiServerProperties.getDeepseekkey() )
//                        .body(
//                                params.toString()
//                        )
//                        .asString();

                HttpResponse<String> response = Unirest.post("https://api.deepseek.com/chat/completions")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer " +"sk-8d2036211cb641eabe74550d35e461d4" )
                        .body(
                                params.toString()
                        )
                        .asString();


                String res=response.getBody();

            //    System.out.println("get res from server:"+res);
                JSONObject jsonObject=new JSONObject(res);


                if(jsonObject.has("choices")) {
                    JSONArray choices = jsonObject.getJSONArray("choices");
                    if(choices.length()>0){
                        JSONObject jsonObject1=choices.getJSONObject(0);
                        String aicontent=jsonObject1.getJSONObject("message").getString("content");
                        System.out.println("get ai content:"+aicontent);
                        String[] rows=aicontent.split("\n");

                        for(int i=0;i<rows.length;i++){
                            String row=rows[i];


                                list.add(row.trim());

                        }





                    }
                }





            }catch (Exception ex){
                ex.printStackTrace();

            }


            return  list;

    }




    public  static  void main(String[] args){

        String lastdata="```json\n" +
                "{\n" +
                "  \"type\": \"cover\",\n" +
                "  \"data\": {\n" +
                "    \"title\": \"国防动员\",\n" +
                "    \"text\": \"国防动员概述、体系与机制、主要内容、实施与保障、新时代挑战与发展\"\n" +
                "  }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "```json\n" +
                "{\n" +
                "  \"type\": \"contents\",\n" +
                "  \"data\": {\n" +
                "    \"items\": [\n" +
                "      {\n" +
                "        \"title\": \"第一章 国防动员概述\",\n" +
                "        \"text\": \"基本概念、地位与作用\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"第二章 国防动员的体系与机制\",\n" +
                "        \"text\": \"组织体系、运行机制\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"第三章 国防动员的主要内容\",\n" +
                "        \"text\": \"人力动员、物资动员、科技与信息动员\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"第四章 国防动员的实施与保障\",\n" +
                "        \"text\": \"动员准备与实施流程、法律与政策保障\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"第五章 新时代国防动员的挑战与发展\",\n" +
                "        \"text\": \"新形势与新挑战、创新与发展方向\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "```json\n" +
                "{\n" +
                "  \"type\": \"transition\",\n" +
                "  \"data\": {\n" +
                "    \"title\": \"第一章 国防动员概述\",\n" +
                "    \"text\": \"介绍国防动员的基本概念及其在国家安全中的重要性\"\n" +
                "  }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "```json\n" +
                "{\n" +
                "  \"type\": \"content\",\n" +
                "  \"data\": {\n" +
                "    \"title\": \"1.1 国防动员的基本概念\",\n" +
                "    \"items\": [\n" +
                "      {\n" +
                "        \"title\": \"定义与内涵\",\n" +
                "        \"text\": \"国防动员是国家为应对战争或危机，调动全社会资源保障国家安全的行为。\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"历史沿革与发展\",\n" +
                "        \"text\": \"从古代兵役制度到现代综合动员体系，国防动员随战争形态演变而发展。\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "```json\n" +
                "{\n" +
                "  \"type\": \"content\",\n" +
                "  \"data\": {\n" +
                "    \"title\": \"1.2 国防动员的地位与作用\",\n" +
                "    \"items\": [\n" +
                "      {\n" +
                "        \"title\": \"国家安全与发展的重要保障\",\n" +
                "        \"text\": \"连接军事与经济体系的枢纽，是维护国家主权和领土完整的基石。\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"应对战争与危机的关键手段\",\n" +
                "        \"text\": \"通过快速资源转化能力提升国家战争潜力和应急响应效率。\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "```json\n" +
                "{\n" +
                "  \"type\": \"transition\",\n" +
                "  \"data\": {\n" +
                "    \"title\": \"第二章 国防动员的体系与机制\",\n" +
                "    \"text\": \"解析国防动员的组织架构和运行逻辑\"\n" +
                "  }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "```json\n" +
                "{\n" +
                "  \"type\": \"content\",\n" +
                "  \"data\": {\n" +
                "    \"title\": \"2.1 国防动员的组织体系\",\n" +
                "    \"items\": [\n" +
                "      {\n" +
                "        \"title\": \"领导机构与职责分工\",\n" +
                "        \"text\": \"中央军委国防动员部统筹，地方政府设立对应机构实施属地管理。\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"层级结构与协调机制\",\n" +
                "        \"text\": \"构建'中央-省-市-县'四级联动体系，建立跨部门联席会议制度。\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "```json\n" +
                "{\n" +
                "  \"type\": \"content\",\n" +
                "  \"data\": {\n" +
                "    \"title\": \"2.2 国防动员的运行机制\",\n" +
                "    \"items\": [\n" +
                "      {\n" +
                "        \"title\": \"预案制定与动态调整\",\n" +
                "        \"text\": \"建立分级分类预案库，每年进行修订和实战化演练。\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"平战转换与快速响应\",\n" +
                "        \"text\": \"设定蓝黄橙红四级响应机制，最短72小时完成战时体制转换。\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "```\n";


        int startind=lastdata.indexOf("```json");
        int endind=lastdata.indexOf("```",startind+7);

        System.out.println(startind+" "+endind);

        while (startind>=0&&endind>startind){

            String page=lastdata.substring(startind+7,endind);
            System.out.println("get ppt page data:"+page);

            lastdata=lastdata.substring(endind+4);
            startind=lastdata.indexOf("```json");
            endind=lastdata.indexOf("```",startind+7);

        }

        PPTAIServerController aiServerController=new PPTAIServerController();

     //   aiServerController.pptOutLineDeekSeek("国防动员办公室信息化系统","zh","deekspeek",true,false);

    }



}