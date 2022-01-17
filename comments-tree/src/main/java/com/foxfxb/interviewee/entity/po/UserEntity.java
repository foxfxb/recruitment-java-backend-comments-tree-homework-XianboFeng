package com.foxfxb.interviewee.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foxfxb.interviewee.entity.base.BaseObj;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

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
@ApiModel(value = "UserEntity对象", description = "")
public class UserEntity extends BaseObj {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("create_time")
    private Date createTime;

    @TableField("email")
    private String email;


}
