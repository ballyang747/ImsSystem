package org.fkjava.ims.identity.action;

import java.util.List;

import javax.annotation.Resource;

import org.fkjava.ims.common.pager.PageModel;
import org.fkjava.ims.identity.bean.Role;
import org.fkjava.ims.identity.bean.User;
import org.fkjava.ims.identity.service.IdentityService;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/identity/role")
public class RoleController {
	@Resource
	private IdentityService identityService;

	// 展示角色信息
	@RequestMapping("/selectRole.jspx")
	public String showRole(Role role, Model model, PageModel pageModel) {

		List<Role> roles = identityService.selectRoleByPage(role, pageModel);
		model.addAttribute("roles", roles);
		// 跳转至用户列表页面
		return "/identity/role/role";
	}

	// 删除操作
	@RequestMapping("/deleteUser.jspx")
	public String deleteUser(@RequestParam("roleIds") String roleIds, Model model) {
		try {
			identityService.deleteRole(roleIds);
			model.addAttribute("message", "删除成功！");
		} catch (Exception e) {
			model.addAttribute("message", "删除失败！");
		}
		return "forward:/identity/role/showRole.jspx";

	}

	// 展示更新页面

	@RequestMapping("/showaddRole.jspx")
	public String showaddRole() {

		return "/identity/role/addRole";

	}
    //添加角色操作
	@ResponseBody
	@RequestMapping("/addRole.jspx")
	public String addRole(Role role, WebRequest webquest) {
		try {
			identityService.addRole(role, webquest);
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}

	}
    //展示更新页面
	@RequestMapping("/showUpdateRole.jspx")
	public String showUpdateRole(@RequestParam("roleId") Long roleId, Model model) {
		try {
			Role role = identityService.getRoelByID(roleId);
			model.addAttribute("role", role);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/identity/role/updateRole";
	}
	//更新角色操作
	@ResponseBody
	@RequestMapping("/updateRole.jspx")
	public String updateRole(Role role,WebRequest webquest) {
		try {
			identityService.upDateRole(role,webquest);
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	//展示绑定用户
	@RequestMapping("/selectRoleUser.jspx")
	public String selectRoleUser(@RequestParam("id") Long id , Model model,PageModel pagemodel) {
              try {
            	  List<User> RoleUsers =identityService.selectRoleUser(id,pagemodel);
            	  model.addAttribute("roleUsers", RoleUsers);
            	  Role role = identityService.getRoelByID(id);
            	  model.addAttribute("role", role);
			} catch (Exception e) {
				e.printStackTrace();
			}
              return "/identity/role/bindUser/roleUser";
	}
    //绑定用户操作页面
	@RequestMapping("/showbindUser.jspx")
	public String showbindUser(@RequestParam("id") Long id,Model model,PageModel pagemodel) {
		
		try {
			List<User> users= identityService.getUnBindUserById(id,pagemodel);
			model.addAttribute("users", users);
			model.addAttribute("id", id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/identity/role/bindUser/bindUser";
	}
	//绑定操作
	@RequestMapping("/bindUser.jspx")
	public String bindUser(@RequestParam("id") Long id,@RequestParam("userIds") String userIds,Model model) {
		try {
			/*System.out.println(id);
			System.out.println(userIds);*/
			identityService.bindUsers(id,userIds);
			 model.addAttribute("message", "绑定成功！");
		} catch (Exception e) {
			 e.printStackTrace();
			  model.addAttribute("message", e.getMessage());
		}
		 return "forward:/identity/role/showbindUser.jspx";
	}
	//解绑操作
	@RequestMapping("/unBindUser.jspx")
	public String unBindUser(@RequestParam("id") Long id,@RequestParam("userIds") String userIds,Model model) {
		try {
			identityService.unBindUser(id,userIds);
			 model.addAttribute("message", "解绑成功！");
		} catch (Exception e) {
			e.printStackTrace();
			  model.addAttribute("message", e.getMessage());
		}
		return "forward:/identity/role/selectRoleUser.jspx";
	}
	
	
	
}
