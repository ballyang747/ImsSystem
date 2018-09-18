package org.fkjava.ims.identity.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.fkjava.ims.common.ConstantUtil;
import org.fkjava.ims.common.CookieUtil;
import org.fkjava.ims.common.pager.PageModel;
import org.fkjava.ims.identity.bean.User;
import org.fkjava.ims.identity.service.IdentityService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.sun.javafx.sg.prism.NGShape.Mode;

@Controller
@RequestMapping("/identity/user")
public class UserController  {
	@Resource
	private IdentityService identityService;

	  //用户异步登录
	@ResponseBody
    @RequestMapping(value="ajaxLogin.jspx",produces=("application/text;charset=utf-8"))
	public String ajaxLogin(User user,String vcode,String remMe,HttpServletRequest request,HttpServletResponse response) {
		String msg=identityService.ajaxLogin(user,vcode,remMe,request,response);
		return msg;
		
	}
	//退出登录页面
	@RequestMapping("/logout.jspx")
	public String LogOut(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().removeAttribute(ConstantUtil.SESSION_USER);
		CookieUtil.removeCookies(ConstantUtil.LOGIN_COOKIE_NAME, request, response);
		return "login";
	}
	//跳转页面
	@RequestMapping("/selectUser.jspx")
	public String selectUser(User user, PageModel page, Model model,String pCode) {
		//开始搞查询了
		//主要业务 调用方法查询用户 然后 返回数据到视图  需要三个参数 User  pager(分页相关) Model 共享数据
		
		List<User> users=identityService.selectUserByPage(user,page);
		model.addAttribute("users",users);
		model.addAttribute("pCode",pCode);
		/*if(StringUtils.isNotEmpty(message)) {
		model.addAttribute("message",message);
		}*/
		return "/identity/user/user";
	}
	//异步获取员工部门表
	@ResponseBody
	@RequestMapping(value="/ajaxLoadDeptAndJob.jspx",produces= {"application/json;charset=utf-8"})
	public String ajaxLoadDeptAndJob() {
	/*	System.out.println("异步获取进来了");*/
		String jsonstr=identityService.getajaxLoadDeptAndJob();
		return jsonstr;
		
	}
	@RequestMapping("/deleteUser.jspx")
	public String deleteUser(@RequestParam("userIds")String userIds,Model model) {
	/*	System.out.println("deleteUserByLogic");*/
		try {
			identityService.deleteUser(userIds);
			model.addAttribute("message", "删除成功！");
		} catch (Exception e) {
			model.addAttribute("message", "删除失败！");
		}
		return "forward:/identity/user/selectUser.jspx";
	}
	
	//转跳的添加用户页面
	@RequestMapping("/showaddUser.jspx")
	public String showaddUser() {
		
		return "/identity/user/addUser";
	}
	
	@ResponseBody
	@RequestMapping("/validUser.jspx")
	public String validUser(@RequestParam("userId")String userId) {
       try {
    	   User user = identityService.getUserById(userId);
           return user==null ? "false" : "true";
	} catch (Exception e) {
		e.printStackTrace();
		return "false";
	}
		
	}
	@ResponseBody
	@RequestMapping("/addUser.jspx")
	public String addUser(User user, WebRequest webrequest) {
		//model负责回传信息,user则是带着表单的数据,request是去拿创建人的id
		
		try {
		
			identityService.addUser(user,webrequest);
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	@RequestMapping("/showupdateUser.jspx")
	public String showupdateUser(@RequestParam ("userId") String userId,Model model) {
	try {
		User id = identityService.getUserById(userId);
		model.addAttribute("user", id);
	} catch (Exception e) {
		e.printStackTrace();
	}
		return "/identity/user/updateUser";
	}
	
	@ResponseBody
	@RequestMapping("/updateUser.jspx")
	public String updateUser(User user, WebRequest webrequest) {
		//model负责回传信息,user则是带着表单的数据,request是去拿创建人的id
		
		try {
		
			identityService.updateUser(user,webrequest);
			return "ok";
		
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	@RequestMapping("/showpreUser.jspx")
	public String showpreUser(@RequestParam ("userId") String userId,Model model) {
	try {
		User id = identityService.getUserById(userId);
		model.addAttribute("user", id);
	} catch (Exception e) {
		e.printStackTrace();
	}
		return "/identity/user/preUser";
	
	}
	
	@RequestMapping("/activeUser.jspx")
	public String activeUser(User user,Model model,WebRequest webrequest) {
		try {
			identityService.activeUser(user,webrequest);
			model.addAttribute("message", user.getStatus()==1?"激活成功":"冻结成功");
		} catch (Exception e) {
			model.addAttribute("message", user.getStatus()==1?"激活失败":"冻结失败");
			e.printStackTrace();
		}
		 return "forward:/identity/user/selectUser.jspx";
	}
	
	@ResponseBody
	@RequestMapping("/updateSelf.jspx")
	public String updateSelf(User user,WebRequest webrequest) {
		try {
			identityService.userUpdate(user,webrequest);	
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
	}
	@ResponseBody
	@RequestMapping(value="/ajaxLoadModules.jspx",produces= {"application/json;charset=utf-8"})
	public String ajaxLoadModules(@RequestParam("pCode") String pCode,WebRequest webRequest) {
	/*	System.out.println("异步获取进来了");*/
		String jsonstr=identityService.ajaxLoadModules(pCode,webRequest);
		System.out.println(jsonstr);
		return jsonstr;
		
	}
}
