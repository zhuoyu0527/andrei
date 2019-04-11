package com.telfa.andrei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

/**
 * Redis配置类
 * @since 1.8
 */
@Configuration
public class RedisConfig {

    /**
     * redis中保存对象时的序列化处理
     * @param factory redis连接工厂类
     * @return redis模板类
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new RedisSerializer<Object>() {

            @Override
            public byte[] serialize(Object o) throws SerializationException {
                if(!Objects.isNull(o)) {
                    try {
                        return new SerializingConverter().convert(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return new byte[0];
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                if(bytes != null && bytes.length > 0) {
                    try {
                        return new DeserializingConverter().convert(bytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });

        return redisTemplate;
    }

}
