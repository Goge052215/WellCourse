package com.opensource.schoolforum.generator;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MpGenerator {

    private static final String projectPath = System.getProperty("user.dir");//获取项目路径 C:\workspace\springboot-demo
    private static final String url = "jdbc:mysql://111.231.172.99:3306/schoolforum?userSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";//jdbc url
    private static final String username = "root";//数据库账号
    private static final String password = "schoolABC_123";//数据库密码
    private static final String parentPackageName = "com.opensource.schoolforum";// 设置父包名
    private static final String moduleName = "";// 设置父包模块名
    private static final String writer = "";// 设置作者
    private static final String outPath = projectPath + "\\src\\main\\java\\";//输出路径
    private static final String mapperPath = projectPath + "\\src\\main\\resources\\mapper\\";// 设置mapperXml 模板路径
    //private static List<String> tableNames = Arrays.asList("user","post","likecollection","comment","mailconfiguration","email","school","discipline","plug");//需要生成的表名
    private static List<String> tableNames = Arrays.asList("sensitive");//需要生成的表名


    public static void main(String[] args) {


        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(writer) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outPath) // 指定输出目录
                            .disableOpenDir();//禁止打开输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(parentPackageName) // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperPath)); // 设置mapperXml生成路径

                })

                .strategyConfig(builder -> {
                    builder.addInclude(tableNames) // 设置需要生成的表名
                            //.addTablePrefix("t_") // 设置过滤表前缀
                            //Service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService")//格式化 service 接口文件名称
                            .formatServiceImplFileName("%sServiceImpl")//格式化 service 实现类文件名称
                            //Entity 策略配置
                            .entityBuilder()
                            .enableChainModel()//开启链式模型
                            .enableLombok()//开启Lombok模型
                            .enableTableFieldAnnotation()//开启生成实体时生成字段注解
//                            .logicDeleteColumnName("deleted")//默认删除属性名称（数据库）
//                            .logicDeletePropertyName("deleted")//默认删除属性名称（实体）
//                            .versionColumnName("version")//乐观锁属性名（数据库）
//                            .versionPropertyName("version")//乐观锁属性名（实体）
//                            .addTableFills(new Column("create_Time", FieldFill.INSERT))//添加表字段填充(自动填充)
//                            .addTableFills(new Column("update_Time", FieldFill.INSERT_UPDATE))//添加表字段填充（自动填充）
                            //controller 策略配置
                            .controllerBuilder()
                            .enableRestStyle();//开启生成@RestController 控制器
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}