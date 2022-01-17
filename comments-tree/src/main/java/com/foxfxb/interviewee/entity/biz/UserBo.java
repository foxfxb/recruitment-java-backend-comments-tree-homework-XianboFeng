package com.foxfxb.interviewee.entity.biz;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("b_user")
@ApiModel(value="UserBo对象", description="")
public class UserBo extends BaseObj {

    private static final long serialVersionUID=1L;

    private Integer userId;

    private String userName;

    private Date createTime;

    private String email;


}
