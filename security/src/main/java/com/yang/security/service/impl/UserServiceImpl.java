package com.yang.security.service.impl;

import com.yang.security.entity.Users;
import com.yang.security.mapper.UsersMapper;
import com.yang.security.service.UserService;
import com.yang.security.util.SaltUtils;
import com.yang.security.vo.user.RegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Override
    public void register(RegisterVo registerVo) {
        Users users = new Users();
        BeanUtils.copyProperties(registerVo,users);
        String salt = SaltUtils.getSalt();
        users.setSalt(salt);
        users.setPassword(SaltUtils.encodePassword(users.getPassword(),salt));
        usersMapper.insert(users);
    }
}
