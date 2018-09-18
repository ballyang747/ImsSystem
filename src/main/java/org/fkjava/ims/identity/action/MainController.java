package org.fkjava.ims.identity.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.fkjava.ims.common.ConstantUtil;
import org.fkjava.ims.identity.bean.Module;
import org.fkjava.ims.identity.service.IdentityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class MainController {
	 @Resource
	  private IdentityService identityService;
	@RequestMapping("/main.jspx")
	public String main(Model model,WebRequest webRequest){
		try {
			Map<Module,List<Module>> mapModules = identityService.findMenuOperas(webRequest);
			model.addAttribute("mapModules", mapModules);
			 List<String> userPageoPeras =  identityService.findPageOperas();
			 webRequest.setAttribute(ConstantUtil.OPERAS_SESSION, userPageoPeras, WebRequest.SCOPE_SESSION);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return"main";
	}
	
	

}
