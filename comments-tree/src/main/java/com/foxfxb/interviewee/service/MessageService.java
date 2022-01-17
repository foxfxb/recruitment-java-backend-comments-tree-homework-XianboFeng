package com.foxfxb.interviewee.service;

import com.foxfxb.interviewee.entity.po.MessageEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foxfxb.interviewee.param.vo.MessageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
public interface MessageService extends IService<MessageEntity> {

    void writeMessage(MessageVo messageVo);

    List<MessageEntity> listAll();
}
