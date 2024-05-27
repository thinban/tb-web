package com.example.tbdevweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tbdevweb.mapper.UsersMapper;
import com.example.tbdevweb.domain.Users;
import com.example.tbdevweb.service.UsersService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2024-05-27 21:49:31
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {
    @SuppressWarnings("unchecked")
    public List<Users> selectByParams(Map<String, Object> params) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        Class<Users> entityClass = getEntityClass();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            try {
                Field field = entityClass.getDeclaredField(fieldName);
                if (field != null) {
                    Class<?> fieldType = field.getType();
                    if (value != null) {
                        if (fieldType == String.class) {
                            // 对于字符串类型，使用模糊查询
                            queryWrapper.like(fieldName, value.toString());
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
                            queryWrapper.eq(fieldName, (Date) value);
                        }
                        // 可以添加更多的类型判断和查询条件
                    }
                }
            } catch (NoSuchFieldException e) {
                // 处理异常，例如字段不存在的情况
            }
        }
        return this.list(queryWrapper);
    }

}




