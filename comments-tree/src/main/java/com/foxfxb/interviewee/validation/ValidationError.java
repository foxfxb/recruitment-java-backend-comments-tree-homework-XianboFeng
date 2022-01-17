package com.foxfxb.interviewee.validation;

import com.foxfxb.interviewee.entity.base.BaseObj;
import com.foxfxb.interviewee.utils.JSONUtils;
import org.springframework.util.StringUtils;

public class ValidationError extends BaseObj {
    public static final String GLOBAL_KEY = "GLOBAL_KEY";
    private static final long serialVersionUID = -4489102207088964689L;
    private String field;
    private Object[] values;
    private String code;
    private String message;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        if (StringUtils.isEmpty(field)) {
            this.field = GLOBAL_KEY;
        } else {
            this.field = field;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return JSONUtils.toJson(this);
    }
}
