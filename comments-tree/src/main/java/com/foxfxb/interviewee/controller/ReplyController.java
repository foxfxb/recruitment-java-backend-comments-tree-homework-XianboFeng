package com.foxfxb.interviewee.controller;


import com.foxfxb.interviewee.param.vo.ReplyVo;
import com.foxfxb.interviewee.response.RestResponse;
import com.foxfxb.interviewee.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  回复controller
 * </p>
 *
 * @author foxfxb
 * @since 2022-01-15
 */
@RestController
@RequestMapping("/api/reply")
public class ReplyController extends BaseController {

    @Autowired
    private ReplyService replyService;

    @PutMapping("/write")
    @ApiOperation(value = "填写留言")
    public RestResponse writeReply(@RequestBody ReplyVo replyVo){

        Assert.notNull(replyVo.getMsgId(), "please select a comment");
        Assert.notNull(replyVo.getContent(), "please write something");
        replyService.writeReply(replyVo);
        return RestResponse.success();
    }
}

