package com.quickmap.ppttovhvideo.repository;


import com.quickmap.ppttovhvideo.entity.ExportVideoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExportVideoRepository extends JpaRepository<ExportVideoEntity, Integer> {




    public List<ExportVideoEntity> findExportVideoEntitiesByPptid(Integer id);

    @Query(value = "select * from exportvideo_table where process<100 or video_url is null order by priority desc",nativeQuery = true)
    public List<ExportVideoEntity> findNoDoneTask( );



}