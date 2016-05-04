package com.thinkgem.jeesite.modules.sys.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Simple Filter that, upon receiving a request, will immediately log-out the
 * currently executing
 * {@link #getSubject(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
 * subject} and then redirect them to a configured {@link #getRedirectUrl()
 * redirectUrl}.
 * 
 * @since 1.2
 */
@Service
public class MyLogoutFilter extends
		org.apache.shiro.web.filter.authc.LogoutFilter {

	private static final Logger log = LoggerFactory
			.getLogger(MyLogoutFilter.class);

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {
		Subject subject = getSubject(request, response);
		String redirectUrl = getRedirectUrl(request, response, subject);

		String loginplace = WebUtils.getCleanParam(request, "loginplace");
		if (null != loginplace && !"".equals(loginplace)
				&& loginplace.contains(Global.getConfig("frontPath")))
			redirectUrl = Global.getConfig("frontPath");
		try {
			subject.logout();
		} catch (SessionException ise) {
			log.debug(
					"Encountered session exception during logout.  This can generally safely be ignored.",
					ise);
		}
		issueRedirect(request, response, redirectUrl);
		return false;
	}

}
