package com.cscec3b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cscec3b.entity.basic.User;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBaseMapper<T> extends BaseMapper<T> {
}
