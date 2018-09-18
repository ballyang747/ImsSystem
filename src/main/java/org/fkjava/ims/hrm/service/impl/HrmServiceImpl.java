package org.fkjava.ims.hrm.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.fkjava.ims.hrm.bean.Dept;
import org.fkjava.ims.hrm.dao.DeptDao;
import org.fkjava.ims.hrm.service.HrmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HrmServiceImpl implements HrmService {
@Resource
private DeptDao deptDao;
	@Override
	public List<Dept> getAllDept() {
		try {
			return deptDao.find(Dept.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
