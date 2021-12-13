package com.yang.security.core.login;

import com.yang.security.entity.Permission;
import com.yang.security.entity.Role;
import com.yang.security.entity.Users;
import com.yang.security.mapper.PermissionMapper;
import com.yang.security.mapper.RoleMapper;
import com.yang.security.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;


    /**
     * 获取用户的基本信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersMapper.getUsersByAccount(username);
        List<Role> roles = roleMapper.getRoleByAccount(username);
        List<Permission> permissions = permissionMapper.getPermissionByAccount(username);
        return new LoginUserDetail(users, roles, permissions);
    }
}
