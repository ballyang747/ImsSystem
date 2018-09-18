package org.fkjava.ims.hrm.action;

import java.util.List;

import javax.annotation.Resource;

import org.fkjava.ims.hrm.bean.Dept;
import org.fkjava.ims.hrm.service.HrmService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author CHUNLONG.LUO
 * @email 584614151@qq.com
 * @version 1.0
 */
@Controller
@RequestMapping("/hrm/dept")
public class DeptController {

	  @Resource
	  private HrmService hrmService;
	
	  //获取所有的部门信息
	  @ResponseBody
	  @RequestMapping("/getDept.jspx")
	  public String getAllDept() {
		  
		 List<Dept> depts =  hrmService.getAllDept();
		 System.out.println("获取到的部门的数量:"+depts.size()); 
		 
		 return null;
	  }
}
