package com.zl.way;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * Caffeine缓存工厂类
 * 
 * @param <K>
 * @param <V>
 * @author xuzhongliang
 */
@Component("caffeine")
public class WayCaffeineCache<K, V> implements FactoryBean<LoadingCache<K, V>> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Class<?> getObjectType() {
        return LoadingCache.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public LoadingCache<K, V> getObject() {
        LoadingCache<K, V> loadingCache = Caffeine.newBuilder().initialCapacity(10).maximumSize(100)
            .expireAfterWrite(30, TimeUnit.MINUTES).expireAfterAccess(30, TimeUnit.MINUTES)
            .removalListener(
                (key, value, cause) -> System.out.println("key: " + key + ", value: " + value + ", cause: " + cause))
            .build(new CacheLoader<K, V>() {
                @Nullable
                @Override
                public V load(@NonNull K key) {
                    return null;
                }

                @Override
                public @NonNull Map<K, V> loadAll(@NonNull Iterable<? extends K> keys) {
                    return new HashMap<>(0);
                }

            });

        logger.debug("Caffeine加载完成。。。");
        return loadingCache;
    }
}
