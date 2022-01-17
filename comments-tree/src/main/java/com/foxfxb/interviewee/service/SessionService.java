package com.foxfxb.interviewee.service;

import com.foxfxb.interviewee.config.ShiroConfig;
import com.foxfxb.interviewee.entity.authorize.UserLoginInfo;
import com.foxfxb.interviewee.entity.po.UserEntity;
import com.foxfxb.interviewee.utils.BeanConvertUtils;
import com.foxfxb.interviewee.utils.CommonUtils;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private UserService userService;

    /**
     * 获取当前用户对象
     * @return
     */
    public UserLoginInfo getLoginUser(){

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        UserLoginInfo loginInfoVO = (UserLoginInfo) session.getAttribute(ShiroConfig.SESSION_KEY);

        if(!CommonUtils.isNull(loginInfoVO)){
            return loginInfoVO;
        }
        //从session登陆
        UserEntity user2 = (UserEntity) currentUser.getPrincipal();
        if (user2 == null) {
            return loginInfoVO;
        }
        String userName = user2.getUserName();

        UserEntity user = userService.getLogin(userName);
        return setLoginInfo(currentUser, user);
    }

    public UserLoginInfo setLoginInfo(Subject currentUser, UserEntity userInfo){
        UserLoginInfo userLoginInfo = BeanConvertUtils.convert(userInfo, UserLoginInfo.class);
        userLoginInfo.setName(userInfo.getUserName());
        // 获取当前session，存入登录用户信息
        Session session = currentUser.getSession();
        session.setTimeout(24 * 60 * 60 * 1000);
        session.setAttribute(ShiroConfig.SESSION_KEY, userLoginInfo);
        return userLoginInfo;
    }

    public String getCurrentUserName(){
        return getLoginUser().getName();
    }

    public Integer getCurrentUserId(){
        return getLoginUser().getUserId();
    }

    public void logout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }
}
