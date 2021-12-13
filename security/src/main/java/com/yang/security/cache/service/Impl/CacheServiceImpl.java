package com.yang.security.cache.service.Impl;

import com.yang.security.cache.KeyMap;
import com.yang.security.cache.service.CacheService;
import com.yang.security.dto.token.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void setJWTToken(String token, TokenDto tokenDto) {
        String key = String.format("%s%s", KeyMap.JWT, token);
        redisTemplate.opsForValue().set(key, tokenDto);
        redisTemplate.expire(key, 30l, TimeUnit.MINUTES);
    }

    @Override
    public TokenDto getJWTToken(String token) {
        String key = String.format("%s%s", KeyMap.JWT, token);
        if (!redisTemplate.hasKey(key)) {
            return null;
        }
        return (TokenDto) redisTemplate.opsForValue().get(key);
    }
}
