package com.info.controller;

import com.info.entity.User;
import com.info.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class HomeController {

    @Autowired
    private UserService userService;


    /**
     * 拦截登录
     */
    @GetMapping("")
    public String toLogin() {
        return "redirect:/";
    }


    /**
     * 登录
     */
    @PostMapping("")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, HttpServletRequest request) {

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        User user = userService.checkLogin(data);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(300);
            return "redirect:/user";
        }
        request.setAttribute("msg", "用户名或密码错误");
        return "index";
    }


    /**
     * 登出
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        return "index";
    }

}
