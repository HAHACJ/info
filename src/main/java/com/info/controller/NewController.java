package com.info.controller;

import com.info.entity.Record;
import com.info.entity.User;
import com.info.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 录入信息
 */
@Controller
@RequestMapping("/new")
public class NewController {


    @Autowired
    private NewService newService;


    /**
     * 录入页面
     *
     * @return
     */
    @RequestMapping()
    public String newPage() {
        return "new";
    }

    @PostMapping("/info")
    public String newInfo(Record record, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        newService.addInfo(record, user);

        return "redirect:/new";
    }

}
