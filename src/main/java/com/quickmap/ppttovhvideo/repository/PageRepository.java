package com.quickmap.ppttovhvideo.repository;


import com.quickmap.ppttovhvideo.entity.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<PageEntity, Integer> {
    List<PageEntity> findByPagecontentContaining(String pagecontent);
    List<PageEntity> findByPptid(Integer pptid);
}