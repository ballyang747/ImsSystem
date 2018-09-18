package org.fkjava.ims.identity.action;



import java.util.List;

import javax.annotation.Resource;

import org.fkjava.ims.identity.bean.Module;
import org.fkjava.ims.identity.bean.Role;
import org.fkjava.ims.identity.service.IdentityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;


@Controller
@RequestMapping("/identity/popedom")
public class PopedomController {
	@Resource
	private IdentityService identityService;
	
	//展示权限页面并且传入rold.id
	@RequestMapping("/mgrPopedom.jspx")
	public String ShowmgrPopedom(@RequestParam("id") String id,Model model) {
		model.addAttribute("id", id);
		return "/identity/role/bindPopedom/mgrPopedom";
	}
	
	
	//异步获取popedom
	@ResponseBody
	@RequestMapping(value="ajaxLoadPopedom.jspx",produces= {"application/json;charset=utf-8"})
	public String ajaxLoadPopedom() {
		try {
			String JsonStr = identityService.getLoadPopedom();
			return JsonStr;
		} catch (Exception e) {
		  e.printStackTrace();
		  return "";
		}
		
	}
	

	@RequestMapping("/getModules.jspx")
	public String getModulebyId(@RequestParam("id") String id,Model model) {
           //测试用		   
			List<Module> modules=identityService.getModulebyId(id);
			Role role = identityService.getRoelByID(Long.valueOf(id));
			model.addAttribute("modules", modules);
			model.addAttribute("role", role);
		return "/identity/role/bindPopedom/operas";
		
	}
	

	@RequestMapping("/getSelectModules.jspx")
	public String getSelectModules(@RequestParam("id") Long id,Module module,Model model) {
		try {
			List<Module> modules=identityService.getAllSonModule(module.getCode());
			model.addAttribute("modules", modules);
		
			Role role = identityService.getRoelByID(id);
			List<String> OperasCodes = identityService.findCodeById(id,module.getCode());
			model.addAttribute("roleModuleOperasCodes", OperasCodes);
			model.addAttribute("role", role);
		
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return "/identity/role/bindPopedom/operas";
	}
	

	@RequestMapping("/bindPopedom.jspx")
	public String bindPopedom(@RequestParam("id") Long id , @RequestParam("code") String pCode, String codes,Model model ,WebRequest webRequest) {
		try {
			
			identityService.bindPopedom(id,pCode,codes,webRequest);
			model.addAttribute("message", "绑定成功");
		} catch (Exception e) {
			 e.printStackTrace();
			 model.addAttribute("message", "绑定失败");
		}
		return"forward:/identity/popedom/getSelectModules.jspx";
		
	}
	
	
	
	

	
}
