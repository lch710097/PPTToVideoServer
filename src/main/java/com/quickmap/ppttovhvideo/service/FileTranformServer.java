package com.quickmap.ppttovhvideo.service;

import com.quickmap.ppttovhvideo.entity.PageEntity;
import com.quickmap.utils.Constant;
import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.sl.usermodel.TextRun;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileTranformServer {


    @Autowired
    FileStorageService fileStorageService;

    public List<PageEntity> changePPTToPage(Path file){


        String name=file.getFileName().toString();
        int ind=name.lastIndexOf(".");

        String filename=name.substring(0,ind);
        String path=file.getParent()+ File.separator+ name.substring(0,ind);


        List<PageEntity> pageEntities=transPPTXToPic(file.toUri().toString(),path,filename);
        return  pageEntities;

    }




    public static List<PageEntity> transPPTXToPic(String url,String localPath,String filename) {
        try {
            if (url.toUpperCase().endsWith(".PPTX")) {
                return transPPTXToPicByPPTX(url,localPath,filename);
            } else if (url.toUpperCase().endsWith(".PPT")) {
                return transPPTToPicByPPt(url,localPath,filename);
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static  int SLIDESCALE=4;

    public static String getSlideText(HSLFSlide slide){

        HSLFNotes notes= slide.getNotes();


        String str="";
        if(notes!=null){
            List<List<HSLFTextParagraph>>  textGraphas=notes.getTextParagraphs();

            for(int i=0;i<textGraphas.size();i++){
                List<HSLFTextParagraph> textParagraphs=textGraphas.get(i);
                for(int j=0;j<textParagraphs.size();j++){
                    HSLFTextParagraph textParagraph=textParagraphs.get(j);
                   List<HSLFTextRun> textRuns= textParagraph.getTextRuns();
                   for(int k=0;k<textRuns.size();k++){
                       HSLFTextRun textRun=textRuns.get(k);
                       String text=textRun.getRawText();
                       if(text!=null&&text.length()>0) {
                           str += text + " \n";
                       }
                   }
                }
            }
        }else {
            List<List<HSLFTextParagraph>>  textGraphas=slide.getTextParagraphs();

            for(int i=0;i<textGraphas.size();i++){
                List<HSLFTextParagraph> textParagraphs=textGraphas.get(i);
                for(int j=0;j<textParagraphs.size();j++){
                    HSLFTextParagraph textParagraph=textParagraphs.get(j);
                    List<HSLFTextRun> textRuns= textParagraph.getTextRuns();
                    for(int k=0;k<textRuns.size();k++){
                        HSLFTextRun textRun=textRuns.get(k);
                        String text=textRun.getRawText();
                        if(text!=null&&text.length()>0) {
                            str += text + " \n";
                        }
                    }
                }
            }
        }
        return  str;
    }

    public static String getSlideTextX(XSLFSlide slide){




        String str="";
        XSLFNotes notes= slide.getNotes();
        if(notes!=null){
            List<List<XSLFTextParagraph>>  textGraphas=notes.getTextParagraphs();

            for(int i=0;i<textGraphas.size();i++){
                List<XSLFTextParagraph> textParagraphs=textGraphas.get(i);
                for(int j=0;j<textParagraphs.size();j++){
                    XSLFTextParagraph textParagraph=textParagraphs.get(j);
                    List<XSLFTextRun> textRuns= textParagraph.getTextRuns();
                    if(textRuns!=null) {
                        for (int k = 0; k < textRuns.size(); k++) {
                            XSLFTextRun textRun = textRuns.get(k);
                            String text = textRun.getRawText();
                            if (text != null && text.length() > 0) {
                                str += text + " \n";
                            }
                        }
                    }
                }
            }
        }else{
            List<XSLFShape> shapes = slide.getShapes();

            str= handleShapes(shapes);






        }

        return  str;
    }




    public static  String handleShapes(final List<XSLFShape> shapes){

        String str="";
        for (XSLFShape shape : shapes) {

            if (shape instanceof XSLFGroupShape) {
                XSLFGroupShape groupShape = ((XSLFGroupShape) shape);
                // 对XSLFGroupShape进行递归处理
                str+=handleShapes(groupShape.getShapes());
            } else if (shape instanceof XSLFTextShape) {



                XSLFTextShape xslfTextShape = ((XSLFTextShape) shape);
                String text=xslfTextShape.getText();

                if (text != null && text.length() > 0) {
                    str += text + " \n";
                }



            }
        }
        return  str;
    }

    // PPT输出为图片 hslf解析
    private static List<PageEntity> transPPTToPicByPPt(String url,String filePath,String filename) {

        List<PageEntity> outFileList = new ArrayList<>();
        // 生成输出
        String outRoot = filePath  + File.separator;
      //  System.out.printf("图片输出路径为:%s\n", outRoot);
        // 不存在则创建文件夹
        mkdir(outRoot);

        try {
            // 读取ppt
            HSLFSlideShow ppt = new HSLFSlideShow(new URL(url).openStream());

            /*
             * 解析PPT基本内容
             * */
            Dimension sheet = ppt.getPageSize();
            int width = sheet.width, height = sheet.height;
            List<HSLFSlide> pages = ppt.getSlides();

            System.out.printf("ppt基本信息: 共%s页,尺寸: %s , %s \n", pages.size(), width, height);

            BufferedImage img = new BufferedImage(width*SLIDESCALE, height*SLIDESCALE, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            int i = 1;
            // 逐页遍历
            for (HSLFSlide slide : pages) {
                // 清空画板





                graphics.setPaint(Color.white);
                if(i==1) {
                    graphics.scale(SLIDESCALE, SLIDESCALE);// 将图片放大times倍
                }
                graphics.fill(new Rectangle2D.Float(0, 0, width, height));
                slide.draw(graphics);



                String content=getSlideText(slide);
                String outFile = outRoot + i++ + ".png";
                PageEntity pageEntity=new PageEntity(0,(i-1), Constant.UPLOAD_URL+filename+"/"+(i-1)+".png",content);

                outFileList.add(pageEntity);

                // 输出为图片
                File f = new File(outFile);
           //     System.out.printf("输出图片：%s\n", f.getAbsolutePath());
                FileOutputStream fos = new FileOutputStream(f);
                javax.imageio.ImageIO.write(img, "PNG", fos);
                fos.close();
            }

            ppt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFileList;
    }

    // PPTX输出为图片 xmls包解析
    private static List<PageEntity> transPPTXToPicByPPTX(String url,String filePath,String filename) {
        List<PageEntity> outFileList = new ArrayList<>();
        // 生成输出
        String outRoot = filePath +File.separator;
      //  System.out.printf("图片输出路径为:%s\n", outRoot);
        // 不存在则创建文件夹
        mkdir(outRoot);

        try {
            // 读取ppt
            XMLSlideShow ppt = new XMLSlideShow(new URL(url).openStream());


            /*
             * 解析PPT基本内容
             * */
            Dimension sheet = ppt.getPageSize();
            int width = sheet.width, height = sheet.height;
            int count = ppt.getSlides().size();
            System.out.printf("ppt基本信息: 共%s页,尺寸: %s , %s \n", count, width, height);


            BufferedImage img = new BufferedImage(width*SLIDESCALE, height*SLIDESCALE, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            int i = 1;
            // 逐页遍历
            for (XSLFSlide shape : ppt.getSlides()) {

                // 清空画板
                graphics.setPaint(Color.white);
                if(i==1) {
                    graphics.scale(SLIDESCALE, SLIDESCALE);// 将图片放大times倍
                }
                graphics.fill(new Rectangle2D.Float(0, 0, width, height));
                shape.draw(graphics);

                String content= getSlideTextX(shape);
                String outFile = outRoot + i++ + ".png";
                PageEntity pageEntity=new PageEntity(0,i-1,Constant.UPLOAD_URL+filename+"/"+(i-1)+".png",content);

                outFileList.add(pageEntity);
                // 输出为图片
                File f = new File(outFile);
             //   System.out.printf("输出图片：%s\n", f.getAbsolutePath());
                FileOutputStream fos = new FileOutputStream(f);
                javax.imageio.ImageIO.write(img, "PNG", fos);
                fos.close();
            }

            ppt.close();
        } catch (Exception e) {
            System.err.println("======ppt转换异常");
            e.printStackTrace();
        }
        return outFileList;
    }

    // 生成文件夹
    private static void mkdir(String path) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
    }



}
