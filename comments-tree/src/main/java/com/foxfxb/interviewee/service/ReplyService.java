package com.foxfxb.interviewee.service;

import com.foxfxb.interviewee.entity.po.ReplyEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foxfxb.interviewee.param.vo.ReplyVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
public interface ReplyService extends IService<ReplyEntity> {

    void writeReply(ReplyVo replyVo);
}
