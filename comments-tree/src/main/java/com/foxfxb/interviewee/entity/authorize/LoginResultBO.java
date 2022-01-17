package com.foxfxb.interviewee.entity.authorize;

import com.foxfxb.interviewee.entity.base.BaseObj;
import com.foxfxb.interviewee.entity.po.UserEntity;
import lombok.Data;

@Data
public class LoginResultBO extends BaseObj {
    private static final long serialVersionUID = -6026555000620176296L;
    private boolean loginSuccess = false;
    private String errorCode;
    private UserEntity loginUser;
}
