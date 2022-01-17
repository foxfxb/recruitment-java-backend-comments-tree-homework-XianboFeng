package com.foxfxb.interviewee.service;

import com.foxfxb.interviewee.entity.authorize.LoginResultBO;
import com.foxfxb.interviewee.entity.po.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foxfxb.interviewee.param.vo.UserLoginVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
public interface UserService extends IService<UserEntity> {

    boolean checkRegist(String userName);

    UserEntity getLogin(String userName);

    void registUser(UserLoginVo loginVo);

    LoginResultBO verifyLogin(String loginName, String password);
}
