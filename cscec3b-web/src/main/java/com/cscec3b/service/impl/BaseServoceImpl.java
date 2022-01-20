package com.cscec3b.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cscec3b.entity.basic.User;
import com.cscec3b.mapper.MyBaseMapper;
import com.cscec3b.service.BaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseServoceImpl<T>implements BaseService<T> {
    @Autowired
    private MyBaseMapper<T> mapper;
    @Override
    public List<T> selectList(@Param("ew") Wrapper<T> queryWrapper) {
      return    mapper.selectList(queryWrapper);
    }

}
