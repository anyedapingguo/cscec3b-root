package com.cscec3b.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cscec3b.entity.basic.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseService<T> {
    public List<T> selectList(@Param("ew") Wrapper<T> queryWrapper);
}
