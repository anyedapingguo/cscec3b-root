package com.cscec3b.entity.basic;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@TableName("hr_user")
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {
    //    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "name", condition = SqlCondition.LIKE)
    private String name;
    private String code;
    private String password;
    private Integer sex;
    private Integer state;
    private LocalDateTime createTime;
    @TableField(condition = "%s&lt;#{%s}")
    private Integer age;
    private String email;
    private Long managerId;
    @Version
    private Long vno;

    //private transient String remark ;//不序列化
    @TableField(exist = false)
    private transient String remark;

}
