package com.foxfxb.interviewee.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SystemLogoutFilter extends LogoutFilter {
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		// 在这里执行退出系统前需要清空的数据
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.logout();
		} catch (SessionException ise) {
			ise.printStackTrace();
		}
		return true;
	}
}