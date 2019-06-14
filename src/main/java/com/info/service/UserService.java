package com.info.service;

import com.github.pagehelper.PageInfo;
import com.info.entity.Record;
import com.info.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * curd接口
 */
public interface UserService {


    /**
     * 检查登录
     *
     * @param data
     * @return
     */
    User checkLogin(Map<String, String> data);


    /**
     * 通过身份证查找用户
     *
     * @param cardType
     * @param IDCard
     * @return
     */
    PageInfo<Record> findByIdCard(String cardType, String IDCard, Integer pageNum, Integer pageSize);


    /**
     * 获取所有的身份证类型
     *
     * @return
     */
    List<String> findAllCardType();


    /**
     * 通过excel查询用户
     *
     * @param file
     * @param request
     * @return
     */
    List<Record> findUserByExcel(MultipartFile file, HttpServletRequest request);


    /**
     * 将通过excel查询用户的结果集存在内存中，分页加载时用到
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Record> tempFindUserByExcel(Integer pageNum, Integer pageSize);

    /**
     * 将通过excel查询用户的结果集存在内存中，获取list总长度
     *
     * @return
     */
    Integer getListSize();

    /**
     * 下载选择的信息并生成excel
     *
     * @param request
     * @param response
     * @param ids
     */
    void downExcel(String ids, HttpServletRequest request, HttpServletResponse response);


    /**
     * 查询所有录入信息
     *
     * @return
     */
    List<User> findAllUser();

    /**
     * api添加信息
     *
     * @param appKey 接口密钥
     * @return
     */
    User checkAppKey(String appKey);


    /**
     * 增加管理员
     *
     * @return appKey 返回appkey
     */
    String addUser(User user);
}
