package com.quickmap.ppttovhvideo.repository;

import com.quickmap.ppttovhvideo.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    List<FileEntity> findByNameContaining(String name);

    List<FileEntity> findByUserid(Integer id);
}