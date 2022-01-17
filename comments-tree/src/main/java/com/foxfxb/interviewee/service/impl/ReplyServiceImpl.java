package com.foxfxb.interviewee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxfxb.interviewee.entity.po.ReplyEntity;
import com.foxfxb.interviewee.mapper.ReplyMapper;
import com.foxfxb.interviewee.param.vo.ReplyVo;
import com.foxfxb.interviewee.service.ReplyService;
import com.foxfxb.interviewee.service.SessionService;
import com.foxfxb.interviewee.utils.BeanConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, ReplyEntity> implements ReplyService {

    @Autowired
    private SessionService sessionService;

    @Override
    public void writeReply(ReplyVo replyVo){

        ReplyEntity replyEntity = BeanConvertUtils.convert(replyVo, ReplyEntity.class);
        replyEntity.setReplyContent(replyVo.getContent());
        replyEntity.setReplyUserId(sessionService.getCurrentUserId());

        this.save(replyEntity);
    }

}
