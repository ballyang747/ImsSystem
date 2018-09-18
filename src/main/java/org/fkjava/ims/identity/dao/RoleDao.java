package org.fkjava.ims.identity.dao;

import java.util.List;

import org.fkjava.ims.common.dao.BaseDao;
import org.fkjava.ims.common.pager.PageModel;
import org.fkjava.ims.identity.bean.User;

/**
 * @author ballyang
 *
 */
public interface RoleDao extends BaseDao {
     //角色删除
	void deleteUserByLogic(String[] strings);
   //分页查询
	List<User> selectRolePageById(Long id, PageModel pagemodel);
	//获取未绑定用户的id
	List<User> selectUnBindPageById(Long id, PageModel pagemodel);

}
