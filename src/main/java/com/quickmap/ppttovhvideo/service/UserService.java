package com.quickmap.ppttovhvideo.service;



import com.quickmap.ppttovhvideo.entity.UserEntity;
import com.quickmap.ppttovhvideo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;





    public List<UserEntity> deleteById(Integer id) {
        Optional<UserEntity> fileOptional = userRepository.findById(id);
        if (fileOptional.isPresent()) {
            UserEntity file = fileOptional.get();
            userRepository.delete(file);
        }

        return  userRepository.findAll();

    }


    public Optional<UserEntity> findById(Integer id) {
        return userRepository.findById(id);
    }


    public List<UserEntity> findByName(String name) {

         List<UserEntity> userEntityList= userRepository.findByName(name);
         return  userEntityList;
    }

    public UserEntity saveUser(UserEntity userEntity){
      return userRepository.save(userEntity);
    }

    public List<UserEntity> findByPhone(String name) {

        List<UserEntity> userEntityList= userRepository.findByPhone(name);
        return  userEntityList;
    }


    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
    

}