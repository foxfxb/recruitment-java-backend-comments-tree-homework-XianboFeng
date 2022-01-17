package com.foxfxb.interviewee.mybatis.annotation;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface ConditionFun {
    void conditionFun(QueryWrapper wrapper, String field, Object value);
}
