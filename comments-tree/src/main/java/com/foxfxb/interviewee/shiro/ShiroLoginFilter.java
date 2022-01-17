package com.foxfxb.interviewee.shiro;

import com.foxfxb.interviewee.utils.CommonUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShiroLoginFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(ShiroLoginFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //可以做成可配置
        if(CommonUtils.contains(httpServletRequest.getRequestURI(), "/login")){
            return true;
        }

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        log.info("doAccessDenied");
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response){
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        log.info("redirectToLogin");
    }
}
