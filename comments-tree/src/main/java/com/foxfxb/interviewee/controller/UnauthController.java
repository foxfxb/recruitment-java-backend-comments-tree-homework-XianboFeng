package com.foxfxb.interviewee.controller;

import com.foxfxb.interviewee.entity.authorize.UserLoginInfo;
import com.foxfxb.interviewee.param.vo.UserLoginVo;
import com.foxfxb.interviewee.response.RestResponse;
import com.foxfxb.interviewee.service.SessionService;
import com.foxfxb.interviewee.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  该controller绕过shiro的权限验证
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
@RestController
@ApiIgnore
public class UnauthController extends BaseController{


    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/skipToLogin")
    public RestResponse skipToLogin(HttpServletResponse res) throws IOException {
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.sendRedirect("/");
        return RestResponse.accessDenied();
    }


    @GetMapping("/error")
    public RestResponse error(HttpServletRequest request) {
        return RestResponse.fail("系统错误");
    }

    @GetMapping("/current")
    @ApiOperation(value = "获取当前登录用户", httpMethod = "GET")
    public RestResponse<UserLoginInfo> currentUser(HttpServletResponse response) {
        UserLoginInfo loginInfoBO = sessionService.getLoginUser();
//        if (loginInfoBO == null) {
////            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return RestResponse.fail(null, "会话超时");
//        }
        return RestResponse.success(loginInfoBO);
    }


    @PutMapping("/regist")
    @ApiOperation(value = "注册用户", httpMethod = "PUT")
    public RestResponse regist(@RequestBody UserLoginVo loginVo) {

        Assert.notNull(loginVo.getUserName(), "username cannot be null");
        Assert.notNull(loginVo.getEmail(), "email cannot be null");
        Assert.notNull(loginVo.getPassword(), "password cannot be null");
        userService.registUser(loginVo);
        return RestResponse.success();
    }
}
