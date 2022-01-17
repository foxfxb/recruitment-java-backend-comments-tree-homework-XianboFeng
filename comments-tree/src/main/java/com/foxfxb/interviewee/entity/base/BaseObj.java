package com.foxfxb.interviewee.entity.base;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.foxfxb.interviewee.utils.JSONUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseObj implements Serializable {
    @Override
    public String toString() {
        return JSONUtils.toJson(this);
    }
}
