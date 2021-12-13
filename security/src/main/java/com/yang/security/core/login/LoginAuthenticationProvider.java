package com.yang.security.core.login;

import com.yang.security.entity.Users;
import com.yang.security.util.SaltUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginToken loginToken = (LoginToken) authentication;
        LoginUserDetail userDetails = (LoginUserDetail)userDetailsService.loadUserByUsername(loginToken.getUsername());
        if (!Optional.ofNullable(userDetails).isPresent()){
            throw new UsernameNotFoundException(String.format("%s%s",loginToken.getUsername(),"is not found"));
        }
        if (!userDetails.isAccountNonLocked()){
            throw new LockedException(String.format("%s%s",loginToken.getUsername(),"is locked"));
        }
        Users users = userDetails.getUsers();
        if (!users.getPassword().equals(SaltUtils.encodePassword(loginToken.getPassword(),users.getSalt()))){
            throw  new BadCredentialsException(String.format("%s%s",loginToken.getUsername()," password is err"));
        }
        userDetails.getUsers().setPassword("");
        loginToken.setPassword("");
        loginToken.setUsers(users);
        loginToken.setAuthenticated(true);
        loginToken.setGrantedAuthorities((List<SimpleGrantedAuthority>) userDetails.getAuthorities());
        return loginToken;
    }

    /**
     * 判断当前 provider 是否支持 LoginToken
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(LoginToken.class);
    }
}
