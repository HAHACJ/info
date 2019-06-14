package com.info.utils;

import com.google.common.collect.Maps;
import net.sf.cglib.beans.BeanMap;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 工具类
 *
 * @version 1.0
 * @since JDK1.7
 */
public class ObjectUtils {


    /**
     * 方法描述 封装返回结果集
     *
     * @param code
     * @param msg
     */
    public static Map<String, Object> getReturnResult(Integer code, String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

    /**
     * 方法描述 封装返回结果集 多加一个key--value
     *
     * @param code
     * @param msg
     * @param paramName
     * @param paramValue
     * @return
     */
    public static Map<String, Object> getReturnResult(Integer code, String msg, String paramName, Object paramValue) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("code", code);
        map.put(paramName, paramValue);
        map.put("msg", msg);
        return map;
    }

    /**
     * 方法描述 如果对象为非空返回true 否则返回false
     *
     * @param obj
     * @return
     */
    public static boolean objIsNotNull(Object obj) {
        if (obj != null) {
            return true;
        }
        return false;
    }

    /**
     * 方法描述 如果对象为空返回 true 否则返回false
     *
     * @param obj
     * @return
     */
    public static boolean objIsNull(Object obj) {
        if (obj != null) {
            return false;
        }
        return true;
    }

    /**
     * 方法描述 判断Map集合非null 非空 返回true 否则返回false
     *
     * @param map
     * @return
     */
    public static boolean mapIsNotNull(Map<Object, Object> map) {
        if (map != null && !map.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 方法描述 判断Map集合是null或者空 返回true 否则返回false
     *
     * @param map
     * @return
     */
    public static boolean mapIsNull(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 方法描述 判断list集合非null 非空 返回true 否则返回false
     *
     * @param list
     * @return
     */
    public static boolean listIsNotNull(List<Object> list) {
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 方法描述 判断list集合是null或者空 返回true 否则返回false
     *
     * @param list
     * @return
     */
    public static boolean listIsNull(List<Object> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }


    /**
     * 判断字段值是否为空
     *
     * @param field
     * @param fieldValue
     * @return
     */
    public static boolean strIsBlank(String field, String fieldValue) {
        if (StringUtils.isBlank(fieldValue)) {

            return true;
        }
        return false;
    }


    /**
     * 判断字段值是否为空
     *
     * @param field
     * @param fieldValue
     * @return
     */
    public static boolean strIsNotBlank(String field, String fieldValue) {
        if (StringUtils.isNotBlank(fieldValue)) {
            return true;
        }
        return false;
    }

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }
}