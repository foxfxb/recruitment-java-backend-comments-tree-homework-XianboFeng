package com.foxfxb.interviewee.entity.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foxfxb.interviewee.entity.base.BaseObj;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;


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
@TableName(value = "b_message", resultMap = "messageWithReply")
@ApiModel(value="MessageEntity对象", description="")
public class MessageBo extends BaseObj {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String content;

    private UserBo userBo;

    private Date createTime;

    private List<ReplyBo> replyList;

}
