package com.example.tbdevweb.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName users
 */
@Data
@TableName("users")
public class Users implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String email;
    private String password;
    private static final long serialVersionUID = 1L;
}