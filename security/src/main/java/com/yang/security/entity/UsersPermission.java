package com.yang.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * (UsersPermission)实体类
 *
 * @author makejava
 * @since 2021-11-27 19:36:35
 */
@Data
@TableName("sys_users_permission")
public class UsersPermission implements Serializable {
    private static final long serialVersionUID = -37697695672134588L;
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
     * 权限ID
     */
    private Long permissionId;
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

