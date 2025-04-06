package com.quickmap.utils;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class Util {


    public  static int getSessionUserId(HttpSession session){
        int userid=0;
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
        return  userid;
    }
}
