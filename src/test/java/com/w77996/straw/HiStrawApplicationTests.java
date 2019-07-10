package com.w77996.straw;

import com.w77996.straw.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HiStrawApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void contextLoads() {
        userMapper.getUserById(1);
    }

}
