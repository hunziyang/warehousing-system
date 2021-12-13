package com.yang.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.security.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-27 21:20:01
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRoleByAccount(@Param("account") String account);
}

