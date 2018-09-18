package org.fkjava.ims.common.pager;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.fkjava.ims.common.ConstantUtil;

public class OperasController extends TagSupport {
   
	
	 private String value;
	@Override
	public int doStartTag() throws JspException {
		List<String> operas =  (List<String>) this.pageContext.getSession().getAttribute(ConstantUtil.OPERAS_SESSION);
		System.out.println("------------operas----------"+value);
		if(operas.indexOf(value) !=-1) {
			return EVAL_PAGE;
		}else {
			return SKIP_BODY;
		}	
	}

	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	

	

}
