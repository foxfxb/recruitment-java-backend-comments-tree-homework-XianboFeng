package com.foxfxb.interviewee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxfxb.interviewee.entity.authorize.UserLoginInfo;
import com.foxfxb.interviewee.entity.po.MessageEntity;
import com.foxfxb.interviewee.mapper.MessageMapper;
import com.foxfxb.interviewee.param.vo.MessageVo;
import com.foxfxb.interviewee.service.MessageService;
import com.foxfxb.interviewee.service.SessionService;
import com.foxfxb.interviewee.utils.BeanConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageEntity> implements MessageService {

    @Autowired
    private SessionService sessionService;

    @Override
    public void writeMessage(MessageVo messageVo) {
        UserLoginInfo userLoginInfo = sessionService.getLoginUser();

        MessageEntity messageEntity = BeanConvertUtils.convert(messageVo, MessageEntity.class);
        messageEntity.setUserId(userLoginInfo.getUserId());
        messageEntity.setId(null);
        this.save(messageEntity);
    }

    @Override
    public List<MessageEntity> listAll() {
//        LambdaQueryWrapper<MessageEntity> wrapper = new LambdaQueryWrapper();
//        wrapper.orderByDesc(MessageEntity::getCreateTime);
//        return list(wrapper);
        return this.baseMapper.findAllMessages();
    }
}
