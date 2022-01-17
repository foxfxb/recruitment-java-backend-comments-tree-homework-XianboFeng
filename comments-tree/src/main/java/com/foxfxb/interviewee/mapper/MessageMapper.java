package com.foxfxb.interviewee.mapper;

import com.foxfxb.interviewee.entity.po.MessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
public interface MessageMapper extends BaseMapper<MessageEntity> {

    List<MessageEntity> findAllMessages();
}
