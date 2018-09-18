package org.fkjava.ims.identity.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fkjava.ims.common.dao.impl.BaseDaoImpl;
import org.fkjava.ims.common.pager.PageModel;
import org.fkjava.ims.identity.bean.Module;
import org.fkjava.ims.identity.dao.ModuleDao;
import org.springframework.stereotype.Service;

@Service
public class ModuleDaoImpl extends BaseDaoImpl implements ModuleDao {

	@Override
	public List<Module> findAllModule() {
		String hql="from Module where  delFlag = '1'";
		return this.find(hql);
	}

	@Override
	public List<Module> getModulesByParent(String pCode, PageModel pageModel) {
		List<Object> params = new ArrayList<>();
		
		params.add(StringUtils.isEmpty(pCode) ? "%%" : pCode+"%");
		params.add(StringUtils.isEmpty(pCode) ? 4  : pCode.length()+4 );
		String hql = "from Module where  delFlag = '1' and  code like ?  and length(code) = ? ";
        return this.findByPage(hql, pageModel, params);
	}

	@Override
	public void deleteModuleByIds(String codes) {
		String[] split = codes.split(",");
		String[] params = new String[split.length];
		StringBuffer hql= new StringBuffer();
		hql.append(" update Module m set delFlag = '0' where   ");
		for (int i = 0; i < split.length; i++) {
			hql.append(i ==0 ? "code like ?" :"or code like ?");
			params[i] = split[i]+"%";//意思如果父模块 子模块要全部ko
		}
		
		this.bulkUpdate(hql.toString(), params);
	}

	@Override
	public String findMaxCode(String pCode) {
		String hql="select Max(code) From Module where code Like '"+pCode+"%' and length(code) =  "+(pCode.length()+4);
		
		return this.findUniqueEntity(hql, null);
	}

	@Override
	public List<Module> findAllPopeDom() {
		String hql="from Module where  delFlag = '1' and length(code) < 9";
		return this.find(hql);
	}

	

}
