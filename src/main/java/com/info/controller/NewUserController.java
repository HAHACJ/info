package com.info.controller;

import com.info.entity.Result;
import com.info.entity.User;
import com.info.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 管理员添加新的用户
 */
@Controller
@RequestMapping("/admin")
public class NewUserController {

    private UserService userService;

    @GetMapping("/find/all")
    @ResponseBody
    public Result<List<User>> findAllUser() {

        List<User> userList = userService.findAllUser();
        return new Result<>(200, "ok", userList);
    }


    @GetMapping("/add")
    public void addUser(User user) {
        userService.addUser(user);
    }

}
