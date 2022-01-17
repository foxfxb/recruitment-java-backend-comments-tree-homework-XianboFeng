package com.foxfxb.interviewee.controller;

import com.foxfxb.interviewee.config.ShiroConfig;
import com.foxfxb.interviewee.entity.authorize.LoginResultBO;
import com.foxfxb.interviewee.entity.authorize.UserLoginInfo;
import com.foxfxb.interviewee.entity.po.UserEntity;
import com.foxfxb.interviewee.param.vo.UserLoginVo;
import com.foxfxb.interviewee.response.RestResponse;
import com.foxfxb.interviewee.service.SessionService;
import com.foxfxb.interviewee.service.UserService;
import com.foxfxb.interviewee.utils.CommonUtils;
import com.foxfxb.interviewee.utils.Md5Util;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  个人资料controller
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController extends BaseController{

    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @GetMapping("/check/session")
    @ApiOperation(value = "会话检测")
    public RestResponse check() {
        return RestResponse.success();
    }

    /**
     * 登出用户
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出登陆")
    public RestResponse logout() {
        sessionService.logout();
        return RestResponse.success();
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录用户", httpMethod = "POST")
    public RestResponse<UserLoginInfo> login(@RequestBody UserLoginVo loginVo, HttpServletResponse httpResponse) {
        RestResponse<UserLoginInfo> response = new RestResponse<>();
        Subject currentUser = SecurityUtils.getSubject();

        if (currentUser != null) {

            UserLoginInfo userLoginInfo = (UserLoginInfo) currentUser.getSession().getAttribute(ShiroConfig.SESSION_KEY);
            if (!currentUser.isAuthenticated()) {// 未登录状态
            } else {// 已有登录用户强制退出已登录用，登录当前用户
                String userName = (String) currentUser.getPrincipal();
                if (!loginVo.getUserName().equals(userName)) {
                    // 如果登录名不同和密码
                    currentUser.logout();
                } else {
                    return RestResponse.success(userLoginInfo);
                }
            }

            LoginResultBO loginInfo = userService.verifyLogin(loginVo.getUserName(), loginVo.getPassword());

            if (loginInfo.isLoginSuccess()) {
                UsernamePasswordToken token = new UsernamePasswordToken(loginVo.getUserName(), Md5Util.md5(loginVo.getPassword()));
                setCookieLoginToken(httpResponse, currentUser, loginInfo.getLoginUser());
                if(loginVo.getRemember()){
                    token.setRememberMe(true);
                }
                currentUser.login(token);
                return RestResponse.success(currentUser.getSession().getAttribute(ShiroConfig.SESSION_KEY));
            } else {
                return RestResponse.fail(loginInfo, loginInfo.getErrorCode());
            }

        } else {
            response.setCode(HttpStatus.SC_UNAUTHORIZED);
            response.setSuccess(false);
            response.setDisplayMessage("your session is expired, please login.");
            httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        }

        return response;
    }

    /**
     * 设置登陆成功凭证cookie shiro
     *
     * @param httpResponse
     */
    private void setCookieLoginToken(HttpServletResponse httpResponse, Subject currentUser, UserEntity userEntity) {
        if (CommonUtils.isNull(userEntity)) {
            return;
        } else {
//            String userToken = userEntity.getPassword();
//
//            Cookie cookie = new Cookie("LOGIN_TOKEN", userToken);
//            // 不设置maxAge，浏览器关闭时，cookie失效
//            cookie.setPath("/");
//            httpResponse.addCookie(cookie);
        }

        UserLoginInfo userLoginInfo = sessionService.setLoginInfo(currentUser, userEntity);

    }
}
