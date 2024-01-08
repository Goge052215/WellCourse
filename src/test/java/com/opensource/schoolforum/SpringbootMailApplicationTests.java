package com.opensource.schoolforum;

import com.opensource.schoolforum.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class SpringbootMailApplicationTests {

    @Autowired
    private EmailService mailService;

    // 邮件发送者
    @Value("${spring.mail.mailRecipient}")
    private String mailRecipient;

    private  String subject = "邮件发送测试";

    // 用于简单文本邮件测试
    private  String messageText = "这是一份测试邮件";

    // 用于HTML格式邮件测试
    private String htmlMessage ="<div>\n" +
            "    <p>这是一个HTML格式的邮件</p> <br>" +
            "    <img src=\"https://w.wallhaven.cc/full/p9/wallhaven-p9o51m.png\" width='500' height='300' /><br>\n" +
            "    <a href=\"https://www.yb2cc.cn\" title=\"码上就去学习\" target=\"_blank\">点我码上就去学习</a>\n" +
            "</div>";

    // 用于带有附件的邮件测试（文件路径）
//    String filePath = "C:\\Users\\Administrator\\Desktop\\初恋_林志美.mp3";
    String filePath = "E:\\idea_space_oneself\\压缩包资料\\spring-boot-realizes-sending-master.zip";


    /**
     * 测试简单文本邮件发送
     */
    @Test
    public void test1(){
        mailService.sendSimpleMail(mailRecipient,subject,messageText);
    }

    /**
     * 测试HTML格式邮件发送
     */
    @Test
    public void test2(){
        mailService.sendHtmlMail(mailRecipient,subject,htmlMessage);
    }

    /**
     * 测试带有附件邮件发送
     */
    @Test
    public void test3(){
        ArrayList<String> list = new ArrayList<>();
        list.add(filePath);
        mailService.sendAppendixMail(mailRecipient,subject,messageText,list);
    }
}

