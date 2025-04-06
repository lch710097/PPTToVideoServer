package com.quickmap.ppttovhvideo.repository;


import com.quickmap.ppttovhvideo.entity.DigitHumanEntity;
import com.quickmap.ppttovhvideo.entity.VoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DigitHumanRepository extends JpaRepository<DigitHumanEntity, Integer> {

    public List<DigitHumanEntity> findByUserid(Integer id);
   
}