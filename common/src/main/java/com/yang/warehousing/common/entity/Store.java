package com.yang.warehousing.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 仓库 (Store)实体类
 *
 * @author makejava
 * @since 2021-10-06 13:19:07
 */
@Data
@TableName("sys_store")
public class Store {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * STORE_NAME
     */
    private String storeName;
    /**
     * ADDRESS
     */
    private String address;
    /**
     * 乐观锁
     */
    @Version
    private Integer revision;
    /**
     * 创建人
     */
    private String createdBy;
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
     * 更新时间
     */
    @TableField(value = "UPDATED_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

}

