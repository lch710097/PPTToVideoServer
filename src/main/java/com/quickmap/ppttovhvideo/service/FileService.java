package com.quickmap.ppttovhvideo.service;

import com.quickmap.ppttovhvideo.entity.FileEntity;
import com.quickmap.ppttovhvideo.entity.PageEntity;
import com.quickmap.ppttovhvideo.repository.FileRepository;
import com.quickmap.ppttovhvideo.repository.PageRepository;
import com.quickmap.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    
    @Autowired
    private FileStorageService fileStorageService;


    @Autowired
    private FileTranformServer fileTranformServer;

    @Autowired
    private  PageRepository pageRepository;

    public Map saveFile(MultipartFile file, String description,int userid) {



        String fileName = fileStorageService.storeFile(file,description);



        Path path=fileStorageService.getFileStorageLocation().resolve(fileName).normalize();

    //    System.out.println("get name:  "+fileName+" "+   path.toUri().toString());

        int ind=description.lastIndexOf(".");

        String name=description;
        if(ind>-1){
            name=description.substring(0,ind);

        }

        FileEntity fileEntity = new FileEntity(
                name,
            Constant.UPLOAD_URL +fileName,
            userid,
            10,
                0.0f
        );
        
          fileRepository.save(fileEntity);

      List<PageEntity> pageEntities=  fileTranformServer.changePPTToPage(path);

        int count=0;
        for (PageEntity page:pageEntities  ) {

            count+=page.getPagecontent().length();
        }


        fileEntity.setVideocount(count/4.0f);
      fileEntity.setPagecount(pageEntities.size());

      int pptid=fileEntity.getId();

      for(int i=0;i<pageEntities.size();i++){
          pageEntities.get(i).setPptid(pptid);
      }
      fileRepository.save(fileEntity);

        pageRepository.saveAll(pageEntities);

        Map map=new HashMap();

        map.put("pages",pageEntities);
        map.put("ppt",fileEntity);
      return  map;


    }



    public  List<FileEntity> findByUserId(Integer id){
        return  fileRepository.findByUserid(id);
    }

    public Map savePage(MultipartFile file, int type,int pageid) {


        Map map=new HashMap();
        Optional<PageEntity> pageEntity=pageRepository.findById(pageid);

         if(pageEntity.isPresent()){
             PageEntity pageEntity1=pageEntity.get();
             Optional<FileEntity> fileEntity=fileRepository.findById(pageEntity1.getPptid());
             if(fileEntity.isPresent()){
                 FileEntity fileEntity1=fileEntity.get();
                 List<PageEntity> pageEntityList=pageRepository.findByPptid(fileEntity1.getId());

                 if(type==1){  //更新图片

                   String url=  fileStorageService.updateFile(pageEntity1.getPageurl(),file);

                   pageEntity1.setPageurl(url);
                   pageRepository.save(pageEntity1);


                 }else{

                     int pageind=pageEntity1.getPageindex();
                     if(type==3){
                         pageind+=1;
                     }


                     String imgurl= fileStorageService.saveImage(fileEntity1.getFileurl(),file);



                     PageEntity newpage=new PageEntity(fileEntity1.getId(),pageind,imgurl,"" );

                     pageRepository.save(newpage);

                     for (PageEntity page:  pageEntityList) {

                         if(page.getPageindex()>=pageind){
                             page.setPageindex(page.getPageindex()+1);
                             pageRepository.save(page);

                         }


                     }

                     int count=0;
                     for (PageEntity page:pageEntityList  ) {

                         count+=page.getPagecontent().length();
                     }


                     fileEntity1.setVideocount(count/4.0f);

                     fileEntity1.setPagecount(fileEntity1.getPagecount()+1);

                     fileRepository.save(fileEntity1);


                     pageEntityList.add( newpage);
                 }



                 map.put("pages",pageEntityList);
                 map.put("ppt",fileEntity1);
                 return  map;

             }
         }

         return  map;

    }
    

    public Optional<FileEntity> getFileById(Integer id) {
        return fileRepository.findById(id);
    }
    
    public List<FileEntity> searchFiles(String fileName) {
        return fileRepository.findByNameContaining(fileName);
    }


    public List<PageEntity> searchPages(Integer pptid) {
        return pageRepository.findByPptid(pptid);
    }

    public FileEntity updateFile(Integer id, String name) {
        Optional<FileEntity> fileOptional = fileRepository.findById(id);
        if (fileOptional.isPresent()) {
            FileEntity file = fileOptional.get();
            file.setName(name);
            return fileRepository.save(file);
        }
        return null;
    }


    public Map updatePage(Integer id, String content) {
        Map map=new HashMap();
        Optional<PageEntity> fileOptional = pageRepository.findById(id);
        if (fileOptional.isPresent()) {
            PageEntity file = fileOptional.get();
            file.setPagecontent(content);

            List<PageEntity> pageEntities=pageRepository.findByPptid(file.getPptid());

            Optional<FileEntity> fileEntity=fileRepository.findById(file.getPptid());
            if(fileEntity.isPresent()){
                FileEntity fileEntity1=fileEntity.get();
                int count=0;
                for (PageEntity page: pageEntities  ) {
                    count+=page.getPagecontent().length();
                }

                fileEntity1.setVideocount(count/4.0f);
                fileRepository.save(fileEntity1);
                map.put("ppt",fileEntity1);
            }
            pageRepository.save(file);
            map.put("page",file);
            return map;
        }
        return null;
    }


    public Map updatePageParam(Integer id, Integer voicid,boolean voiceforall ,Integer dgid,int left,int top ,int width,int hei,boolean dgforall ,boolean isbgtrans ) {
        Map map=new HashMap();
        Optional<PageEntity> fileOptional = pageRepository.findById(id);
        if (fileOptional.isPresent()) {
            PageEntity file = fileOptional.get();

            List<PageEntity> pageEntities=pageRepository.findByPptid(file.getPptid());
            boolean isall=false;
            if(voicid!=null) {
                if (voiceforall) {
                    for (PageEntity page : pageEntities
                    ) {
                        page.setSoundid(voicid);
                    }

                    isall=true;

                } else {
                    file.setSoundid(voicid);
                }
            }

            if(dgid!=null) {
                if (dgforall ) {
                    for (PageEntity page : pageEntities
                    ) {
                        page.setDigitalhunmanid(dgid);
                        page.setDh_posleft(left);
                        page.setDh_postop(top);
                        page.setDh_width(width);
                        page.setDh_height(hei);
                        page.setIsbgtransprent(isbgtrans?1:0);
                    }
                    isall=true;
                } else {
                    file.setDigitalhunmanid(dgid);
                    file.setDh_posleft(left);
                    file.setDh_postop(top);
                    file.setDh_width(width);
                    file.setDh_height(hei);
                    file.setIsbgtransprent(isbgtrans?1:0);
                }
            }

            if(isall){
                   pageRepository.saveAll(pageEntities);
            }else{
                pageRepository.save(file);
            }





            map.put("pages",pageEntities);
            return map;
        }
        return null;
    }
    
    public boolean deleteFile(Integer id) {
        Optional<FileEntity> fileOptional = fileRepository.findById(id);
        if (fileOptional.isPresent()) {
            FileEntity file = fileOptional.get();
            fileStorageService.deleteFile(file.getFileurl());
            fileRepository.delete(file);
            return true;
        }
        return false;
    }

    public FileEntity deletePage(Integer id) {
        Optional<PageEntity> fileOptional = pageRepository.findById(id);
        if (fileOptional.isPresent()) {
            PageEntity file = fileOptional.get();

            pageRepository.delete(file);
            List<PageEntity> pageEntityList=pageRepository.findByPptid(file.getPptid());

            Optional<FileEntity> fileEntity=fileRepository.findById(file.getPptid());
            if(fileEntity.isPresent()){
                FileEntity fileEntity1=fileEntity.get();
                fileEntity1.setPagecount(pageEntityList.size() );

                int count=0;
                for (PageEntity page: pageEntityList  ) {
                    count+=page.getPagecontent().length();
                }

                fileEntity1.setVideocount(count/4.0f);
                fileRepository.save(fileEntity1);

                return  fileEntity1;

            }
            return null;
        }
        return null;
    }
}