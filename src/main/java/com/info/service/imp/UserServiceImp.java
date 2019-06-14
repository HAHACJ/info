package com.info.service.imp;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.config.ExcelListener;
import com.info.dao.RecordMapper;
import com.info.dao.UserMapper;
import com.info.entity.Record;
import com.info.entity.User;
import com.info.service.UserService;
import com.info.utils.IdUtil;
import com.info.utils.MD5Utils;
import com.info.utils.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.info.utils.FileUtil.judeDirExists;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecordMapper recordMapper;


    /**
     * 为excel查询分页做的临时变量存在内存中
     */
    private static List<Record> excelList = null;


    private Logger log = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public User checkLogin(Map<String, String> map) {

        if (map == null || map.isEmpty()) {
            log.error("登录map为空{}", map);
            return null;
        }

        String username = map.get("username");
        String password = map.get("password");

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            log.error("用户名或密码为空:{},{}", username, password);
            return null;
        }

//        md5再次加密
        password = MD5Utils.MD5(password);
        map.put("password", password);

        User user = userMapper.findUser(map);

        return user;
    }

    /**
     * 通过身份证查找用户
     *
     * @param cardType
     * @param IDCard
     * @return
     */
    @Override
    public PageInfo<Record> findByIdCard(String cardType, String IDCard, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(IDCard) || StringUtils.isBlank(cardType)) {
            log.error("IDCard或cardType为空:{},{}", IDCard, cardType);
        }
        Map<String, String> map = new HashMap<>();
        map.put("IDCard", IDCard);
        map.put("cardType", cardType);
        PageHelper.startPage(pageNum, pageSize);
        List<Record> records = recordMapper.findByIdCard(map);
        PageInfo<Record> pageInfo = new PageInfo<>(records);
        return pageInfo;
    }

    /**
     * 获取所有的身份证类型
     *
     * @return
     */
    @Override
    public List<String> findAllCardType() {

        return recordMapper.findAllCardType();
    }

    /**
     * 通过excel查询用户
     *
     * @param file
     * @return
     */
    @Override
    public List<Record> findUserByExcel(MultipartFile file, HttpServletRequest request) {

        InputStream inputStream = null;

        List<Map<String, Object>> list = null;
        try {

            String fileName = file.getOriginalFilename();
            // 获取文件的扩展名
            String ext = FilenameUtils.getExtension(fileName);

            String url = request.getSession().getServletContext().getRealPath("/upload");

            judeDirExists(new File(url));

            File tempFile = new File(url + "/" + fileName);

            System.out.println(url + "/" + fileName);
            file.transferTo(tempFile);
            inputStream = new FileInputStream(tempFile);
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();

            ExcelReader excelReader = null;


            if (ext.equals("xlsx")) {
                excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            } else if (ext.equals("xls")) {
                excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
            } else {
                log.error("上传文件不符合要求！");
                new RuntimeException("上传文件不符合要求！");
            }

            excelReader.read();

            list = listener.getModelData();

            //数据处理查询结果返回，并存在内存中
            excelList = search(list);

            //删除文件
            tempFile.delete();

        } catch (Exception e) {
            log.error("文件上传异常：{}", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return excelList;
    }

    /**
     * 将通过excel查询用户的结果集存在内存中，分页加载时用到
     *
     * @return
     */
    @Override
    public List<Record> tempFindUserByExcel(Integer pageNum, Integer pageSize) {

        return excelList.stream()
                .skip((long) (pageNum - 1) * pageSize).limit((long) pageSize)
                .collect(Collectors.toList());
    }

    /**
     * 将通过excel查询用户的结果集存在内存中，获取list总长度
     *
     * @return
     */
    @Override
    public Integer getListSize() {
        return excelList.size();
    }


    /**
     * map类型的bean转成listBean并批量查询返回结果集
     *
     * @param list
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private List<Record> search(List<Map<String, Object>> list) throws IllegalAccessException, InstantiationException {

        List<Record> recordList = ObjectUtils.mapsToObjects(list, Record.class);
        Map<String, List<Record>> map = recordList.stream().collect(Collectors.groupingBy(Record::getCardType));

        Map<String, Object> result = new HashMap<>();
        List<String> idList = new ArrayList<>();
        //最终结果集
        List<Record> recordResult = new ArrayList<>();

        for (Map.Entry<String, List<Record>> entry : map.entrySet()) {

            for (Record records : entry.getValue()) {
                idList.add(records.getIDCard());
            }
            result.put("cardType", entry.getKey());
            result.put("IDCard", idList);

            List<Record> temp = recordMapper.findUserbyExcel(result);
            recordResult.addAll(temp);
        }
        return recordResult;
    }


    /**
     * 下载选择的信息并生成excel
     *
     * @param ids
     */
    @Override
    public void downExcel(String ids, HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isBlank(ids)) {
            log.error("参数传入为空");
            return;
        }
        String[] idArray = ids.split(",");
        List<Record> recordList = recordMapper.findUser(idArray);

        try {
            String fileName = new String(("UserInfo_" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                    .getBytes(), "UTF-8");
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0, Record.class);

            writer.write(recordList, sheet1);
            writer.finish();
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }


    /**
     * api添加信息
     *
     * @param appKey 接口密钥
     * @return
     */
    @Override
    public User checkAppKey(String appKey) {

        return userMapper.findUserByAppKey(appKey);
    }

    /**
     * 增加管理员
     */
    @Override
    public String addUser(User user) {
        String appKey = IdUtil.createAppKey();
        user.setAppKey(MD5Utils.MD5(appKey));
        userMapper.addUser(user);
        return appKey;
    }

}
