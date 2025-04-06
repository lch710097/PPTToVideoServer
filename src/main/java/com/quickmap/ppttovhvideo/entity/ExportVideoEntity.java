package com.quickmap.ppttovhvideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exportvideo_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportVideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer pptid;


    private String video_url;

    private Float video_length;


    private Integer process;

    private Integer priority;


        private Integer userid;
    private LocalDateTime adddate;

    private LocalDateTime donedate;



    // 构造函数

    public ExportVideoEntity(Integer pptid,  Float video_length, Integer process, Integer priority, Integer userid ) {
        this.pptid = pptid;
        this.video_length = video_length;
        this.process = process;
        this.priority = priority;
        this.userid = userid;
        this.adddate =  LocalDateTime.now();
    }
}