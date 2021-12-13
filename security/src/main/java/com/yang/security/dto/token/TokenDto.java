package com.yang.security.dto.token;

import com.yang.security.entity.Users;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.List;

@Data
public class TokenDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Users users;
    private List<SimpleGrantedAuthority> simpleGrantedAuthorities;
}
