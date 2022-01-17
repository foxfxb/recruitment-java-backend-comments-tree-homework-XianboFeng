package com.foxfxb.interviewee.param.vo;

import com.foxfxb.interviewee.entity.base.BaseObj;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value="MessageVo对象", description="")
public class MessageVo extends BaseObj {

    private static final long serialVersionUID=1L;

    private String content;

}
