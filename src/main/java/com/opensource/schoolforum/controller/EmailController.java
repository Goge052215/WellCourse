package com.opensource.schoolforum.controller;


import com.opensource.schoolforum.annotate.PassToken;
import com.opensource.schoolforum.entity.Email;
import com.opensource.schoolforum.model.AddSuggestionsReq;
import com.opensource.schoolforum.model.R;
import com.opensource.schoolforum.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 邮件 前端控制器
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@RestController
@RequestMapping("/email")
@Api(value = "建议", tags = "建议")
@Slf4j
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.mailRecipient}")
    private String mailRecipient;

    @PostMapping("addSuggestions")
    @ApiOperation("建议")
    public R<?> addSuggestions(@RequestBody @Valid  AddSuggestionsReq addSuggestionsReq){
        emailService.sendSimpleMail(mailRecipient,addSuggestionsReq.getSubject(),addSuggestionsReq.getText());
        Email email = new Email();
        email.setSubject(addSuggestionsReq.getSubject());
        email.setText(addSuggestionsReq.getText());
        emailService.save(email);
        return R.success();
    }

}
