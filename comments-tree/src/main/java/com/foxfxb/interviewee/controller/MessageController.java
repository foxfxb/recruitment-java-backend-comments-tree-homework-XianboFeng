package com.foxfxb.interviewee.controller;


import com.foxfxb.interviewee.entity.po.MessageEntity;
import com.foxfxb.interviewee.param.vo.MessageVo;
import com.foxfxb.interviewee.response.RestResponse;
import com.foxfxb.interviewee.service.MessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  留言controller
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
@RestController
@RequestMapping("/api/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/list")
    @ApiOperation(value = "列出所有评论")
    public RestResponse<List<MessageEntity>> list(){
        List<MessageEntity> list = messageService.listAll();
        return RestResponse.success(list);
    }

    @PutMapping("/write")
    @ApiOperation(value = "填写留言")
    public RestResponse write(@RequestBody MessageVo messageVo){
        Assert.notNull(messageVo.getContent(), "please write something");
        mustLoginCheck();
        messageService.writeMessage(messageVo);
        return RestResponse.success();
    }
}

