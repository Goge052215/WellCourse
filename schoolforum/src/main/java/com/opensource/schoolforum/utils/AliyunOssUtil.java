package com.opensource.schoolforum.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import com.opensource.schoolforum.config.AliyunOssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * 对象存储工具类
 */
@Component
public class AliyunOssUtil {

    @Autowired
    private AliyunOssConfig aliyunOssConfig;





    /**
     * 创建一个OSS连接
     * @return OssClient
     */
    private OSS createOssClient(){
        String endpoint = "http://" + aliyunOssConfig.getEndpoint();
        String accessKeyId = aliyunOssConfig.getAccessKeyId();
        String accessKeySecret = aliyunOssConfig.getAccessKeySecret();
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 关闭连接
     * @param ossClient
     */
    private void close(OSS ossClient){
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

    /**
     * 上传文件
     * @param file File类型
     * @return
     */
    public String uploadFile(File file){
        String key = aliyunOssConfig.getKey();
        String bucketName = aliyunOssConfig.getBucketName();
        String endpoint = aliyunOssConfig.getEndpoint();
        //1、取得保存文件路径saveFilePath：拼接目标路径 + 图片名称
        String originFileName = file.getName();
        String filePrefix = UUID.randomUUID().toString();
        String saveFileName = filePrefix + originFileName.substring(file.getName().indexOf("."));
        String saveFilePath = key + saveFileName;
        OSS ossClient = null;
        try {
            ossClient = createOssClient();
            //2、上传文件
            //第一个参数为：桶的名称；第二个参数为路径+上你的文件名，如test/1.png，若是test/test2/1.png，也会给我们自动创建目录
            //第三个参数为：文件对象
            ossClient.putObject(new PutObjectRequest(bucketName,saveFilePath , file));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            this.close(ossClient);
        }
        //3、拼接获取公共访问地址
        String publicVisitPath = "http://" + bucketName + '.' +  endpoint + "/" + saveFilePath;
        return publicVisitPath;
    }



    /**
     * 上传文件
     * @param file MultipartFile类型
     * @return
     */
    public String uploadFile(MultipartFile file)throws Exception{
        String key = aliyunOssConfig.getKey();
        String bucketName = aliyunOssConfig.getBucketName();
        String endpoint = aliyunOssConfig.getEndpoint();
        //1、取得保存文件路径saveFilePath：拼接目标路径 + 图片名称
        String originFileName = file.getOriginalFilename();
        String filePrefix = UUID.randomUUID().toString();
        String saveFileName = filePrefix + originFileName.substring(originFileName.indexOf("."));
        String saveFilePath = key + saveFileName;
        OSS ossClient = null;
        PutObjectResult putObjectResult = null;
        try {
            ossClient = createOssClient();
            //2、上传文件
            //第一个参数为：桶的名称；第二个参数为路径+上你的文件名，如test/1.png，若是test/test2/1.png，也会给我们自动创建目录
            //第三个参数为：文件对象
            /**
             * 阿里云OSS 默认图片上传ContentType是image/jpeg
             * 图片链接后，图片是下载链接，而并非在线浏览链接
             * 这里在上传的时候要解决ContentType的问题，将其改为image/jpg
             */
            InputStream is = file.getInputStream();
            ObjectMetadata meta = new ObjectMetadata();
            //meta.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            meta.setObjectAcl(CannedAccessControlList.PublicRead);
            //缓存，可以预览
            //meta.setCacheControl("no-cache");
            //meta.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            //meta.setContentDisposition("inline;filename=" + saveFileName);
            //meta.setContentType(getcontentType(file.getName().substring(file.getName().lastIndexOf("."))));
            //meta.setContentType(getcontentType(file.getName().substring(file.getName().lastIndexOf("_")+1)));
            meta.setContentType(getcontentTypeOSS(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))));
           // meta.setContentType("image/jpg");
            PutObjectRequest putObjectRequest  =new PutObjectRequest(bucketName,saveFilePath , is, meta);
            putObjectRequest.setProcess("true");
            putObjectResult = ossClient.putObject(putObjectRequest);
            ossClient.setBucketAcl(bucketName,CannedAccessControlList.PublicRead);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            this.close(ossClient);
        }
        //3、拼接获取公共访问地址
        String publicVisitPath = "http://" + bucketName + '.' +  endpoint + "/" + saveFilePath;
        return saveFileName;
    }





    /**
     * 判断OSS服务文件上传时文件的类型contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentTypeOSS(String FilenameExtension) {
        if (".bmp".equalsIgnoreCase(FilenameExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(FilenameExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(FilenameExtension) ||
                ".jpg".equalsIgnoreCase(FilenameExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(FilenameExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(FilenameExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.visio";
        }
        if (".pptx".equalsIgnoreCase(FilenameExtension) ||
                ".ppt".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".docx".equalsIgnoreCase(FilenameExtension) ||
                ".doc".equalsIgnoreCase(FilenameExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(FilenameExtension)) {
            return "text/xml";
        }
        if (".png".equalsIgnoreCase(FilenameExtension)) {
            return "image/png";
        }
        return "image/jpg";
    }

    /**
     * 删除文件
     * @param fileName 待删除的文件名
     * @return
     */
    public void deleteFile(String fileName)throws Exception{
        String key = aliyunOssConfig.getKey();
        String bucketName = aliyunOssConfig.getBucketName();
        //1、拼接待删除路径
        String deleteFilePath = key + fileName;
        OSS ossClient = null;
        try {
            ossClient = createOssClient();
            //2、删除文件
            ossClient.deleteObject(bucketName, deleteFilePath);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            this.close(ossClient);
        }
    }

    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
       
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx"))
        {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }


    //通过该方法快速获取文件类型
    public static String getcontentTypes(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }





}
