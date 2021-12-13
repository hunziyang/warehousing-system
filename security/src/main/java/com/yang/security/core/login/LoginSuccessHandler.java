package com.yang.security.core.login;

import com.alibaba.fastjson.JSON;
import com.yang.security.cache.service.CacheService;
import com.yang.security.dto.token.TokenDto;
import com.yang.security.entity.Users;
import com.yang.security.result.Result;
import com.yang.security.util.JwtUtils;
import com.yang.security.vo.user.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private CacheService cacheService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginToken loginToken = (LoginToken) authentication;
        TokenDto tokenDto = new TokenDto();
        tokenDto.setUsers(loginToken.getUsers());
        tokenDto.setSimpleGrantedAuthorities(loginToken.getGrantedAuthorities());
        Users users = loginToken.getUsers();
        String token = JwtUtils.sign(users.getAccount());
        cacheService.setJWTToken(token, tokenDto);
        response.setHeader(JwtUtils.AUTH_HEADER, String.format("%s%s", JwtUtils.TOKEN_PREFIX, token));
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) loginToken.getAuthorities();
        // 适配 Vue 的 meta
        Set<String> modules = new HashSet<>();
        authorities.stream().filter(simpleGrantedAuthority -> simpleGrantedAuthority != null)
                .forEach(simpleGrantedAuthority -> {
                    String authority = simpleGrantedAuthority.getAuthority();
                    if (authority.startsWith("ROLE")) {
                        return;
                    }
                    modules.add(authority.split(":")[0]);
                });
        LoginVo loginVo = new LoginVo();
        loginVo.setAccount(users.getAccount());
        loginVo.setUsername(users.getUsername());
        loginVo.setModule(modules);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(Result.success(loginVo)));
    }
}
