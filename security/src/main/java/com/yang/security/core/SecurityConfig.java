package com.yang.security.core;

import com.yang.security.core.login.LoginAuthenticationFilter;
import com.yang.security.core.login.LoginAuthenticationProvider;
import com.yang.security.core.login.LoginFailureHandler;
import com.yang.security.core.login.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;

    /**
     * 对url的访问进行权限配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .disable()
                .formLogin()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/user/register").permitAll()
                // 剩下的允许地址为 swagger 相关地址
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    /**
     * 自定义Filter
     *
     * @return
     * @throws Exception
     */
    private LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter();
        loginAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        loginAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        // 设置 Filter 管理
        loginAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return loginAuthenticationFilter;
    }

    /**
     * AuthenticationManagerBuilder用于创建AuthenticationManager。 允许轻松构建内存身份验证，LDAP身份验证，基于JDBC的身份验证，添加UserDetailsService以及添加AuthenticationProvider。
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginAuthenticationProvider);
    }
}
