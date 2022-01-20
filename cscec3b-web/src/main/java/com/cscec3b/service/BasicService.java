package com.cscec3b.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cscec3b.entity.basic.User;

import java.util.List;
import java.util.Map;

public interface BasicService extends IService<User> {
    public List<User> getUsers();

    public int insertUser(User user);

    public User getUserById(String userId);

    public List<User> selectUserByIds(List<String> userIds);

    public List<User> selectUserByMap(Map<String, Object> columnMap);
}
