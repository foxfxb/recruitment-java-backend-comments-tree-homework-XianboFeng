package com.foxfxb.interviewee.entity.biz;

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
@ApiModel(value="ReplyBo对象", description="")
public class ReplyBo extends BaseObj {

    private static final long serialVersionUID=1L;

    private Integer id;

    private Integer msgId;

    private UserBo replyUser;

    private Date createTime;

    private String replyContent;

    private ReplyBo replyBo;

}
