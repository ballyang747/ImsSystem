package org.fkjava.ims.identity.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fkjava.ims.common.pager.PageModel;
import org.fkjava.ims.identity.bean.Module;
import org.fkjava.ims.identity.bean.Role;
import org.fkjava.ims.identity.bean.User;
import org.springframework.web.context.request.WebRequest;


/**
 * @author ballyang
 *
 */

public interface IdentityService {

	/**
	 * @param user
	 * @param vcode
	 * @param remMe
	 * @param request
	 * @param response
	 * @return
	 * 登录验证用
	 */
	String ajaxLogin(User user, String vcode, String remMe, HttpServletRequest request, HttpServletResponse response);

	/**
	 * @param string
	 * @return
	 * 监听器获取验证cookie用
	 */
	User getUserById(String userId);
    
	/**
	 * //异步加载部门以及职位信息
	 * @return
	 */
	String getajaxLoadDeptAndJob();

	/**
	 * @param user
	 * @param page
	 * @return
	 * 分页查询
	 */
	List<User> selectUserByPage(User user, PageModel page);

	/**
	 * @param userIds
	 * 逻辑删除用户信息
	 */
	void deleteUser(String userIds);

	/**
	 * @param user
	 * @param webrequest
	 * 添加用户信息
	 */
	void addUser(User user, WebRequest webrequest);

	/**
	 * @param user
	 * 修改用户信息
	 * @param webrequest s
	 */
	void updateUser(User user, WebRequest webrequest);

	/**
	 * @param user激活操作
	 */
	void activeUser(User user, WebRequest webrequest);
	/*****************************角色模块*************************************/

	/**
	 * @param role
	 * @param pagemodel
	 * @return
	 * 角色页面查询
	 */
	List<Role> selectRoleByPage(Role role, PageModel pagemodel);

	/**
	 * @param roleIds
	 * 角色删除操作
	 */
	void deleteRole(String roleIds);

	/**
	 * @param role
	 * @param webquest
	 * 添加角色
	 */
	void addRole(Role role, WebRequest webquest);

	/**
	 * @param roleId
	 * @return
	 * 获取角色
	 */
	Role getRoelByID(Long roleId);
      //修改角色
	void upDateRole(Role role, WebRequest webquest);

	/**
	 * @param id
	 * @param pagemodel
	 * @return
	 * 分页查询
	 */
	List<User> selectRoleUser(Long id, PageModel pagemodel);

	/**
	 * @param id
	 * @param pagemodel
	 * @return
	 * 获取所有未绑定用户
	 */
	List<User> getUnBindUserById(Long id, PageModel pagemodel);
     
	/**
	 * @param id
	 * @param userIds
	 * 绑定用户
	 */
	void bindUsers(Long id, String userIds);
	/**
	 * @param id
	 * @param userIds
	 * 解绑用户
	 */
	void unBindUser(Long id, String userIds);

	/**
	 * @return
	 * 异步获取模块
	 */
	String getajaxLoadModule();

	/**
	 * @param user
	 * @param webrequest
	 * 跟新用户信息
	 */
	void userUpdate(User user, WebRequest webrequest);

	/**
	 * @param pCode
	 * @param pageModel
	 * @return
	 * 获取模块信息
	 */
	List<Module> getModulesByParent(String pCode, PageModel pageModel);

	/**
	 * @param code
	 * @return
	 * 根据code 获取模块信息
	 */
	Module getModuleByCode(String code);

	/**
	 * @param module
	 * @param webRequest
	 * 更新模块信息
	 */
	void updateModule(Module module, WebRequest webRequest);

	/**
	 * @param codes
	 * 根据id删除模块
	 */
	void deleteModuleById(String codes);

	/**
	 * @param module
	 * @param pCode
	 * 增加模块
	 * @param webRequest 
	 */
	void addModule(Module module, String pCode, WebRequest webRequest);

	/**
	 * @return
	 * 异步获取权限
	 */
	String getLoadPopedom();

	/**
	 * @param id
	 * @return
	 * 根据id获取
	 */
	List<Module> getModulebyId(String id);

	/**
	 * @param pCode
	 * @return
	 * 获取所有的Model
	 */
	List<Module> getAllSonModule(String pCode);

	/**
	 * @param id
	 * @param pCode 
	 * @return
	 * 找出选中的id
	 */
	List<String> findCodeById(Long id, String pCode);

	/**
	 * @param id
	 * @param pCode
	 * @param codes
	 * @param webRequest
	 * 绑定操作
	 */
	void bindPopedom(Long id, String pCode, String codes, WebRequest webRequest);

	/**
	 * @param webRequest
	 * @return
	 * 获取角色相应的modules
	 */
	Map<Module, List<Module>> findMenuOperas(WebRequest webRequest);

	/**
	 * @param pCode
	 * @param webRequest
	 * @return
	 * 异步获取模块 验证用
	 */
	String ajaxLoadModules(String pCode, WebRequest webRequest);

	/**
	 * @return
	 * 根据用户获取模块
	 */
	List<String> findPageOperas();

}
