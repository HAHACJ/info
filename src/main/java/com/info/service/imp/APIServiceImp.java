package com.info.service.imp;

import com.info.entity.Record;
import com.info.entity.User;
import com.info.service.APIService;
import com.info.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class APIServiceImp implements APIService {

    @Autowired
    private NewService newService;


    @Override
    public void addInfo(String username, String cardType, String IDCard, String recordReason, User user) {

        Record record = new Record();
        record.setUsername(username);
        record.setCardType(cardType);
        record.setIDCard(IDCard);
        record.setRecordReason(recordReason);
        newService.addInfo(record, user);
    }
}
