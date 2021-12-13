package com.yang.security.vo.user;

import lombok.Data;

import java.util.Set;

@Data
public class LoginVo {
    private String account;
    private String username;
    private Set<String> module;
}
