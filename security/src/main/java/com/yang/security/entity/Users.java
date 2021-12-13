package com.yang.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户表(Users)实体类
 *
 * @author makejava
 * @since 2021-11-27 19:36:35
 */
@Data
@TableName("sys_users")
public class Users implements Serializable {
    private static final long serialVersionUID = -12043115918254334L;
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号锁
     */
    private boolean accountLock;
    /**
     * 乐观锁
     */
    private Integer revision;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建人ID
     */
    private Long createdByUserId;
    /**
     * 创建时间
     */
    @TableField(value = "CREATED_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新人ID
     */
    private Long updatedByUserId;
    /**
     * 更新时间
     */
    @TableField(value = "UPDATED_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}

