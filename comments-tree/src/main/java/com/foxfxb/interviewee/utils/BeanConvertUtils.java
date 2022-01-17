package com.foxfxb.interviewee.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Bean转换帮助类
 */
@Log4j2
public class BeanConvertUtils {


    /**
     * Bean转换
     *
     * @param source
     * @param targetClass
     * @param dateFormat
     * @return
     */
    public static <T> T convert(Object source, Class<T> targetClass, String dateFormat) {
        String jsonStr = JSON.toJSONStringWithDateFormat(source, dateFormat);
        return JSON.parseObject(jsonStr, targetClass);
    }

    /**
     * Bean转换
     *
     * @param source
     * @param targetClass
     * @return
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        String jsonStr = JSON.toJSONString(source);
        return JSON.parseObject(jsonStr, targetClass);
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param targetClass 要转化的类型
     * @param map         包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static <T> T convert(Map map, Class<T> targetClass) {
        if(map == null){
            return null;
        }
        JSONObject json = new JSONObject(map);
        return JSON.parseObject(json.toJSONString(), targetClass);
    }

    /**
     * List转换
     *
     * @param source
     * @param targetClass List存放的数据类型
     * @param dateFormat
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> convertList(List source, Class<T> targetClass, String dateFormat) {
        String jsonStr = JSON.toJSONStringWithDateFormat(source, dateFormat);
        return JSON.parseArray(jsonStr, targetClass);
    }

    /**
     * List转换
     *
     * @param source
     * @param targetClass List存放的数据类型
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> convertList(List source, Class<T> targetClass) {
        String jsonStr = JSON.toJSONString(source);
        return JSON.parseArray(jsonStr, targetClass);
    }
}
