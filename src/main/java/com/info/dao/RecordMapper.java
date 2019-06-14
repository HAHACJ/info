package com.info.dao;

import com.info.entity.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 查询信息接口
 */
public interface RecordMapper {


    List<Record> findByIdCard(Map<String, String> map);

    void addRecord(Record record);

    List<String> findAllCardType();

    List<Record> findUserbyExcel(Map<String, Object> map);

    List<Record> findUser(@Param("ids") String[] idArray);
}
