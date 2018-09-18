package org.fkjava.ims.hrm.dao.impl;



import java.util.List;
import java.util.Map;

import org.fkjava.ims.common.dao.impl.BaseDaoImpl;
import org.fkjava.ims.hrm.dao.JobDao;
import org.springframework.stereotype.Repository;
@Repository
public class JobDaoImpl extends BaseDaoImpl implements JobDao {

	@Override
	public List<Map<String, String>> findAllJobs() {
		String hql = "select new Map(j.code as code, j.name as name) from Job j order by j.code asc";
		return this.find(hql);
	}

	

}
