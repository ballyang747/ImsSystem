package org.fkjava.ims.common.filter;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.fkjava.ims.common.ConstantUtil;
import org.fkjava.ims.common.LoginListenerTest;
import org.fkjava.ims.common.ipadd;
import org.fkjava.ims.identity.service.IdentityService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class OperasFilter  extends HandlerInterceptorAdapter{
	@Resource
	private IdentityService identityService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HashMap session1 = LoginListenerTest.getSession();
		String ipAddr = ipadd.getIpAddr(request);
	System.out.println("ip--------------------->"+ipAddr);	
		for (Object ss : session1.keySet()) {
			 Object ssv= session1.get(ss);
			 HttpSession value =  (HttpSession)ssv;
			 Object attribute = value.getAttribute("session_user");
			 
		 System.out.println("key:  "+ss+"value"+attribute);
		}
		Object attribute = request.getSession().getAttribute(ConstantUtil.OPERAS_SESSION);
		if(attribute == null) {
		   List<String> pageOperas = 	identityService.findPageOperas();
		   request.getSession().setAttribute(ConstantUtil.OPERAS_SESSION, pageOperas);
           return true;
		}else {
		    return true;
		}
	
	}
}
