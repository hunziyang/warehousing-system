package com.yang.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.security.entity.Users;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表(SysUsers)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-27 21:20:01
 */
public interface UsersMapper extends BaseMapper<Users> {

    Users getUsersByAccount(@Param("account") String account);
}

