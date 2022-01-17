package com.foxfxb.interviewee.shiro;

import com.foxfxb.interviewee.redis.RedisClusterCache;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Log4j2
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {


    private static final Integer EXPIRE_TIME =30*60;

    @Autowired
    private RedisClusterCache redisClusterCache;

    /**
     * 创建session，保存到redis数据库
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessionId = super.doCreate(session);
        try {
            redisClusterCache.setWithExpireTime(session.getId().toString(),session,EXPIRE_TIME);
        } catch (Exception e) {
            log.error("session Dao create session throw a error:" + e.getMessage());
        }
        return sessionId;
    }

    /**
     * 获取session
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = null;
        try {
            session = super.doReadSession(sessionId);
        } catch (UnknownSessionException e) {
            log.error("session Dao read session throw a error:" + e.getMessage());
        }

        if (session == null) {
            try {
                session = redisClusterCache.getCache(sessionId.toString());
            } catch (Exception e) {
                log.error("session Dao read session throw a error:" + e.getMessage());
            }
        }
        return session;
    }

    /**
     * 更新session的最后一次访问时间
     *
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        try {
            redisClusterCache.setWithExpireTime(session.getId().toString(),session,EXPIRE_TIME);
        } catch (Exception e) {
            log.error("session Dao update session throw a error:" + e.getMessage());
        }
    }

    /**
     * 删除session
     *
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        try {
            redisClusterCache.delCache(session.getId().toString());
        } catch (Exception e) {
            log.error("session Dao delete session throw a error:" + e.getMessage());
        }
    }
}
