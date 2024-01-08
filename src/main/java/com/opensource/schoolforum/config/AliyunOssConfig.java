package com.opensource.schoolforum.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
// 指定配置文件中自定义属性前缀
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
@ToString
public class AliyunOssConfig {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String key;
}
