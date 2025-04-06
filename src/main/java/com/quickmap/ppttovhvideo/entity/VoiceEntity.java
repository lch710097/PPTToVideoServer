package com.quickmap.ppttovhvideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sound_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;


    private String audio_url;

    private String audio_content;



        private Integer userid;
    private LocalDateTime adddate;



    // 构造函数
    public VoiceEntity(String name, String audio_url ,String audio_content, Integer userid ) {

        this.name = name;
        this.audio_url = audio_url;
        this.adddate = LocalDateTime.now();
        this.userid = userid;
        this.audio_content = audio_content;
    }
}