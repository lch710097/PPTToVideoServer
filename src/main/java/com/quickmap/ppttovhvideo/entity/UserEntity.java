package com.quickmap.ppttovhvideo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    

    private String phone;

    private String password;
    

    private String iconurl;

    private Float videolen;
    private Float usevideolen;
    private LocalDateTime outdate;

    private LocalDateTime adddate;
    private LocalDateTime updatedate;

    public UserEntity(String name, String phone, String password) {

        this.name = name;
        this.phone = phone;
        this.password = password;
        this.adddate =  LocalDateTime.now();
        this.updatedate =  LocalDateTime.now();
    }

    public UserEntity(String name, String phone, String password, String iconurl) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.iconurl = iconurl;
        this.adddate =  LocalDateTime.now();
        this.updatedate =  LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public LocalDateTime getAdddate() {
        return adddate;
    }

    public void setAdddate(LocalDateTime adddate) {
        this.adddate = adddate;
    }

    public LocalDateTime getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(LocalDateTime updatedate) {
        this.updatedate = updatedate;
    }
}