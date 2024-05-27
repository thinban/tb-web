package com.example.tbdevweb;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;

@Configuration
@MapperScan("com.example.tbdevweb.mapper")
public class MybatisPlusConfig {
    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 如果配置多个插件, 切记分页最后添加
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        return interceptor;
    }

    @Bean
    public DdlApplicationRunner ddlApplicationRunner(@Autowired(required = false) List ddlList) {
        return new DdlApplicationRunner(ddlList);
    }

    //基于反射构建
    public static QueryWrapper search(Class<?> usersClass, Object req) {
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        try {
//            Class<?> usersClass = req.getClass();
            Field[] fields = usersClass.getDeclaredFields();
            for (Field field : fields) {
                if (field != null) {
                    if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) continue;
                    field.setAccessible(true);
                    Object value = field.get(req);
                    if (value != null) {
                        Class<?> fieldType = field.getType();
                        String fieldName = field.getName();
                        if (fieldType == String.class) {
                            // 对于字符串类型，使用模糊查询
                            if (value instanceof String && ((String) value).contains("%")) {
                                queryWrapper.like(fieldName, value);
                            } else {
                                queryWrapper.eq(fieldName, value);
                            }
                        } else if (fieldType == Integer.class || fieldType == int.class) {
                            // 对于整型，使用等于查询
                            queryWrapper.eq(fieldName, (Integer) value);
                        } else if (fieldType == Long.class || fieldType == long.class) {
                            // 对于长整型，使用等于查询
                            queryWrapper.eq(fieldName, (Long) value);
                        } else if (fieldType == Double.class || fieldType == double.class) {
                            // 对于双精度浮点型，使用等于查询
                            queryWrapper.eq(fieldName, (Double) value);
                        } else if (fieldType == Float.class || fieldType == float.class) {
                            // 对于单精度浮点型，使用等于查询
                            queryWrapper.eq(fieldName, (Float) value);
                        } else if (fieldType == Date.class) {
                            // 对于日期类型，使用等于查询
//                            queryWrapper.eq(fieldName, (Date) value);
//                            if ("start_time".equals(fieldName) && value != null) {
//                                // 只查询大于等于start_time的记录
//                                queryWrapper.ge(fieldName, (Date) value);
//                            } else if ("end_time".equals(fieldName) && value != null) {
//                                // 只查询小于等于end_time的记录
//                                queryWrapper.le(fieldName, (Date) value);
//                            } else if ("start_time".equals(fieldName) && "end_time".equals(fieldName)) {
//                                // 如果同时有start_time和end_time，则进行时间范围查询
//                                queryWrapper.between(fieldName, (Date) params.get("start_time"), (Date) params.get("end_time"));
//                            }
                            System.out.println("时间的还没写");
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return queryWrapper;
    }

//【application.yml】
    //spring:
    //  datasource:
    //    url: jdbc:mysql://mysql:3307/mp1208?useUnicode=true&characterEncoding=UTF-8&tinyInt1isBit=false
    //    username: root
    //    password: AA123456
    //    driver-class-name: com.mysql.cj.jdbc.Driver
    //  data:
    //    redis:
    //      host: redis
    //      port: 6379
    //
    //logging:
    //  level:
    //    com.example.tbdevweb: debug
    //    org.springframework.web: DEBUG

//pom.xml
    //<?xml version="1.0" encoding="UTF-8"?>
    //<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    //         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    //    <modelVersion>4.0.0</modelVersion>
    //    <parent>
    //        <groupId>org.springframework.boot</groupId>
    //        <artifactId>spring-boot-starter-parent</artifactId>
    //        <version>3.2.6</version>
    //        <relativePath/> <!-- lookup parent from repository -->
    //    </parent>
    //    <groupId>com.example</groupId>
    //    <artifactId>tb-dev-web</artifactId>
    //    <version>0.0.1-SNAPSHOT</version>
    //    <name>tb-dev-web</name>
    //    <description>tb-dev-web</description>
    //    <properties>
    //        <java.version>17</java.version>
    //    </properties>
    //    <dependencies>
    //        <dependency>
    //            <groupId>org.springframework.boot</groupId>
    //            <artifactId>spring-boot-starter-data-redis</artifactId>
    //        </dependency>
    //        <dependency>
    //            <groupId>org.springframework.boot</groupId>
    //            <artifactId>spring-boot-starter-web</artifactId>
    //        </dependency>
    //        <dependency>
    //            <groupId>com.mysql</groupId>
    //            <artifactId>mysql-connector-j</artifactId>
    //            <scope>runtime</scope>
    //        </dependency>
    //        <dependency>
    //            <groupId>org.projectlombok</groupId>
    //            <artifactId>lombok</artifactId>
    //            <optional>true</optional>
    //        </dependency>
    //        <dependency>
    //            <groupId>org.springframework.boot</groupId>
    //            <artifactId>spring-boot-starter-test</artifactId>
    //            <scope>test</scope>
    //        </dependency>
    //        <dependency>
    //            <groupId>com.baomidou</groupId>
    //            <artifactId>mybatis-plus-boot-starter</artifactId>
    //            <version>3.5.4.1</version>
    //        </dependency>
    //        <dependency>
    //            <groupId>com.baomidou</groupId>
    //            <artifactId>mybatis-plus-boot-starter</artifactId>
    //            <version>3.5.4.1</version>
    //            <exclusions>
    //                <exclusion>
    //                    <groupId>org.mybatis</groupId>
    //                    <artifactId>mybatis-spring</artifactId>
    //                </exclusion>
    //            </exclusions>
    //        </dependency>
    //        <dependency>
    //            <groupId>org.mybatis</groupId>
    //            <artifactId>mybatis-spring</artifactId>
    //            <version>3.0.3</version>
    //        </dependency>
    //        <!-- https://mvnrepository.com/artifact/com.alibaba.fastjson2/fastjson2 -->
    //        <dependency>
    //            <groupId>com.alibaba.fastjson2</groupId>
    //            <artifactId>fastjson2</artifactId>
    //            <version>2.0.50</version>
    //        </dependency>
    //        <dependency>
    //            <groupId>org.example</groupId>
    //            <artifactId>tb-core</artifactId>
    //            <version>1.0-SNAPSHOT</version>
    //        </dependency>
    //    </dependencies>
    //
    //    <build>
    //        <plugins>
    //            <plugin>
    //                <groupId>org.springframework.boot</groupId>
    //                <artifactId>spring-boot-maven-plugin</artifactId>
    //                <configuration>
    //                    <excludes>
    //                        <exclude>
    //                            <groupId>org.projectlombok</groupId>
    //                            <artifactId>lombok</artifactId>
    //                        </exclude>
    //                    </excludes>
    //                </configuration>
    //            </plugin>
    //        </plugins>
    //    </build>
    //
    //</project>
}
