package com.foxfxb.interviewee.param.vo;

import com.foxfxb.interviewee.entity.base.BaseObj;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "User登陆对象", description = "")
public class UserLoginVo extends BaseObj {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String password;

    private String email;

    private Boolean remember;


}
