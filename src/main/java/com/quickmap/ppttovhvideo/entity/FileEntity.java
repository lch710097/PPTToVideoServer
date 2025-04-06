package com.quickmap.ppttovhvideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ppt_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    

    private String fileurl;
    
    private Integer pagecount;

    private Float videocount;

    private Integer userid;
    private LocalDateTime adddate;
    
    private String description;
    
    // 构造函数
    public FileEntity( String name, String fileurl , Integer userid,Integer pagecount,Float videocount) {

        this.name = name;
        this.fileurl = fileurl;
        this.adddate = LocalDateTime.now();
        this.userid = userid;
        this.pagecount = pagecount;
        this.videocount = videocount;
    }
}