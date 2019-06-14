package com.info.controller;


import com.github.pagehelper.PageInfo;
import com.info.entity.Record;
import com.info.entity.Result;
import com.info.service.UserService;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员控制类curd
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户中心
     *
     * @return
     */
    @RequestMapping()
    public String userCenter() {
        return "usercenter";
    }


    /**
     * 通过身份证查找用户
     *
     * @param IDCard
     * @param cardType
     * @return
     */
    @RequestMapping("/find/IDCard")
    @ResponseBody
    public Result<PageInfo<Record>> findByIdCard(@RequestParam("cardType") String cardType,
                                                 @RequestParam("IDCard") String IDCard,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize) {

        PageInfo<Record> pageInfo = userService.findByIdCard(cardType, IDCard, pageNum, pageSize);
        Result<PageInfo<Record>> result = new Result<>(200, "ok", pageInfo);
        return result;
    }


    /**
     * 获取所有身份证类型
     *
     * @return
     */
    @RequestMapping("/cardType")
    @ResponseBody
    public List<String> cardType() {
        return userService.findAllCardType();
    }


    /**
     * 通过excel查询数据
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result<List<Record>> findUserByExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        List<Record> list = userService.findUserByExcel(file, request);
        Result<List<Record>> result = new Result<>(200, "ok", list);
        return result;
    }

    /**
     * 通过excel查询数据,将内存数据进行分页
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/upload/find")
    @ResponseBody
    public Result<List<Record>> tempFindUserByExcel(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize) {
        List<Record> list = userService.tempFindUserByExcel(pageNum, pageSize);

        Integer listSize = userService.getListSize();
        double pages = Math.ceil(listSize / pageSize);
        Result<List<Record>> result = new Result<>(200, pages + "", list);
        return result;
    }


    /**
     * 下载excel文件
     *
     * @param ids
     * @param request
     * @param response
     */
    @PostMapping("/down")
    public void downExcel(@RequestParam("ids") String ids, HttpServletRequest request, HttpServletResponse response) {
        userService.downExcel(ids, request, response);
    }


}
