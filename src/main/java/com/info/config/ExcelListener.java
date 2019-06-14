package com.info.config;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析监听器，
 * 每解析一行会回调invoke()方法。
 * 整个excel解析结束会执行doAfterAllAnalysed()方法
 * <p>
 * 下面只是我写的一个样例而已，可以根据自己的逻辑修改该类。
 *
 * @author jipengfei
 * @date 2017/03/14
 */
public class ExcelListener extends AnalysisEventListener {
    /**
     * 自定义用于暂时存储data。
     * 可以通过实例获取该值
     */

    private List<List<Object>> datas = new ArrayList<>();


    private List<Map<String, Object>> modelData = new ArrayList<>();


    @Override
    public void invoke(Object object, AnalysisContext context) {
        System.out.println("当前行：" + context.getCurrentRowNum());
        System.out.println(object);

        //转成list
        List<Object> list = (List<Object>) object;

        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(list);
        //根据自己业务做处理
        doSomething(object);
    }

    private void doSomething(Object object) {
        //1、入库调用接口
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // datas.clear();//解析结束销毁不用的资源
    }

    public List<List<Object>> getDatas() {
        return datas;
    }


    /**
     * 根据bean模型的Excel来对应list集合的map
     *
     * @return
     */
    public List<Map<String, Object>> getModelData() {
        List<Object> firstRow = getDatas().get(0);

        List<List<Object>> datas = getDatas();

        for (int dataRow = 1; dataRow < datas.size(); dataRow++) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < datas.get(dataRow).size(); i++) {
                Object temp = datas.get(dataRow).get(i);
                map.put(firstRow.get(i) + "", temp);
            }
            modelData.add(map);
        }
        return modelData;
    }
}
