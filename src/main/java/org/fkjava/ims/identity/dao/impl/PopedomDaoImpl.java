package org.fkjava.ims.identity.dao.impl;

import java.util.List;

import org.fkjava.ims.common.dao.impl.BaseDaoImpl;
import org.fkjava.ims.identity.bean.Module;
import org.fkjava.ims.identity.bean.Popedom;
import org.fkjava.ims.identity.dao.PopedomDao;
import org.springframework.stereotype.Service;

@Service
public class PopedomDaoImpl  extends BaseDaoImpl implements PopedomDao {

	@Override
	public List<Module> findAllSonModules(String pCode) {
		String hql = "select m from Module m where code like '"+pCode+"%' and length(code) = 12";
		
		return this.find(hql);
	}

	@Override
	public List<Popedom> findCodeById(Long id,String pCode) {
		String hql="from Popedom where ROLE_ID="+id+"and OPERA_CODE LIKE'"+pCode+"%'";
		return this.find(hql);
	}

	@Override
	public void deleteModuleById(Long id, String pCode) {

	
	    String hql ="delete from Popedom where role.id="+id+"and module.code='"+pCode+"'";
		this.bulkUpdate(hql, null);
	}

	@Override
	public List<String> findMenuOperas(String userId) {
	    StringBuffer hql = new StringBuffer();
	    hql.append("select distinct p.module.code from Popedom p where p.role.id in (  ");
	    hql.append("select r.id from Role r inner join r.users u where u.userId =?) order by  p.module.code asc ");
	    
		return (List<String>) this.find(hql.toString(), new Object[] {userId});
	}

	@Override
	public List<String> ajaxLoadModules(String userId, String pCode) {
		 StringBuffer hql = new StringBuffer();
		
		    hql.append("select distinct p.module.code from Popedom p where  p.role.id in (  ");
		    hql.append("select r.id from Role r inner join r.users u where u.userId =?)  and p.module.code  like '00010001%'    order by  p.module.code asc ");
		    
			return (List<String>) this.find(hql.toString(), new Object[] {userId});
	}

	@Override
	public List<String> findPageOperas(String userId) {
		  StringBuffer hql = new StringBuffer();
		    hql.append("select distinct p.opera.code  from Popedom p where p.role.id in (  ");
		    hql.append("select r.id from Role r inner join r.users u where u.userId =?) order by  p.module.code asc ");
			return (List<String>) this.find(hql.toString(), new Object[] {userId});
	}

	
	

	
	}


