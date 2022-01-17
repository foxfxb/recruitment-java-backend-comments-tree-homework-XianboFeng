package com.foxfxb.interviewee.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConditionType {
    ConditionTypeEnum type() default ConditionTypeEnum.EQ;

    /**
     * 判定字段名
     * @return
     */
    String filed() default "";
    /**
     * 是否忽略
     */
    boolean ignore() default false;
}
