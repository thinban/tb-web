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
//                            if ("start_time".equals(fieldName)) {
//                                // 只查询大于等于start_time的记录
//                                queryWrapper.ge(fieldName, (Date) value);
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
}
