package com.foxfxb.interviewee.shiro;

import com.foxfxb.interviewee.config.ShiroConfig;
import com.foxfxb.interviewee.entity.authorize.UserLoginInfo;
import com.foxfxb.interviewee.entity.po.UserEntity;
import com.foxfxb.interviewee.service.SessionService;
import com.foxfxb.interviewee.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddPrincipalToSessionFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        //查询当前用户的信息
        Subject subject = SecurityUtils.getSubject();
        //判断用户是不是通过自动登录进来的
        if (subject.isRemembered()) {
            UserEntity user2 = (UserEntity) subject.getPrincipal();
            String userName = user2.getUserName();
            if (userName == null) {
                return;
            }
            //根据用户名查询该用户的信息
            UserEntity user = userService.getLogin(userName);
            if (user == null) return;
            //由于是继承的OncePerRequestFilter，没办法设置session
            //这里发现可以将servletReques强转为子类，所以使用request.getsiion())
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpSession session = request.getSession();
            //当session为空的时候
            if (session.getAttribute(ShiroConfig.SESSION_KEY) == null) {
                //把查询到的用户信息设置为session，时效为3600秒
                session.setAttribute(ShiroConfig.SESSION_KEY, user);
                session.setMaxInactiveInterval(3600);
            }
            sessionService.setLoginInfo(subject, user);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
