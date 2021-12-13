package com.yang.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.security.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysPermission)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-27 21:20:01
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> getPermissionByAccount(@Param("account") String account);
}

