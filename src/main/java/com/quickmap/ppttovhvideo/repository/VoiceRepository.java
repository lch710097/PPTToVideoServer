package com.quickmap.ppttovhvideo.repository;


import com.quickmap.ppttovhvideo.entity.PageEntity;
import com.quickmap.ppttovhvideo.entity.VoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoiceRepository extends JpaRepository<VoiceEntity, Integer> {

    public  List<VoiceEntity> findByUserid(Integer id);

}