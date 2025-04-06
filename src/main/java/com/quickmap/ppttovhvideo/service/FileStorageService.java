package com.quickmap.ppttovhvideo.service;

import com.quickmap.ppttovhvideo.exception.FileStorageException;
import com.quickmap.ppttovhvideo.exception.FileNotFoundException;
import com.quickmap.ppttovhvideo.property.FileStorageProperties;
import com.quickmap.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {


    private  final Path parentLocation;

    private final Path fileStorageLocation;
    private final Path voiceStorageLocation;
    private final Path digitHumanStorageLocation;
    private final Path generatorVideoStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        this.voiceStorageLocation = Paths.get(fileStorageProperties.getVoiceDir())
                .toAbsolutePath().normalize();

        this.digitHumanStorageLocation = Paths.get(fileStorageProperties.getDigithumanDir())
                .toAbsolutePath().normalize();

        this.generatorVideoStorageLocation = Paths.get(fileStorageProperties.getGenVedioDir())
                .toAbsolutePath().normalize();

        this.parentLocation=this.fileStorageLocation.getParent();

        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.voiceStorageLocation);
            Files.createDirectories(this.digitHumanStorageLocation);
            Files.createDirectories(this.generatorVideoStorageLocation);

        } catch (Exception ex) {
            throw new FileStorageException("无法创建文件上传目录", ex);
        }
    }

    public Path getParentLocation() {
        return parentLocation;
    }

    public Path getVoiceStorageLocation() {
        return voiceStorageLocation;
    }

    public Path getDigitHumanStorageLocation() {
        return digitHumanStorageLocation;
    }

    public Path getGeneratorVideoStorageLocation() {
        return generatorVideoStorageLocation;
    }

    public  Path getFileStorageLocation(){
        return  fileStorageLocation;
    }

    public String storeFile(MultipartFile file,String name) {
        // 生成唯一文件名
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        if(originalFileName.indexOf(".")<0){
            originalFileName=name;
        }

        System.out.println(originalFileName);
        String fileExtension = "";
        
        try {
            if (originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            
            String fileName = UUID.randomUUID().toString() + fileExtension;
            
            // 检查文件名是否包含无效字符
            if (fileName.contains("..")) {
                throw new FileStorageException("文件名包含无效路径序列 " + fileName);
            }

            // 复制文件到目标位置
            Path targetLocation = this.fileStorageLocation.resolve(fileName);


            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("无法存储文件 " + originalFileName, ex);
        }
    }


    public String storeVoiceFile(MultipartFile file) {
        // 生成唯一文件名
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";

        try {
            if (originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID().toString() + fileExtension;

            // 检查文件名是否包含无效字符
            if (fileName.contains("..")) {
                throw new FileStorageException("文件名包含无效路径序列 " + fileName);
            }

            // 复制文件到目标位置
            Path targetLocation = this.voiceStorageLocation.resolve(fileName);


            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("无法存储文件 " + originalFileName, ex);
        }
    }


    public String storeImageFile(MultipartFile file) {
        // 生成唯一文件名
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";

        try {
            if (originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID().toString() + fileExtension;

            // 检查文件名是否包含无效字符
            if (fileName.contains("..")) {
                throw new FileStorageException("文件名包含无效路径序列 " + fileName);
            }

            // 复制文件到目标位置
            Path targetLocation = this.digitHumanStorageLocation.resolve(fileName);


            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("无法存储文件 " + originalFileName, ex);
        }
    }

    public String saveImage(String url,MultipartFile file) {
        // 生成唯一文件名

        int ind=url.lastIndexOf("/");
        int end=url.lastIndexOf(".");
        String name=url.substring(ind+1,end);
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";

        try {
            if (originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID().toString() + fileExtension;

            // 检查文件名是否包含无效字符
            if (fileName.contains("..")) {
                throw new FileStorageException("文件名包含无效路径序列 " + fileName);
            }

            // 复制文件到目标位置
            Path targetLocation = this.fileStorageLocation.resolve(name+ File.separator+fileName);


            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return Constant.UPLOAD_URL+name+"/"+fileName;
        } catch (IOException ex) {
            throw new FileStorageException("无法存储文件 " + originalFileName, ex);
        }
    }

    public String updateFile(String url,MultipartFile file) {
        // 生成唯一文件名

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";


        try {



                if (originalFileName.contains(".")) {
                    fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                }

            String originalFileName2=url.replace(Constant.UPLOAD_URL,"");


            String path=originalFileName2.substring(0,originalFileName2.lastIndexOf("/"));

                String fileName = path+"/"+UUID.randomUUID().toString() + fileExtension;

                Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return Constant.UPLOAD_URL+fileName;
        } catch (IOException ex) {
            throw new FileStorageException("无法存储文件 " + url, ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("文件未找到 " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("文件未找到 " + fileName, ex);
        }
    }
    
    public boolean deleteFile(String fileName) {
        try {
            fileName=fileName.substring(fileName.indexOf("/")+1);

            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            String path=fileName.substring(0,fileName.lastIndexOf("."));

            Path imgPath=this.fileStorageLocation.resolve(path).normalize();

            System.out.println("delete ppt "+imgPath.toAbsolutePath().toString()+" "+filePath.toAbsolutePath().toString());

            deleteDirectory(imgPath.toFile());

            deleteDirectory(filePath.toFile());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
             return  false;
        }
    }


    public boolean deleteExportFile(String fileName) {
        try {

            Path filePath = this.generatorVideoStorageLocation.resolve(fileName).normalize();



            System.out.println("delete  export  "+filePath.toAbsolutePath().toString());

            deleteDirectory(filePath.toFile());


            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return  false;
        }
    }

    public boolean deleteVoiceFile(String fileName) {
        try {
            fileName=fileName.substring(fileName.indexOf("/")+1);

            Path filePath = this.voiceStorageLocation.resolve(fileName).normalize();
            System.out.println("delete voice "+filePath.toAbsolutePath().toString());
            deleteDirectory(filePath.toFile());
            return true;
        } catch (Exception ex) {
            return  false;
        }
    }

    public boolean deleteImageFile(String fileName) {
        try {
            fileName=fileName.substring(fileName.indexOf("/")+1);

            Path filePath = this.digitHumanStorageLocation.resolve(fileName).normalize();
            System.out.println("delete digithuman "+filePath.toAbsolutePath().toString());
            deleteDirectory(filePath.toFile());
            return true;
        } catch (Exception ex) {

            ex.printStackTrace();
            return  false;
        }
    }


    public static void deleteDirectory(File dir) {

        try {
            if (dir.isDirectory()) {
                // 获取目录下的所有文件和子目录
                File[] children = dir.listFiles();
                if (children != null) {
                    for (File child : children) {
                        // 递归删除子目录和文件
                        deleteDirectory(child);
                    }
                }
            }
            // 目录为空或只包含子目录（这些子目录也已被删除），现在删除该目录
            dir.delete();
        }catch (Exception e){

            e.printStackTrace();

        }
    }
}