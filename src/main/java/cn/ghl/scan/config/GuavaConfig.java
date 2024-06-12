package cn.ghl.scan.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存Token 配置 后面可以替换成 Redis
 * @Author GengHongLiang
 * @Date 2024/5/30 9:04
 * @Version 1.0
 */
@Configuration
public class GuavaConfig {
    @Bean(name = "wxAccessToken")
    public Cache<String, String> wxAccessToken() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build();
    }

    @Bean(name = "openidToken")
    public Cache<String, String> openidToken() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }

}
