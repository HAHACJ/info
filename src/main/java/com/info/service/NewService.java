package com.info.service;

import com.info.entity.Record;
import com.info.entity.User;
import org.springframework.transaction.annotation.Transactional;


/**
 * 新建信息
 */
@Transactional(rollbackFor = Exception.class)
public interface NewService {


    void addInfo(Record record, User user);
}
