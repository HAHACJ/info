package com.info.dao;

import com.info.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 用户查询
 */
public interface UserMapper {


    User findUser(Map<String, String> map);

    User findUserByAppKey(@Param("appKey") String appKey);

    List<User> findAllUser();

    void addUser(User user);


}
