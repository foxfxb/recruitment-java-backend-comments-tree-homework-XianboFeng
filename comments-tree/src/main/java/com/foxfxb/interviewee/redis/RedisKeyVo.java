package com.foxfxb.interviewee.redis;

import com.foxfxb.interviewee.entity.base.BaseObj;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Redis Key", description = "Redis Key")
public class RedisKeyVo  extends BaseObj {
    @ApiModelProperty(value="key", name="key")
    private String key;
    @ApiModelProperty(value="nodeInfo", name="nodeInfo")
    private String nodeInfo;

    public RedisKeyVo(String key, String nodeInfo) {
        this.key = key;
        this.nodeInfo = nodeInfo;
    }
}
