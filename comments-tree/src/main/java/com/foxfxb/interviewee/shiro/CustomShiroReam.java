package com.foxfxb.interviewee.shiro;

import com.foxfxb.interviewee.config.ShiroConfig;
import com.foxfxb.interviewee.entity.authorize.UserLoginInfo;
import com.foxfxb.interviewee.entity.po.UserEntity;
import com.foxfxb.interviewee.service.UserService;
import com.foxfxb.interviewee.utils.CommonUtils;
import com.foxfxb.interviewee.utils.Md5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;
import java.util.Set;

public class CustomShiroReam extends BaseShiroRealm {

    private static final String REALNAME = "Realm";

    @Autowired
    private UserService userService;
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pPrincipals) {

        String loginName = (String) pPrincipals.getPrimaryPrincipal();
        if (!CommonUtils.isNull(loginName)) {

            Session session = SecurityUtils.getSubject().getSession();
            UserLoginInfo userLoginInfo = (UserLoginInfo) session.getAttribute(ShiroConfig.SESSION_KEY);

            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            Set<String> permissionsList =new LinkedHashSet<>();
            authorizationInfo.setStringPermissions(permissionsList);
            return authorizationInfo;
        }
        return null;
    }


    /*
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken pToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) pToken;
        String loginName = (String) pToken.getPrincipal();
        if (!"".equals(loginName)) {
            String userName = token.getUsername();
            String password = new String(token.getPassword());

            UserEntity user = userService.getLogin(userName);
            if(user == null){
                throw new UnknownAccountException("账号不存在");
            }else if(!user.getPassword().equals(password)){
                throw new IncorrectCredentialsException("密码不正确");
            }
            Session session = SecurityUtils.getSubject().getSession();
            UserLoginInfo userLoginInfo = (UserLoginInfo) session.getAttribute(ShiroConfig.SESSION_KEY);

            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, userLoginInfo.getPassword(), REALNAME);
            return authcInfo;
        }
        return null;
    }

}
