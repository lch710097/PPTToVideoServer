package com.quickmap.ppttovhvideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "digital_human_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitHumanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;


    private String pic_url;



        private Integer userid;
    private LocalDateTime adddate;



    // 构造函数
    public DigitHumanEntity(String name, String pic_url ,   Integer userid ) {

        this.name = name;
        this.pic_url = pic_url;
        this.adddate = LocalDateTime.now();
        this.userid = userid;
    }
}