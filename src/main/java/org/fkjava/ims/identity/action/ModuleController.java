package org.fkjava.ims.identity.action;

import java.util.List;

import javax.annotation.Resource;

import org.fkjava.ims.common.pager.PageModel;
import org.fkjava.ims.identity.bean.Module;
import org.fkjava.ims.identity.service.IdentityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/identity/module")
public class ModuleController {
	@Resource
	private IdentityService identityService;

	@RequestMapping("/mgrModule.jspx")
	public String mgrModule() {

		return "/identity/module/mgrModule";
	}

	// 异步获取dtree的数据
	@ResponseBody
	@RequestMapping(value = "/ajaxLoadModule.jspx", produces = { "application/json;charset=utf-8" })
	public String ajaxLoadModule() {
		try {
			String JsonStr = identityService.getajaxLoadModule();
			/* System.out.println(JsonStr); */
			return JsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// 显示权限模块
	@RequestMapping("/getModulesByParent.jspx")
	public String getModulesByParent(String pCode, PageModel pageModel, Model model) {
		try {
			List<Module> modules = identityService.getModulesByParent(pCode, pageModel);
			model.addAttribute("pCode", pCode);
			model.addAttribute("modules", modules);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/identity/module/module";
	}

	// 修改权限模块
	@RequestMapping("/showupdateModule.jspx")
	public String showupdateModule(@RequestParam("code") String code, Model model) {
		try {
		
			Module module = identityService.getModuleByCode(code);
			model.addAttribute("module", module);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/identity/module/updateModule";
	}

	// 跟新模块
	@ResponseBody
	@RequestMapping("/updateModule.jspx")
	public String updateModule(Module module, WebRequest webRequest) {
		try {
			identityService.updateModule(module, webRequest);
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	@RequestMapping("/deleteModule.jspx")
	public String deleteModule(@RequestParam("codes") String codes,String pCode,Model model) {
		try {
			identityService.deleteModuleById(codes);
			model.addAttribute("message", "删除成功！");
			model.addAttribute("pCode", pCode);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message",e.getMessage());
		}
		return "forward:/identity/module/getModulesByParent.jspx";
		
	}
	
	@RequestMapping("/ShowaddModule.jspx")
	public String ShowaddModule(@RequestParam("pCode") String pCode,Model model) {
		model.addAttribute("pCode", pCode);
		
		return "/identity/module/addModule";
		
	}
	
	
	@ResponseBody
	@RequestMapping("/addModule.jspx")
	public String updateModule(Module module, String pCode,WebRequest webRequest) {
		try {
			identityService.addModule(module,pCode,webRequest);
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
}
}
