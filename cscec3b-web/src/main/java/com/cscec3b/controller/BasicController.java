package com.cscec3b.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cscec3b.common.JSONResult;
import com.cscec3b.entity.basic.User;
import com.cscec3b.mapper.basic.UserMapper;
import com.cscec3b.service.BasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/{version}/basic")
@RestController
@Slf4j
/* 类注解 */
@Api(value = "基础操作")
public class BasicController {
    @Autowired
    private BasicService basicService;

    /* 方法注解 */
    @ApiOperation(value = "获取用户", notes = "")
    @GetMapping("selectList")
    public JSONResult selectList() {
        List<User> users = userMapper.selectList(null);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapper")
    public JSONResult selectByWrapper() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapper2")
    public JSONResult selectByWrapper2() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.like("name", "雨").between("age", 20, 40).isNotNull("email");
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapper3")
    public JSONResult selectByWrapper3() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.likeRight("name", "王").or().ge("age", 25).orderByDesc("age")
                .orderByAsc("id");
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapper4")
    public JSONResult selectByWrapper4() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.apply("date_format(create_time,'%y-%m-%d')={0}", "2022-01-15")
                .inSql("manager_id", "select id from hr_user where name like '王%'");
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapper5")
    public JSONResult selectByWrapper5() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapper6")
    public JSONResult selectByWrapper6() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.likeRight("name", "王")
                .or(qw -> qw.lt("age", 40).gt("age", 20).isNotNull("email"));
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapper7")
    public JSONResult selectByWrapper7() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.nested(wq -> wq.lt("age", 40).or().isNotNull("email"))
                .likeRight("name", "王");
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapper8")
    public JSONResult selectByWrapper8() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.in("age", Arrays.asList(30, 31, 34, 35));
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }


    @GetMapping("selectByWrapper9")
    public JSONResult selectByWrapper9() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperSupper")
    public JSONResult selectByWrapperSupper() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.select("id", "name").like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperSupper2")
    public JSONResult selectByWrapperSupper2() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.like("name", "雨").lt("age", 40)
                .select(User.class, info -> !info.getColumn().equals("create_time")
                        && !info.getColumn().equals("manager_id"));
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperSupper3")
    public JSONResult selectByWrapperSupper3(String name, String email) {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.like(StringUtils.isNotEmpty(name), "name", name).like(StringUtils.isNotEmpty(email), "email", email);
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperEntity")
    public JSONResult selectByWrapperEntity() {
        User whereUser = new User();
        whereUser.setName("王天风");
        QueryWrapper<User> query = new QueryWrapper<User>(whereUser);
        query.like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperAllEq")
    public JSONResult selectByWrapperAllEq() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "王天风");
        params.put("age", null);
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.allEq((k, v) -> !k.equals("name"), params);
//        query.allEq(params,false);
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @Autowired
    private UserMapper userMapper;

    @GetMapping("selectByWrapperMaps")
    public JSONResult selectByWrapperMaps() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.like("name", "雨").lt("age", 40).select("id", "name");
        List<Map<String, Object>> users = userMapper.selectMaps(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperMaps2")
    public JSONResult selectByWrapperMaps2() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age)<{0}", 500);
        List<Map<String, Object>> users = userMapper.selectMaps(query);
        return JSONResult.ok(users);
    }

    @GetMapping("getUser")
    public JSONResult getUser() {
        List<User> users = basicService.getUsers();
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperObjs")
    public JSONResult selectByWrapperObjs() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.like("name", "雨").lt("age", 40);
        List<Object> users = userMapper.selectObjs(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperCount")
    public JSONResult selectByWrapperCount() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.like("name", "雨").lt("age", 40);
        Long count = userMapper.selectCount(query);
        return JSONResult.ok(count);
    }

    @GetMapping("selectByWrapperLambda")
    public JSONResult selectByWrapperLambda() {
//        LambdaQueryWrapper<User> query = new QueryWrapper<User>().lambda();
//        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<User>();
        LambdaQueryWrapper<User> query = Wrappers.<User>lambdaQuery();
        query.like(User::getName, "雨").lt(User::getAge, 40);
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperLambda2")
    public JSONResult selectByWrapperLambda2() {
        LambdaQueryWrapper<User> query = Wrappers.<User>lambdaQuery();
        query.likeRight(User::getName, "王")
                .and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> users = userMapper.selectList(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperLambda3")
    public JSONResult selectByWrapperLambda3() {
        List<User> userList = new LambdaQueryChainWrapper<User>(userMapper)
                .like(User::getName, "雨").ge(User::getAge, 20).list();
        return JSONResult.ok(userList);
    }

    @GetMapping("selectMy")
    public JSONResult selectMy() {
        LambdaQueryWrapper<User> query = Wrappers.<User>lambdaQuery();
        query.likeRight(User::getName, "王")
                .and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> users = userMapper.selectAll(query);
        return JSONResult.ok(users);
    }

    @GetMapping("selectByWrapperOne")
    public JSONResult selectByWrapperOne() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.like("name", "雨").lt("age", 40);
        User user = userMapper.selectOne(query);
        return JSONResult.ok(user);
    }

    @GetMapping("selectPage")
    public JSONResult selectPage() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.ge("age", 26);
        Page<User> page = new Page<User>(1, 2);
        IPage<User> iPage = userMapper.selectPage(page, query);
        return JSONResult.ok(iPage);
    }

    @GetMapping("selectMapsPage")
    public JSONResult selectMapsPage() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.ge("age", 26);
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(1, 2, false);
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, query);
        return JSONResult.ok(iPage);
    }

    @GetMapping("selectMyPage")
    public JSONResult selectMyPage() {
        QueryWrapper<User> query = new QueryWrapper<User>();
        query.ge("age", 26);
        Page<User> page = new Page<User>(1, 2);
        IPage<User> iPage = userMapper.selectUserPage(page, query);
        return JSONResult.ok(iPage);
    }

    @PostMapping("insertUser")
    public JSONResult insertUser() {
        User user = new User();
        user.setName("刘明强");
        user.setAge(26);
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        int rows = basicService.insertUser(user);
        log.info(JSON.toJSONString(rows));
        log.info(JSON.toJSONString(user));
        return JSONResult.ok(rows);
    }


    @PostMapping("updateUserById")
    public JSONResult updateUserById() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setAge(26);
        return JSONResult.ok(userMapper.updateById(user));
    }

    @PostMapping("updateUserWrapper")
    public JSONResult updateUserWrapper() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
        updateWrapper.eq("name", "李艺伟").eq("age", 28);
        User user = new User();
        user.setEmail("lyw2019@baomidou.com");
        user.setAge(29);
        return JSONResult.ok(userMapper.update(user, updateWrapper));
    }

    @PostMapping("updateUserWrapper2")
    public JSONResult updateUserWrapper2() {
        User whereUser = new User();
        whereUser.setName("李艺伟");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>(whereUser);
        updateWrapper.eq("name", "李艺伟").eq("age", 28);
        User user = new User();
        user.setEmail("lyw2019@baomidou.com");
        user.setAge(29);
        return JSONResult.ok(userMapper.update(user, updateWrapper));
    }

    @PostMapping("updateUserWrapper3")
    public JSONResult updateUserWrapper3() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
        updateWrapper.eq("name", "李艺伟").eq("age", 29).set("age", 30);
        return JSONResult.ok(userMapper.update(null, updateWrapper));
    }

    @PostMapping("updateUserWrapperLambda")
    public JSONResult updateUserWrapperLambda() {
        LambdaUpdateWrapper<User> lambdaUodate = Wrappers.<User>lambdaUpdate();
        lambdaUodate.eq(User::getName, "李艺伟").eq(User::getAge, 30).set(User::getAge, 31);
        return JSONResult.ok(userMapper.update(null, lambdaUodate));
    }

    @PostMapping("updateUserWrapperLambdaChain")
    public JSONResult updateUserWrapperLambdaChain() {
        boolean update = new LambdaUpdateChainWrapper<User>(userMapper)
                .eq(User::getName, "李艺伟").eq(User::getAge, 30).set(User::getAge, 31).update();
        return JSONResult.ok(update);
    }


    @PostMapping("deleteById")
    public JSONResult deleteById() {
        int rows = userMapper.deleteById(1482372568067133411L);
        return JSONResult.ok(rows);
    }

    @PostMapping("deleteByMap")
    public JSONResult deleteByMap() {
        Map<String, Object> columanMap = new HashMap<>();
        columanMap.put("name", "向前");
        columanMap.put("age", 31);
        int rows = userMapper.deleteByMap(columanMap);
        return JSONResult.ok(rows);
    }

    @PostMapping("deleteBatchIds")
    public JSONResult deleteBatchIds() {
        int rows = userMapper.deleteBatchIds(Arrays.asList(1482372568067133411L, 1482372568067133411L));
        return JSONResult.ok(rows);
    }

    @PostMapping("deleteWrapper")
    public JSONResult deleteWrapper() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery();
        lambdaQuery.eq(User::getAge, 27).or().ge(User::getAge, 41);
        int rows = userMapper.delete(lambdaQuery);
        return JSONResult.ok(rows);
    }

    @PostMapping("insertUserAR")
    public JSONResult insertUserAR() {
        User user = new User();
        user.setName("张草");
        user.setAge(24);
        user.setEmail("zc@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insert = user.insert();
        return JSONResult.ok(insert);
    }

    @PostMapping("selectUserByIdAR")
    public JSONResult selectUserByIdAR() {
        User user = new User();
        User userSelect = user.selectById(1482707248784207873L);
        return JSONResult.ok(userSelect);
    }

    @PostMapping("updateUserByIdAR")
    public JSONResult updateUserByIdAR() {
        User user = new User();
        user.setId(1482703223548334082L);
        user.setName("张草草");
        boolean updateById = user.updateById();
        return JSONResult.ok(updateById);
    }

    @PostMapping("deleteUserByIdAR")
    public JSONResult deleteUserByIdAR() {
        User user = new User();
        user.setId(1482703223548334082L);
        boolean deleteById = user.deleteById();
        return JSONResult.ok(deleteById);
    }

    @PostMapping("insertOrUpdateUserAR")
    public JSONResult insertOrUpdateUserAR() {
        User user = new User();
        user.setName("张磊");
        user.setAge(24);
        user.setEmail("zl@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insertOrUpdate = user.insertOrUpdate();
        return JSONResult.ok(user);
    }

    @PostMapping("insertOrUpdateUserAR2")
    public JSONResult insertOrUpdateUserAR2() {
        User user = new User();
        user.setId(1482707248784207873L);
        user.setAge(25);
        boolean insertOrUpdate = user.insertOrUpdate();
        return JSONResult.ok(insertOrUpdate);
    }

    @GetMapping("getUserOne")
    public JSONResult getUserOne() {
        User one = basicService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25), false);
        return JSONResult.ok(one);
    }

    @PostMapping("insertUserBatch")
    public JSONResult insertUserBatch() {
        User user1 = new User();
        user1.setName("徐丽1");
        user1.setAge(28);
        User user2 = new User();
        user2.setName("徐丽2");
        user2.setAge(29);
        List<User> userList = Arrays.asList(user1, user2);
        boolean saveBatch = basicService.saveBatch(userList);
        return JSONResult.ok(saveBatch);
    }

    @PostMapping("saveOrUpdateBatchUser")
    public JSONResult saveOrUpdateBatchUser() {
        User user1 = new User();
        user1.setName("徐丽3");
        user1.setAge(30);
        User user2 = new User();
        user2.setId(1482721082018209794L);
        user2.setAge(24);
        List<User> userList = Arrays.asList(user1, user2);
        boolean saveOrUpdate = basicService.saveOrUpdateBatch(userList);
        return JSONResult.ok(saveOrUpdate);
    }

    @PostMapping("queryChainUser")
    public JSONResult queryChainUser() {
        List<User> userList = basicService.lambdaQuery().gt(User::getAge, 25).like(User::getName, "雨").list();
        return JSONResult.ok(userList);
    }

    @PostMapping("updateChainUser")
    public JSONResult updateChainUser() {
        boolean update = basicService.lambdaUpdate().eq(User::getAge, 25).set(User::getAge, 26).update();
        return JSONResult.ok(update);
    }

    @PostMapping("deleteChainUser")
    public JSONResult deleteChainUser() {
        boolean remove = basicService.lambdaUpdate().eq(User::getAge, 25).remove();
        return JSONResult.ok(remove);
    }
}
