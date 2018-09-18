package org.fkjava.ims.hrm.dao;

import java.util.List;
import java.util.Map;

import org.fkjava.ims.common.dao.BaseDao;

public interface DeptDao extends BaseDao   {
	//找出所有的部门信息
	  List<Map<Long,String>> findAllDept();
}
