package com.thinkgem.jeesite.modules.sys.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;
@Service
public class MyRolesAuthorizationFilter extends
		org.apache.shiro.web.filter.authz.RolesAuthorizationFilter {

	public static  String URI = "";
	private ServletResponse response;
	
	@Override
	public boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws IOException {
		URI =  ((HttpServletRequest) request).getRequestURI(); 
		
		
		if(URI.contains(Global.getConfig("frontPath"))){
			setUnauthorizedUrl(Global.getConfig("frontPath"));
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}
	
	




	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws IOException {
		if(URI.contains(Global.getConfig("frontPath"))){
			WebUtils.issueRedirect(request, response,request.getScheme()+"://"+request.getServerName()+
					":"+request.getServerPort()+((HttpServletRequest)request).getContextPath()+Global.getConfig("frontPath") );
		}else{
			return super.onAccessDenied(request, response);
		}
		return false;
	}






	@Override
	public void setUnauthorizedUrl(String unauthorizedUrl) {
		super.setUnauthorizedUrl(unauthorizedUrl);
	}
	
	
	
	

}
