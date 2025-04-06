package com.quickmap.ppttovhvideo.repository;




import com.quickmap.ppttovhvideo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    List<UserEntity> findByName(String name);

    List<UserEntity> findByPhone(String phone);

}