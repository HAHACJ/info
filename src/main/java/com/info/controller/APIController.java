package com.info.controller;


import com.info.entity.Result;
import com.info.entity.User;
import com.info.service.APIService;
import com.info.service.NewService;
import com.info.service.UserService;
import com.info.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过api，appkey直接操作，免登陆
 */

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private UserService userService;

    @Autowired
    private NewService newService;

    @Autowired
    private APIService apiService;


    /**
     * 拉入黑名单
     *
     * @param username     被拉黑人
     * @param cardType     证件类型
     * @param IDCard       证件号码
     * @param recordReason 原因
     * @param appKey       操作人key
     * @return
     */
    @GetMapping("/blacklist/{username}/{cardType}/{IDCard}/{recordReason}/{appKey}")
    public Result<String> blacklist(@PathVariable("username") String username,
                                    @PathVariable("cardType") String cardType,
                                    @PathVariable("IDCard") String IDCard,
                                    @PathVariable("recordReason") String recordReason,
                                    @PathVariable("appKey") String appKey) {

        Result<String> result = new Result<>();
        try {

            if (StringUtils.isBlank(appKey)) {
                result.setCode(403);
                result.setMsg("appKey is null");
                return result;
            }

            if (StringUtils.isAnyBlank(username, cardType, IDCard, recordReason)) {
                result.setCode(400);
                result.setMsg("Missing parameter");
                return result;
            }

            appKey = MD5Utils.MD5(appKey);
            User user = userService.checkAppKey(appKey);
            if (user != null) {
                apiService.addInfo(username, cardType, IDCard, recordReason, user);
                result.setMsg("ok");
                result.setCode(200);
            }else {
                result.setCode(403);
                result.setMsg("appKey does not exist!");
            }

        } catch (Exception e) {
            result.setCode(500);
            result.setMsg(e.getMessage());
        }
        return result;
    }



}
