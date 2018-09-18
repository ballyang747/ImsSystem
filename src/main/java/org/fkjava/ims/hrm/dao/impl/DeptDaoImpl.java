package org.fkjava.ims.hrm.dao.impl;

import java.util.List;
import java.util.Map;

import org.fkjava.ims.common.dao.impl.BaseDaoImpl;
import org.fkjava.ims.hrm.dao.DeptDao;
import org.springframework.stereotype.Repository;

@Repository
public class DeptDaoImpl extends  BaseDaoImpl implements DeptDao{

	@Override
	public List<Map<Long, String>> findAllDept() {
		// TODO Auto-generated method stub
		String hql = "select new Map(d.id as id , d.name as name) from Dept d order by d,id asc";
		return this.find(hql);
	}
  
}
