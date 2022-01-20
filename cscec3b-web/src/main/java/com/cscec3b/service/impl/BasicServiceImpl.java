package com.cscec3b.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cscec3b.entity.basic.User;
import com.cscec3b.mapper.basic.UserMapper;
import com.cscec3b.service.BasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BasicServiceImpl extends ServiceImpl<UserMapper,User> implements BasicService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getUsers() {
        List<User> users = userMapper.selectList(null);
        return users;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User getUserById(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public List<User> selectUserByIds(List<String> userIds) {
        return userMapper.selectBatchIds(userIds);
    }

    @Override
    public List<User> selectUserByMap(Map<String, Object> columnMap) {
        return null;
    }
}

