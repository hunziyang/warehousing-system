package com.yang.security.core.login;

import com.yang.security.entity.Permission;
import com.yang.security.entity.Role;
import com.yang.security.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDetail implements UserDetails {
    private Users users;
    private List<Role> roles;
    private List<Permission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (Optional.ofNullable(roles).isPresent()) {
            roles.stream().filter(role -> role != null).forEach(role -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRoleKey());
                grantedAuthorities.add(simpleGrantedAuthority);
            });
        }
        if (Optional.ofNullable(permissions).isPresent()) {
            permissions.stream().filter(permission -> permission != null).forEach(permission -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission.getPermissionKey());
                grantedAuthorities.add(simpleGrantedAuthority);
            });
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !users.isAccountLock();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
