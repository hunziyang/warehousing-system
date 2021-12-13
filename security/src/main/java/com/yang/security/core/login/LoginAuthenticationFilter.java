package com.yang.security.core.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = true;

    public LoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login","POST"));
    }

    /**
     * 只允许 Post 请求的登录
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (postOnly && !"POST".equals(request.getMethod())){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> map = objectMapper.readValue(request.getInputStream(), Map.class);
        LoginToken loginToken = new LoginToken(map.get(usernameParameter),map.get(passwordParameter));
        // 将Authentication 交给 provide，并返回完整版的 将Authentication
        return super.getAuthenticationManager().authenticate(loginToken);
    }
}
