package com.foxfxb.interviewee.mapper;

import com.foxfxb.interviewee.entity.biz.ReplyBo;
import com.foxfxb.interviewee.entity.biz.UserBo;
import com.foxfxb.interviewee.entity.po.ReplyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
public interface ReplyMapper extends BaseMapper<ReplyEntity> {

    @Select("select id,msg_id,reply_user_id,parent_id,create_time," +
            "reply_content from b_reply where id = #{replyId}")
    @ResultMap("replyBaseInfoMap")
    ReplyBo selectReplyById(@Param("replyId") Integer replyId);
}
