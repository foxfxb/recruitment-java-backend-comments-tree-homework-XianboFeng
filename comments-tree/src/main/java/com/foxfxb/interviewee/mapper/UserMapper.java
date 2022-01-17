package com.foxfxb.interviewee.mapper;

import com.foxfxb.interviewee.entity.biz.UserBo;
import com.foxfxb.interviewee.entity.po.UserEntity;
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
public interface UserMapper extends BaseMapper<UserEntity> {

    @Select("select user_id,user_name,create_time,email from b_user where user_id = #{userId}")
    @ResultMap("userBaseInfoMap")
    UserBo selectUserBaseInfo(@Param("userId") Integer userId);
}
