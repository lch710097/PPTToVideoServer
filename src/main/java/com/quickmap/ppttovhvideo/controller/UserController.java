package com.quickmap.ppttovhvideo.controller;


import com.alibaba.fastjson.JSONObject;

import com.quickmap.ppttovhvideo.entity.UserEntity;
import com.quickmap.ppttovhvideo.service.UserService;
import com.quickmap.utils.Constant;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;



    private  Map<String,Object> sessionCache=new HashMap<>();
    private  Map<String,Long> sessionCacheTime=new HashMap<>();
    @GetMapping("/api/@me")
    public ResponseEntity<String> getAboutMe(@RequestParam(required =false) String sessionid, HttpSession session) {



        System.out.println(session.getId());
        Object user=session.getAttribute(Constant.USER);
        if(user==null){
            if(sessionid!=null){

                Long time=sessionCacheTime.get(sessionid);
                if(time!=null&&System.currentTimeMillis()-time<30000){
                  Object map=  sessionCache.get(sessionid);

                  session.setAttribute(Constant.USER,map);
                  return  ResponseEntity.ok(map.toString());
                }
            }
            System.out.println("get  user sesion null");
            JSONObject map=new JSONObject();
            return  ResponseEntity.ok(map.toString());
        }else{
            System.out.println("get  user sesion");
            return  ResponseEntity.ok(user.toString());
        }



    }

    @GetMapping("/api/login")
    public ResponseEntity<String> logIn(@RequestParam String username,
                                             @RequestParam String password,
                                             HttpSession session) {
        JSONObject map=new JSONObject();

        System.out.println(session.getId());

        List<UserEntity> userEntityList=userService.findByPhone(username);
        if(userEntityList.size()<1){

            map.put("message","没有"+username+"用户");
        }else{
             boolean isfind=false;
            for(int i=0;i<userEntityList.size();i++){
                UserEntity userEntity=userEntityList.get(i);

                if(userEntity.getPassword().equals(password)){

                    isfind=true;
                    map.put("name",userEntity.getName());
                    map.put("phone",userEntity.getPhone());

                    map.put("adddate",userEntity.getAdddate());
                    map.put("updatedate",userEntity.getUpdatedate());
                    map.put("avatar",userEntity.getIconurl());
                    map.put("phone",userEntity.getPhone());
                    map.put("id",userEntity.getId());
                    map.put("preferred_username",userEntity.getName());
                    map.put("ok",isfind);

                    map.put("sessionid",session.getId());
                    session.setAttribute(Constant.USER,map);

                    sessionCache.put(session.getId(),map);
                    sessionCacheTime.put(session.getId(),System.currentTimeMillis());

                    System.out.println("add user sesion");
                }

            }

            if(!isfind){
                map.put("message",username+"密码不正确");
            }
        }
        return  ResponseEntity.ok(map.toString());

    }

    @GetMapping("/api/register")
    public ResponseEntity<String> registerUser(@RequestParam String username,
                                        @RequestParam String password,
                                               @RequestParam String phone
                                       ) {
         JSONObject map=new JSONObject();
         List<UserEntity> userEntityList=userService.findByPhone(phone);
         if(userEntityList.size()>0){
             map.put("msg","手机号码已经存在,不能注册");
         }else{
             UserEntity userEntity=new UserEntity(username,phone,password);

             userEntity=userService.saveUser(userEntity);

             if(userEntity!=null&&userEntity.getId()>0){
                 map.put("ok",true);
             }else {
                 map.put("msg","注册失败");
             }

         }
        return  ResponseEntity.ok(map.toString());

    }

    @GetMapping("/api/updateuser")
    public ResponseEntity<String> updateuser(@RequestParam String currentPassword,
                                               @RequestParam String newPassword,
                                              HttpSession session
    ) {


        int userid=-1;
        if(session!=null){
            Object obj=session.getAttribute(Constant.USER);
            if(obj!=null){
                Map map=(Map)obj;

                Object idobj=map.get("id");
                if(idobj!=null){
                    userid=Integer.parseInt(idobj.toString());
                }
            }
        }

        JSONObject map=new JSONObject();

        if(userid<0){
            map.put("msg","没有登录,请先登录");
        }else {

           Optional<UserEntity> userEntity= userService.findById(userid);
           if(userEntity.isPresent()){
               UserEntity userEntity1=userEntity.get();
               if(currentPassword.equals(userEntity1.getPassword())){

                   userEntity1.setPassword(newPassword);
                   userEntity1.setUpdatedate(LocalDateTime.now());
                   userService.saveUser(userEntity1);
                   map.put("ok",true);
               }else{
                   map.put("msg","密码不正确");
               }
           }else{
               map.put("msg","没有找到用户信息");
           }

        }
        return  ResponseEntity.ok(map.toString());

    }

    @GetMapping("/api/logout")
    public ResponseEntity<String> logOut(@RequestParam(required =false) String sessionid,
                                         HttpSession session) {
        JSONObject map=new JSONObject();
        if(sessionid!=null){
            sessionCache.remove(sessionid);
            sessionCacheTime.remove(sessionid);
        }
        session.removeAttribute(Constant.USER);
        return  ResponseEntity.ok(map.toString());
    }


    @GetMapping("/api/users")
    public List<UserEntity> getAll() {
        return userService.getAll();
    }




    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<List<UserEntity>> deleteFile(@PathVariable Integer id) {


        List<UserEntity> voiceEntities= userService.deleteById(id);

            return ResponseEntity.ok(voiceEntities) ;
    }





}