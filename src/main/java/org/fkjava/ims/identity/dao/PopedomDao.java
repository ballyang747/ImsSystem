package org.fkjava.ims.identity.dao;



import java.util.List;

import org.fkjava.ims.common.dao.BaseDao;
import org.fkjava.ims.common.pager.PageModel;
import org.fkjava.ims.identity.bean.Module;
import org.fkjava.ims.identity.bean.Popedom;

/**
 * @author ballyang
 *
 */
public interface PopedomDao extends BaseDao {

	List<Module> findAllSonModules(String pCode);

	List<Popedom> findCodeById(Long id,String pCode);

	/**
	 * @param id
	 * @param pCode
	 * 删除操作
	 */
	void deleteModuleById(Long id, String pCode);

	/**
	 * @param userId
	 * @return
	 * 根据id获取模块信息
	 */
	List<String> findMenuOperas(String userId);

	/**
	 * @param userId
	 * @param pCode
	 * @return
	 * 异步获取模块
	 */
	List<String> ajaxLoadModules(String userId, String pCode);

	/**
	 * @param userId
	 * @return
	 * 获取模块
	 */
	List<String> findPageOperas(String userId);

}
