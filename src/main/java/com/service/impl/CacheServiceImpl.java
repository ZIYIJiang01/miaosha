package com.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.service.CacheService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {

    private Cache<String,Object> commonCache = null;

    @PostConstruct
    public void init() {
        commonCache = CacheBuilder.newBuilder()
//                set cache container initial capacity
                .initialCapacity(10)
//                set cache maximum store key, if exceed will remove it by LRU
                .maximumSize(100)
//                set expired time after write
                .expireAfterWrite(60, TimeUnit.SECONDS).build();

    }

    @Override
    public void setCommonCache(String key,Object value) {
        commonCache.put(key,value);
    }

    @Override
    public Object getFromCommonCache(String key) {
        return commonCache.getIfPresent(key);
    }
}
