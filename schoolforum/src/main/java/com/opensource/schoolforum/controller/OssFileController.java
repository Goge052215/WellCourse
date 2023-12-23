package com.opensource.schoolforum.controller;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.opensource.schoolforum.config.AliyunOssConfig;
import com.opensource.schoolforum.model.R;
import com.opensource.schoolforum.utils.AliyunOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@Controller
@Api(value = "图片附件", tags = "图片附件")
@Slf4j
public class OssFileController {



    @Autowired
    private AliyunOssUtil aliOssUtil;

    @Autowired
    private AliyunOssConfig aliyunOssConfig;


    /**
     * 上传图片
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("/upload")
    @ApiOperation("图片附件上传")
    public R<?> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            String visitUrl = aliOssUtil.uploadFile(file);
            Map<String,String> result = new HashMap<>(1);
            result.put("visitUrl", visitUrl);
            return R.success(result);
        }catch (Exception e){
            e.printStackTrace();
            return R.failure();
        }
    }

    /**
     * 删除图片
     * @param fileName 文件名称如：b8809d28-82ec-4b06-af5f-8d3d7a16107c.jpg
     * @return
     */
    @ResponseBody
    @GetMapping("/deleFile/{fileName}")
    @ApiOperation("图片附件删除")
    public R<?>  deleteFile(@PathVariable("fileName")String fileName){
        try {
            aliOssUtil.deleteFile(fileName);
            return R.success();
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure();
        }
    }

    @GetMapping("/downLoadFile")
    @ApiOperation("图片加载,附件下载")
    public void downLoadFile(HttpServletResponse response,@RequestParam("fileName")String fileName) throws IOException {

        String bucketName=aliyunOssConfig.getBucketName();
        OSSClient ossClient = getOssClient();
        String key = aliyunOssConfig.getKey();
        String saveFilePath = key + fileName;
        String endpoint = aliyunOssConfig.getEndpoint();
        //文件大小设置，可不用
        //response.addHeader("Content-Length", "" + buf.length);
        OSSObject ossObject = ossClient.getObject(bucketName, saveFilePath);
        String contentType = ossObject.getObjectMetadata().getContentType();
        //设置响应内容类型，当设置了ContentType为“image/jpg”时，浏览器可以直接显示图片；
        response.setContentType(contentType);
        String imgType = "image png tif jpg jpeg bmp";
        int lastDotIndex = fileName.lastIndexOf(".");
        String extension = fileName.substring(lastDotIndex + 1);
        if(!imgType.contains(extension)){
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        }
        //请求的输入流 和响应的输入流
        InputStream in = null;
        OutputStream toClient = null;
        try {
            toClient = new BufferedOutputStream(response.getOutputStream());
            byte[] buf = new byte[1024];
            in = ossObject.getObjectContent();
            int L;
            while ((L = in.read(buf)) != -1)
            {
                // if (buf.length != 0)
                // {
                toClient.write(buf, 0, L);
                // }
            }
        }catch (IOException e)
        {
            e.getMessage();
        }
        finally {
            in.close();
            // 写完以后关闭文件流
            toClient.flush();
            toClient.close();
        }
    }

    public OSSClient getOssClient()
    {
        String endpoint = aliyunOssConfig.getEndpoint();
        String accessKeyId=aliyunOssConfig.getAccessKeyId();
        String accessKeySecret= aliyunOssConfig.getAccessKeySecret();
        OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
        return  ossClient;
    }

}
