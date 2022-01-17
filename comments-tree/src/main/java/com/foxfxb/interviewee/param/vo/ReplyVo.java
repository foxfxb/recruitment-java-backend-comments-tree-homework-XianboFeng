package com.foxfxb.interviewee.param.vo;

import com.foxfxb.interviewee.entity.base.BaseObj;
import com.foxfxb.interviewee.entity.biz.UserBo;
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
@ApiModel(value="ReplyVo对象", description="")
public class ReplyVo extends BaseObj {

    private static final long serialVersionUID=1L;

    private Integer msgId;

    private String content;

    private Integer parentId;

}
