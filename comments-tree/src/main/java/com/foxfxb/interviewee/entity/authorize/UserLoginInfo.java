package com.foxfxb.interviewee.entity.authorize;

import com.foxfxb.interviewee.entity.base.BaseObj;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginInfo extends BaseObj implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2073416815711098437L;
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private String createTime;

}
