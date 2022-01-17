package com.foxfxb.interviewee.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.foxfxb.interviewee.mybatis.annotation.ConditionFun;
import com.foxfxb.interviewee.mybatis.annotation.ConditionType;
import com.foxfxb.interviewee.mybatis.annotation.ConditionTypeEnum;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
public class ConditionAnalysisUtils {

    private static Map<ConditionTypeEnum, ConditionFun> typeFunc;

    static {
        if (typeFunc == null) {
            typeFunc = new HashMap<ConditionTypeEnum, ConditionFun>();
            typeFunc.put(ConditionTypeEnum.EQ, (w, k, v) -> {

                w.eq(k, v);
            });
            typeFunc.put(ConditionTypeEnum.NEQ, (w, k, v) -> {
                w.ne(k, v);
            });
            typeFunc.put(ConditionTypeEnum.IN, (w, k, v) -> {
                if (v instanceof Collection) {
                    w.in(k, (Collection<?>) v);
                } else if (v instanceof Object[]) {
                    w.in(k, (Object[]) v);
                } else {
                    w.in(k, v.toString());
                }
            });
            typeFunc.put(ConditionTypeEnum.LIKE, (w, k, v) -> {
                w.like(k, v.toString());
            });
            typeFunc.put(ConditionTypeEnum.LE, (w, k, v) -> {
                w.le(k, v);
            });
            typeFunc.put(ConditionTypeEnum.LT, (w, k, v) -> {
                w.lt(k, v);
            });
            typeFunc.put(ConditionTypeEnum.GE, (w, k, v) -> {
                w.ge(k, v);
            });
            typeFunc.put(ConditionTypeEnum.GT, (w, k, v) -> {
                w.gt(k, v);
            });
        }

    }

    /**
     * 封装成需要的wrapper
     *
     * @param t 实体对象
     * @return
     */
    public static QueryWrapper invoke(Object t) {
        QueryWrapper<Object> wrapper = new QueryWrapper();
        excute(t, wrapper);
        // 获取
        return wrapper;
    }

    public static void invoke(Object t, QueryWrapper wrapper) {
        excute(t, wrapper);
    }

    /**
     * 执行
     *
     * @param t       obj
     * @param wrapper
     */
    public static void excute(Object t, QueryWrapper wrapper) {
        //反射获取属性
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object val = field.get(t);
                String colum = "";
                if (val != null && !"".equals(val.toString())) {
                    ConditionType whereType = field.getAnnotation(ConditionType.class);
                    //没有注解，取默认为下划线拼接
                    if (whereType == null) {
                        colum = camelToUnderline(field.getName());
                        typeFunc.get(ConditionTypeEnum.EQ).conditionFun(wrapper, colum, val);
                    } else {
                        if (whereType.ignore()) {
                            continue;
                        } else {
                            //没有定义查询属性，取默认
                            if (!StringUtils.isBlank(whereType.filed())) {
                                colum = whereType.filed();
                            } else {
                                colum = camelToUnderline(field.getName());
                            }
                            if (whereType.type().equals(ConditionTypeEnum.LIKE)) {
//                                typeFunc.get(whereType.type()).conditionFun(wrapper, colum, "%" + val + "%");
                                typeFunc.get(whereType.type()).conditionFun(wrapper, colum, val);
                            } else {
                                typeFunc.get(whereType.type()).conditionFun(wrapper, colum, val);
                            }

                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 驼峰转下划线
     *
     * @param param
     * @return
     * @Author 张鹏
     * @Date 2018/11/27 11:52
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
