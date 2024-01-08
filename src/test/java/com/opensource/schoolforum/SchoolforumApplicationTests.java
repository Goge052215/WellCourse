package com.opensource.schoolforum;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.dto.MessageDto;
import com.opensource.schoolforum.entity.Plug;
import com.opensource.schoolforum.entity.User;
import com.opensource.schoolforum.model.PagerModel;
import com.opensource.schoolforum.service.EmailService;
import com.opensource.schoolforum.service.MessageService;
import com.opensource.schoolforum.service.PlugService;
import com.opensource.schoolforum.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootTest
@Slf4j
class SchoolforumApplicationTests {

    @Autowired
    private UserService  userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private PlugService plugService;



    @Test
    void messageTest(){
        Page<MessageDto> page = new Page(1, 10);
        PagerModel<MessageDto> messagePagerMode = messageService.messagePager(page,1L,3);
    }

    @Test
    void queryPlugListFromDiscipline (){
        List<Plug> plugs  = plugService.queryPlugListFromDiscipline(1L);
        System.out.println(plugs.size());
    }


    @Test
    void contextLoads() {
       List<User>  listUser = userService.list();
       log.info("3333333"+listUser.get(0).getUseremail());
    }

    @Autowired
    private EmailService emailService;
    @Test
    void sendMail(){

        emailService.sendSimpleMail("","验证码","121212");
    }

}
