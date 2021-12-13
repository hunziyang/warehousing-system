package com.yang.security.cache.service;

import com.yang.security.dto.token.TokenDto;

public interface CacheService {
    void setJWTToken(String token, TokenDto tokenDto);
    TokenDto getJWTToken(String token);

}
