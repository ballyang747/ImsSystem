package org.fkjava.ims.hrm.dao;

import java.util.List;
import java.util.Map;

import org.fkjava.ims.common.dao.BaseDao;

public interface JobDao extends BaseDao   {
	
	List<Map<String,String>> findAllJobs();



}
