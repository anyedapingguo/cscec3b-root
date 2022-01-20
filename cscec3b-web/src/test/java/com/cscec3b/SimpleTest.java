package com.cscec3b;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.cscec3b.entity.basic.User;
import com.cscec3b.mapper.basic.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SimpleTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        List<User> list = userMapper.selectList(null);
        log.info(JSON.toJSONString(list));
        Assert.assertEquals(5, list.size());
    }
}