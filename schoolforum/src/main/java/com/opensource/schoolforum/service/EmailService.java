package com.opensource.schoolforum.service;

import com.opensource.schoolforum.entity.Email;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 邮件 服务类
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
public interface EmailService extends IService<Email> {

    /** 1
     * @description 发送简单文本邮件
     * @param mailRecipient  邮件接收方
     * @param subject  邮件主题
     * @param messageText  HTML格式的邮件文本内容
     */
    void sendSimpleMail(String mailRecipient,String subject ,String messageText);

    /** 2
     * @description 发送HTML格式邮件
     * @param mailRecipient 邮件接收方
     * @param subject 邮件主题
     * @param htmlText HTML格式的邮件文本内容
     */
    void sendHtmlMail(String mailRecipient,String subject , String  htmlText);


    /** 3
     * @description 发送包含附件的邮件
     * @param mailRecipient 邮件接收方
     * @param subject 邮件主题
     * @param messageText 邮件文本内容
     * @param filePathList 附件（文件路径集合）
     */
    void sendAppendixMail(String mailRecipient , String subject , String messageText , List<String> filePathList);
}
