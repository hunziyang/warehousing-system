package com.yang.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * (UsersRole)实体类
 *
 * @author makejava
 * @since 2021-11-27 19:36:36
 */
@Data
@TableName("sys_users_role")
public class UsersRole implements Serializable {
    private static final long serialVersionUID = 920332597712727702L;
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;
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

