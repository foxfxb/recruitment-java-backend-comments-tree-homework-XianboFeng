
package com.foxfxb.interviewee.config;

import com.foxfxb.interviewee.redis.RedisCacheManager;
import com.foxfxb.interviewee.shiro.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private static final Logger log = LoggerFactory.getLogger(ShiroFilterFactoryBean.class);
    @Resource
    private RedisSessionDAO sessionDAO;

    public static final String SESSION_KEY = "LOGIN_INFO";

    @Bean(name = "securityManager")
    public SecurityManager securityManager(ShiroSeesionManager sessionManager) {
        log.debug("securityManager()");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myAuthRealm());
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(redisCacheManager());


        return securityManager;
    }

    @Bean(name = "sessionManager")
    public ShiroSeesionManager sessionManager() {
        ShiroSeesionManager sessionManager = new ShiroSeesionManager();
        sessionManager.setCacheManager(redisCacheManager());
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setGlobalSessionTimeout(3600000L);
        return sessionManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        return redisCacheManager;
    }

    /**
     * realm
     *
     * @return
     */
    @Bean(name = "authRealm")
    public CustomShiroReam myAuthRealm() {
        log.debug("myShiroRealm()");
        CustomShiroReam myAuthorizingRealm = new CustomShiroReam();
        // 设置密码凭证匹配器
        myAuthorizingRealm.setCredentialsMatcher(md5CredentialsMathcer());

        return myAuthorizingRealm;
    }

    /**
     * cookie对象;
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        log.debug("rememberMeCookie()");
        // 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(30 * 24 * 60 * 60);
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * 密码匹配凭证管理器
     *
     * @return
     */
    @Bean(name = "md5CredentialsMathcer")
    public MD5CredentialsMatcher md5CredentialsMathcer() {
        return new MD5CredentialsMatcher();
    }

    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持; Controller才能使用@RequiresPermissions
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager) {
        log.debug("authorizationAttributeSourceAdvisor()");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);

        return authorizationAttributeSourceAdvisor;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier(value = "securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroPermissionFactory = new ShiroFilterFactoryBean();
        shiroPermissionFactory.setSecurityManager(securityManager);
        setFilterChainDefinitions(shiroPermissionFactory);
        return shiroPermissionFactory;
    }

    @Bean(name = "shiroLoginFilter")
    public ShiroLoginFilter shiroLoginFilter() {
        ShiroLoginFilter shiroLoginFilter = new ShiroLoginFilter();
        return shiroLoginFilter;
    }

    private void setFilterChainDefinitions(ShiroFilterFactoryBean shiroPermissionFactory) {
        Map<String, String> chains = new LinkedHashMap<>();

        chains.put("/api/profile/login", "anon");
        chains.put("/api/message/list", "anon");
        chains.put("/api/message/write", "authc");
        chains.put("/api/reply/write", "authc");
        chains.put("/api/profile/*", "authc");
        chains.put("/logout", "logout");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroPermissionFactory.setLoginUrl("/skipToLogin");
        // 登录成功后要跳转的链接
        shiroPermissionFactory.setSuccessUrl("/pages/index.html");

        // 未授权界面;
        Map<String, Filter> authcMap = new HashMap<>();
        authcMap.put("authc", shiroLoginFilter());
        authcMap.put("addPrincipal", addPrincipalToSessionFilter());

        shiroPermissionFactory.setUnauthorizedUrl("/skipToLogin");
        shiroPermissionFactory.setFilters(authcMap);

        // 加上数据库中过滤链
        shiroPermissionFactory.setFilterChainDefinitionMap(chains);
    }

    /**
     * Shiro自定义过滤器（解决session丢失）
     * @return
     */
//    @Bean
    public OncePerRequestFilter addPrincipalToSessionFilter() {
        return new AddPrincipalToSessionFilter();
    }
}