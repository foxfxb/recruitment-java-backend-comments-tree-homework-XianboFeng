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
@TableName("b_reply")
@ApiModel(value = "ReplyEntity对象", description = "")
public class ReplyEntity extends BaseObj {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("msg_id")
    private Integer msgId;

    @TableField("reply_user_id")
    private Integer replyUserId;

    @TableField("parent_id")
    private Integer parentId;

    @TableField("create_time")
    private Date createTime;

    @TableField("reply_content")
    private String replyContent;


}
