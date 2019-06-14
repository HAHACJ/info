package com.info.service.imp;

import com.info.dao.RecordMapper;
import com.info.entity.Record;
import com.info.entity.User;
import com.info.service.NewService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NewServiceImp implements NewService {


    private static final Logger log = LoggerFactory.getLogger(NewServiceImp.class);

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public void addInfo(Record record, User user) {

        String recordUser = user.getUsername();
        String recordOrg = user.getOrg().getOrgName();
        String recordTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (StringUtils.isBlank(recordUser) || StringUtils.isBlank(recordOrg)) {
            log.error("录入人或组织为空！");
            throw new RuntimeException();
        }
        record.setRecordUser(recordUser);
        record.setRecordOrg(recordOrg);
        record.setRecordTime(recordTime);
        recordMapper.addRecord(record);
    }
}
