package org.fkjava.ims.identity.dao;

import java.util.List;

import org.fkjava.ims.common.dao.BaseDao;
import org.fkjava.ims.identity.bean.User;

/**
 * @author ballyang
 *
 */
public interface UserDao extends BaseDao {
	
	 //select * from User u ,Dept d , Job j where u.dept_id = d.id and u.job_code = j.code
	//分页查询
	List<User> findBySelectUser ();
	
	void testOnly();
//逻辑删除用户信息
	void deleteUserByLogic(String[] strings);

}
