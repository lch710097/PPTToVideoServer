package com.quickmap.ppttovhvideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ppt_page_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer pptid;
    private Integer pageindex;
    private  String pageurl;
    private  String pagecontent;
    private  Integer soundid;
    private Integer digitalhunmanid;
    private Integer dh_posleft;
    private  Integer dh_postop;
    private  Integer dh_width;
    private  Integer dh_height;

    private  Float vediolength;


    private  Integer isbgtransprent;

    public PageEntity(Integer pptid, Integer pageindex, String pageurl, String pagecontent) {
        this.pptid = pptid;
        this.pageindex = pageindex;
        this.pageurl = pageurl;
        this.pagecontent = pagecontent;

        this.soundid = 1;
        this.digitalhunmanid = 1;
        this.dh_posleft = 1;
        this.dh_postop = 1;
        this.dh_width = 300;
        this.dh_height = 300;
        this.vediolength = 0.0f;

        this.isbgtransprent=0;
    }

    public PageEntity(Integer pptid, Integer pageindex, String pageurl, String pagecontent,Integer isbgtransprent) {
        this.pptid = pptid;
        this.pageindex = pageindex;
        this.pageurl = pageurl;
        this.pagecontent = pagecontent;

        this.soundid = 1;
        this.digitalhunmanid = 1;
        this.dh_posleft = 1;
        this.dh_postop = 1;
        this.dh_width = 300;
        this.dh_height = 300;
        this.vediolength = 0.0f;

        this.isbgtransprent=isbgtransprent;
    }
// 构造函数



    public PageEntity(Integer pptid, Integer pageindex, String pageurl, String pagecontent, Integer soundid,
                      Integer digitalhunmanid, Integer dh_posleft, Integer dh_postop, Integer dh_width, Integer dh_height, Float vediolength) {
        this.pptid = pptid;
        this.pageindex = pageindex;
        this.pageurl = pageurl;
        this.pagecontent = pagecontent;
        this.soundid = soundid;
        this.digitalhunmanid = digitalhunmanid;
        this.dh_posleft = dh_posleft;
        this.dh_postop = dh_postop;
        this.dh_width = dh_width;
        this.dh_height = dh_height;
        this.vediolength = vediolength;
    }
}