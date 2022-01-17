package com.foxfxb.interviewee.config;

import com.foxfxb.interviewee.exception.BizException;
import com.foxfxb.interviewee.response.RestResponse;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public RestResponse handleIllegalArgumentException(Throwable ex) {
        logger.error(ex.getMessage(), ex);
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(false);
        restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        restResponse.setDisplayMessage(ex.getMessage());
        return restResponse;
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public RestResponse handleBusinessException(BizException ex) {
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(false);

        restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        restResponse.setDisplayMessage(ex.getMessage());
        return restResponse;
    }

    /**
     * 处理数据绑定异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestResponse handleException(Throwable ex) {
        logger.error(ex.getMessage(), ex);
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(false);
        restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        restResponse.setDisplayMessage("服务器异常");
        return restResponse;
    }
    /**
     * 处理SHIRO绑定异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public RestResponse handleShiroUnauthorizedException(UnauthorizedException ex) {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.warn("当前用户没有权限:{}", ex.getMessage());
        return RestResponse.accessDenied();
    }

    /**
     * 处理发送邮件异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MailSendException.class)
    @ResponseBody
    public RestResponse handleMailSendExceptionException(MailSendException ex) {
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(false);
        restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        restResponse.setDisplayMessage(ex.getMessageExceptions()[0].getMessage());
        return restResponse;
    }
}
