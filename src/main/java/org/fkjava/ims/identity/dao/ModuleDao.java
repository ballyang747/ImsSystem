package org.fkjava.ims.identity.dao;



import java.util.List;

import org.fkjava.ims.common.dao.BaseDao;
import org.fkjava.ims.common.pager.PageModel;
import org.fkjava.ims.identity.bean.Module;

/**
 * @author ballyang
 *
 */
public interface ModuleDao extends BaseDao {
     //获取所有模块
	List<Module> findAllModule();

	/**
	 * @param pCode
	 * @param pageModel
	 * @return
	 * 获取相应模块
	 */
	List<Module> getModulesByParent(String pCode, PageModel pageModel);

	/**
	 * @param codes
	 * 按照id删除模块
	 */
	void deleteModuleByIds(String codes);

	/**
	 * @param pCode
	 * @return
	 * 寻找最大子模块code
	 */
	String findMaxCode(String pCode);

	/**
	 * @return
	 */
	List<Module> findAllPopeDom();
    
}
