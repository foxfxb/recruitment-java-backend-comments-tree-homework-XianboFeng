package com.foxfxb.interviewee.controller;

import com.foxfxb.interviewee.exception.BizException;
import com.foxfxb.interviewee.service.SessionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class BaseController {

    @Autowired
    private SessionService sessionService;

    protected boolean isLogin(){
        return sessionService.getLoginUser() != null;
    }

    protected void mustLoginCheck(){
        if(!isLogin()){
            throw new BizException("该功能需要登录后使用使用");
        }
    }

}
