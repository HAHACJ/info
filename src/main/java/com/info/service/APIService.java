package com.info.service;

import com.info.entity.User;


/**
 * api添加信息
 */
public interface APIService {

    /**
     *
     * @param username
     * @param cardType
     * @param IDCard
     * @param recordReason
     * @param user
     */
    void addInfo(String username, String cardType,
                 String IDCard, String recordReason, User user);
}
