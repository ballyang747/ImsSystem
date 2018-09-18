package org.fkjava.ims.common.filter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fkjava.ims.common.ConstantUtil;
import org.fkjava.ims.common.CookieUtil;
import org.fkjava.ims.identity.bean.User;
import org.fkjava.ims.identity.service.IdentityService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginFilter  extends HandlerInterceptorAdapter{
	@Resource
	private IdentityService identityService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//主要检查session和cookie的用户信息
		User user = (User)request.getSession().getAttribute(ConstantUtil.SESSION_USER);
		if(user == null) {
			Cookie cookie=CookieUtil.getCookieByName(ConstantUtil.LOGIN_COOKIE_NAME, request);
			if(cookie == null) {
				request.setAttribute("message","您的验证信息已经失效，请重新登录！");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				return false;
			}else {
				String value = cookie.getValue();
				String[] split = value.split("#");
				User u = identityService.getUserById(split[0]);
				if(u!=null&&u.getPassWord().equals(split[1]) && u.getStatus()==1) {
					//把用户的信息放到session中
					request.getSession().setAttribute(ConstantUtil.SESSION_USER, u);
				      return true;
				}else if(u.getStatus()==0){
					request.setAttribute("message","您的账号需要激活,请联系管理员！");
					request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
					return false;
				}else {
					request.setAttribute("message","您的验证信息已经失效，请重新登录！");
					request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
					return false;
				}
			}
		}else {
			//用户信息已经在session中了
			return true;
		}
		

	}

}
