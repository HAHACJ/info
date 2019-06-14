package com.info.info;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.info.config.ExcelListener;
import com.info.dao.RecordMapper;
import com.info.entity.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoApplicationTests {

    @Autowired
    private RecordMapper recordMapper;

    @Test
    public void contextLoads() {

        Record record = new Record();

        for (int i = 0; i < 600; i++) {
            record.setCardType("港澳通行证");
            record.setIDCard("422423472342" + i);
            record.setRecordOrg(i + "");
            record.setRecordUser(i + "");
            record.setUsername(i + "");
            record.setRecordReason(i + "");
            record.setRemark1(i + "");
            record.setRemark2(i + "");
            recordMapper.addRecord(record);
        }
    }

//    @Test
//    public void testExcel() throws IOException {
//        Map<String, String> map1 = new HashMap<>();
//        map1.put("IDCard", "0");
//        map1.put("cardType", "0");
//        Record record = recordMapper.findByIdCard(map1);
//
//        //用排序的Map且Map的键应与ExcelCell注解的index对应
//        Map<String, String> map = new LinkedHashMap<>();
//        map.put("username", "username");
//        map.put("cardType", "cardType");
//        map.put("IDCard", "IDCard");
//        map.put("recordUser", "recordUser");
//        map.put("recordOrg", "recordOrg");
//        map.put("recordTime", "recordTime");
//        map.put("recordReason", "recordReason");
//        map.put("remark1", "remark1");
//        map.put("remark2", "remark2");
//        Collection<Object> dataset = new ArrayList<>();
//        dataset.add(record);
//        File f = new File("C:\\Users\\chen.000\\Desktop\\test.xls");
//        OutputStream out = new FileOutputStream(f);
//
//        ExcelUtil.exportExcel(map, dataset, out);
//        out.close();
//
//    }

//    @Test
//    public void testAli() throws FileNotFoundException {
//
//        OutputStream out = new FileOutputStream("C:\\Users\\chen.000\\Desktop\\1888.xlsx");
//        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
//        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
//        Sheet sheet1 = new Sheet(1, 0, Record.class);
//        Map<String, String> map1 = new HashMap<>();
//        map1.put("IDCard", "0");
//        map1.put("cardType", "0");
//        List<Record> record = recordMapper.findByIdCard(map1);
//
//
//        writer.write(record, sheet1);
//        writer.finish();
//    }
//
//    @Test
//    public void testNOMdul() throws IOException {
//        InputStream inputStream = new FileInputStream("C:\\Users\\chen.000\\Desktop\\78.xlsx");
//
//        // 解析每行结果在listener中处理
//        ExcelListener listener = new ExcelListener();
//
//        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
//        excelReader.read();
//        inputStream.close();
//        System.out.println(listener.getModelData());
//    }

}
