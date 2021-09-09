package com.decent.coupon.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * REDIS分布式锁
 *
 * @author sunxy
 * @date 2020/12/31
 */
@Slf4j
@Component
public class RedisTool {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /***
     * 加锁
     * @param key key
     * @param value 当前时间+超时时间(超时时间最好设置在10秒以上,保证在不同的项目获取到的时间误差在控制范围内)
     * @return 锁住返回true
     */
    public boolean lock(String key, String value) {
        try {
            //setNX 返回boolean
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, value);
            if (Objects.nonNull(aBoolean)) {
                return aBoolean;
            }
            //如果锁超时 ***
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
                //获取上一个锁的时间
                String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
                if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("加锁发生异常[{}]", e.getLocalizedMessage(), e);
        }
        return false;
    }

    /***
     * 解锁
     * @param key key
     * @param value value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (StringUtils.isBlank(currentValue)) {
                return;
            }
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.delete(key);
            }
        } catch (Exception e) {
            log.error("解锁异常[{}]", e.getLocalizedMessage(), e);
        }
    }

}