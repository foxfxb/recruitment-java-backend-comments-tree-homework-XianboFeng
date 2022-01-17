package com.foxfxb.interviewee.request;

import com.foxfxb.interviewee.entity.base.BaseObj;
import com.foxfxb.interviewee.utils.BeanConvertUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PageQueryRequest<T> extends BaseObj {

    private Long pageNum = 0l;

    private Long pageSize = 10l;

    private T condition;

    public Map<String, Object> toParams(){
        return BeanConvertUtils.convert(condition, HashMap.class);
    }
}
