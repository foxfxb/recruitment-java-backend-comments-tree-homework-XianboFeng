package com.foxfxb.interviewee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxfxb.interviewee.entity.authorize.LoginResultBO;
import com.foxfxb.interviewee.entity.po.UserEntity;
import com.foxfxb.interviewee.exception.BizException;
import com.foxfxb.interviewee.mapper.UserMapper;
import com.foxfxb.interviewee.param.vo.UserLoginVo;
import com.foxfxb.interviewee.service.BaseService;
import com.foxfxb.interviewee.service.UserService;
import com.foxfxb.interviewee.utils.BeanConvertUtils;
import com.foxfxb.interviewee.utils.CommonUtils;
import com.foxfxb.interviewee.utils.Md5Util;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService, BaseService {

    @Override
    public boolean checkRegist(String userName) {
        UserEntity user = getLogin(userName);
        if (CommonUtils.isNull(user)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public UserEntity getLogin(String loginName) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper();
        wrapper.eq(UserEntity::getUserName, loginName)
                .or()
                .eq(UserEntity::getEmail, loginName);
        return this.getOne(wrapper);
    }

    @Override
    public void registUser(UserLoginVo loginVo) {
        if(checkRegist(loginVo.getUserName())){
            throw new BizException("this username had been used.");
        }

        String md5Password = Md5Util.md5(loginVo.getPassword());
        UserEntity userEntity = BeanConvertUtils.convert(loginVo, UserEntity.class);
        userEntity.setUserId(null);
        userEntity.setPassword(md5Password);

        this.save(userEntity);
    }

    @Override
    public LoginResultBO verifyLogin(String loginName, String password) {

        UserEntity loginUser = getLogin(loginName);
        LoginResultBO loginInfo = new LoginResultBO();

        loginInfo.setLoginUser(loginUser);
        // 用户不存在
        if (loginUser == null) {
            loginInfo.setLoginSuccess(false);
            loginInfo.setErrorCode("用户不存在");
            return loginInfo;
        }

        // 验证密码是否匹配
        boolean passwordMatch = Md5Util.passwordMatches(password, loginUser.getPassword());
        // 密码不正确
        if (!passwordMatch) {
            loginInfo.setLoginSuccess(false);

            loginInfo.setErrorCode("密码错误");
            return loginInfo;
        }

        // 密码正确
        loginInfo.setLoginSuccess(true);
        return loginInfo;
    }
}
