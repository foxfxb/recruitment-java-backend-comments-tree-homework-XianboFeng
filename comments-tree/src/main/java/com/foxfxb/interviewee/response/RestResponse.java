package com.foxfxb.interviewee.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxfxb.interviewee.entity.base.BaseObj;
import com.foxfxb.interviewee.utils.JSONUtils;
import com.foxfxb.interviewee.validation.ValidationError;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestResponse<T> extends BaseObj {

    private static final long serialVersionUID = -3083953981310916608L;

    /**
     *
     */
    @ApiModelProperty(example="401:未认证; 402:未认证;403:认证过期;407:重复认证;406:权限不足，200:成功 ",value="结果码")
    private int code = 200;
    @ApiModelProperty(example="true:成功",value="是否成功")
    private boolean success = true;
    @ApiModelProperty(value="错误消息")
    private List<ValidationError> validateErrors = new ArrayList<>();
    @ApiModelProperty(value="返回数据")
    private T data = null;
    @ApiModelProperty(value="显示错误消息")
    private String displayMessage;

    public RestResponse() {
    }

    public RestResponse(Errors errors) {
        addValidateErrors(errors);
    }

    public RestResponse(ValidationError error) {
        addValidateError(error);
    }

    public RestResponse(T data) {
        this.data = data;
    }

    public static RestResponse success() {
        return success(null);
    }

    public static RestResponse success(Object data) {
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(true);
        restResponse.setData(data);
        return restResponse;
    }

    public static RestResponse accessDenied(){
        return fail("验证失效", HttpStatus.UNAUTHORIZED.value());
    }

    public static RestResponse fail(Object data) {
        return fail(data, 500);
    }

    public static RestResponse fail(Object data, String errorMsg) {
        return fail(data, 500, errorMsg);
    }

    public static RestResponse fail(Object data, int code) {
        return fail(data, code, String.valueOf(data));
    }

    public static RestResponse fail(Object data, int code, String errorMsg) {
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(false);
        restResponse.setCode(code);
        restResponse.setData(data);
        restResponse.setDisplayMessage(errorMsg);
        return restResponse;
    }

    public <T> T getData(Class<T> clazz) {
        if (data instanceof Map || data instanceof String) {
            ObjectMapper mapper = new ObjectMapper();
            T returnJson = mapper.convertValue(data, clazz);
            return returnJson;
        }
        return (T) data;
    }

    public <T> T getData(TypeReference<T> typeReference) {
        if (data == null) {
            return null;
        }
        T returnData = null;
        if (data instanceof String) {
            returnData = JSONUtils.toObject((String) data, typeReference);
        } else {
            String jsonString = JSONUtils.toJson(data);
            returnData = JSONUtils.toObject(jsonString, typeReference);
        }
        return (T) returnData;
    }

    public void addValidateErrors(Errors errors) {
        if (errors == null) {
            return;
        }
        if (errors.hasGlobalErrors()) {
            errors.getGlobalErrors()
                    .forEach(e -> addValidateError(null, e.getDefaultMessage(), e.getCode(), e.getArguments()));
        }
        if (errors.hasFieldErrors()) {
            errors.getFieldErrors()
                    .forEach(e -> addValidateError(e.getField(), e.getDefaultMessage(), e.getCode(), e.getArguments()));
        }
    }

    public void addValidateError(String field, String defaultMessage, String errorCode, Object[] values) {
        ValidationError error = new ValidationError();
        error.setField(field);
        error.setCode(errorCode);
        error.setValues(values);
        error.setMessage(defaultMessage);
        addValidateError(error);
    }

    public boolean hasFieldError(String field) {
        if (field == null) {
            field = ValidationError.GLOBAL_KEY;
        }

        for (ValidationError error : validateErrors) {
            if (field.equals(error.getField())) {
                return true;
            }
        }
        return false;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        if (!success) {
            if (isValidateError()) {
                String validationMsg = "";
                for (ValidationError validationError : validateErrors) {
                    validationMsg += validationError.getMessage() + " ";
                }
                return validationMsg;
            } else {
                // logger.debug("System internal error: " + data);
                return displayMessage;
            }
        }
        return "";
    }

    public boolean isValidateError() {
        if (this.validateErrors == null) {
            return false;
        } else {
            return !this.validateErrors.isEmpty();
        }
    }

    public List<ValidationError> getValidateErrors() {
        return this.validateErrors;
    }

    public void addValidateErrors(List<ValidationError> validateErrors) {
        setSuccess(false);
        if (validateErrors != null) {
            for (ValidationError validationError : validateErrors) {
                this.validateErrors.add(validationError);
            }
        }
    }

    public void addValidateError(ValidationError error) {
        setSuccess(false);
        this.validateErrors.add(error);
    }
}
