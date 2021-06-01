package com.pf.springboot;

import com.pf.springboot.mapper.UserMapper;
import com.pf.springboot.test.TestBean;
import com.pf.springboot.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    UserMapper userMapper;

    private static Integer num = 1;
    @Test
    void contextLoads() {
    }

    @Autowired
    TestBean testBean;

    @Test
    public void testMultiThread(){
        ExecutorService es1 = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            es1.execute(()-> num += 1);
        }
        System.out.println(num);
    }


    @Test
    public void test(){
        System.out.println(testBean.getBeanName());
    }
}
