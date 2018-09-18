package org.fkjava.ims.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	
	//添加Cookie
	public static void addCookie(String cookieName,int age,String userId,String password, HttpServletResponse response,HttpServletRequest request) {
		Cookie cookie = getCookieByName(cookieName,request);
		if(cookie == null) {
			cookie = new Cookie(cookieName,userId+"#"+password);
		}
		//设置cookie的有效时间
		cookie.setMaxAge(age);
		 //设置cookie的作用域
		cookie.setPath(request.getContextPath());
		 //将cookie响应至客户端|浏览器
		response.addCookie(cookie);
	}
	//根据cookie名字获取cookie信息
	public static Cookie getCookieByName(String cookieName, HttpServletRequest request) {
	 Cookie[] cookies = request.getCookies();
	 if(cookies!=null) {
		 for (Cookie cookie : cookies) {
			 if(cookie.getName().equals(cookieName)) {
				 return cookie;
			 }
		}
	 }
	 return null;
	}
	
	public static void removeCookies(String cookieName,HttpServletRequest request,HttpServletResponse response) {
		Cookie cookie= getCookieByName(cookieName, request);
		if(cookie!=null) {
			cookie.setMaxAge(0);
			
			cookie.setPath(request.getContextPath());
			
			response.addCookie(cookie);
		}
	}
}
