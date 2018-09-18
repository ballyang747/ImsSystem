package org.fkjava.ims.identity.dao.impl;


import java.util.List;

import org.fkjava.ims.common.dao.impl.BaseDaoImpl;
import org.fkjava.ims.common.pager.PageModel;
import org.fkjava.ims.identity.bean.User;
import org.fkjava.ims.identity.dao.RoleDao;
import org.springframework.stereotype.Service;
@Service
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

	@Override
	public void deleteUserByLogic(String[] strings) {
		StringBuffer hql = new StringBuffer();
		hql.append("update Role  set delFlag = 0 where   ");
			for (int i = 0; i < strings.length; i++) {
				hql.append(i==0 ?"id= '"+strings[i]+"'":" or id = '"+strings[i]+"'");
			}
			this.bulkUpdate(hql.toString(),null);
		
	}

	@Override
	public List<User> selectRolePageById(Long id, PageModel pagemodel) {
		StringBuffer hql = new StringBuffer();
		hql.append("select u from User u where u.userId in (    ");
		hql.append("select u from User u inner join u.roles r where r.id = "+id+")");
		
		return this.findByPage(hql.toString(), pagemodel, null);
	}

	@Override
	public List<User> selectUnBindPageById(Long id, PageModel pagemodel) {
		StringBuffer hql = new StringBuffer();
		hql.append("select u from User u where u.userId not in (    ");
		hql.append("select u from User u inner join u.roles r where r.id = "+id+")");
		return this.findByPage(hql.toString(), pagemodel, null);
	}



}
