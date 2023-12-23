package com.opensource.schoolforum.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration

@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api(){
        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        //添加head参数end
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                //不显示错误的接口地址
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
                //.paths(PathSelectors.regex("/api/.*"))
                .build()
                .globalOperationParameters(pars)
                .apiInfo(adminApiInfo());
    }


   // @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                //只显示admin路径下的页面
                //.paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();

    }
    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("学校论坛管理系统-API文档")
                //描述信息
                .description("本文档描述了后台管理系统微服务接口定义")
                //版本号
                .version("1.0")
                //创建人
                //.contact(new Contact("atguigu", "http://atguigu.com", "XXXXX@qq.com"))
                .build();
    }
}
