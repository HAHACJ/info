package com.info.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * record
 */


@Getter
@Setter
@ToString
public class Record extends BaseRowModel {


    private Integer recordId;

    @ExcelProperty(value = "username", index = 0)
    private String username;

    @ExcelProperty(value = "cardType", index = 1)
    private String cardType;

    @ExcelProperty(value = "IDCard", index = 2)
    private String IDCard;

    @ExcelProperty(value = "recordUser", index = 3)
    private String recordUser;

    @ExcelProperty(value = "recordOrg", index = 4)
    private String recordOrg;

    @ExcelProperty(value = "recordTime", index = 5)
    private String recordTime;

    @ExcelProperty(value = "recordReason", index = 6)
    private String recordReason;

    @ExcelProperty(value = "remark1", index = 7)
    private String remark1;

    @ExcelProperty(value = "remark2", index = 8)
    private String remark2;

}
