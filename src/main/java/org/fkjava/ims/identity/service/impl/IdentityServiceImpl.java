package org.fkjava.ims.identity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.fkjava.ims.common.ConstantUtil;
import org.fkjava.ims.common.CookieUtil;
import org.fkjava.ims.common.LoginListenerTest;
import org.fkjava.ims.common.pager.PageModel;
import org.fkjava.ims.hrm.dao.DeptDao;
import org.fkjava.ims.hrm.dao.JobDao;
import org.fkjava.ims.identity.bean.Module;
import org.fkjava.ims.identity.bean.Popedom;
import org.fkjava.ims.identity.bean.Role;
import org.fkjava.ims.identity.bean.User;
import org.fkjava.ims.identity.dao.ModuleDao;
import org.fkjava.ims.identity.dao.PopedomDao;
import org.fkjava.ims.identity.dao.RoleDao;
import org.fkjava.ims.identity.dao.UserDao;
import org.fkjava.ims.identity.service.IdentityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class IdentityServiceImpl implements IdentityService {

	@Resource
	private UserDao userDao;
	@Resource
	private DeptDao deptDao;
	@Resource
	private JobDao jobDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private ModuleDao moduleDao;
	@Resource
	private PopedomDao popedomDao;

	@Override
	public String ajaxLogin(User user, String vcode, String remMe, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(user);
		try {
			// 首先获取验证码
			String session_code_raw = (String) request.getSession().getAttribute(ConstantUtil.VCODE);
			String session_code = session_code_raw.toLowerCase();
			String vcode1 = vcode.toLowerCase();
			System.out.println(session_code);
			System.out.println(vcode);
			if (!session_code.equals(vcode1)) {
				return "验证码不正确";
			} else {
				User u = userDao.get(User.class, user.getUserId());
				if (u == null) {
					return "账号或者密码不正确";
				} else if (!u.getPassWord().equals(user.getPassWord())) {
					return "账号或者密码不正确";
				} else {
					request.getSession().setAttribute(ConstantUtil.SESSION_USER, u);
					if ("checked".equals(remMe)) {
						CookieUtil.addCookie(ConstantUtil.LOGIN_COOKIE_NAME, 7 * 24 * 60 * 60, u.getUserId(),
								u.getPassWord(), response, request);
					}
				}
			}
		
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUserById(String userId) {
		try {
			return userDao.get(User.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getajaxLoadDeptAndJob() {

		try {
			List<Map<Long, String>> depts = deptDao.findAllDept();
			List<Map<String, String>> jobs = jobDao.findAllJobs();
			/* System.out.println("depts:"+depts+"   jobs:"+jobs); */
			// 引入json工具与
			JSONObject jso = new JSONObject();
			jso.put("depts", depts);
			jso.put("jobs", jobs);
			/* System.out.println( jso.toString()); */
			return jso.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> selectUserByPage(User user, PageModel page) {
		try {
			// 接收用户存过来的参数
			List<Object> params = new ArrayList<>();
			// 拼接hql语句
			StringBuffer hql = new StringBuffer();
			hql.append("select u from User u where  u.delFlag = 1 ");
			// 前面两个参数只有穿不穿进来的问题,解决起来比较简单
			if (StringUtils.isNotEmpty(user.getName())) {
				hql.append("and u.name like ?");
				params.add("%" + user.getName() + "%");
			}
			if (StringUtils.isNotEmpty(user.getPhone())) {
				hql.append("and u.phone like ?");
				params.add("%" + user.getPhone() + "%");
			}
			// 后面的会设计到比较多的条件判断

			if (user.getDept() != null && user.getDept().getId() != null && user.getDept().getId() != 0) {
				hql.append("and u.dept.id = ?");
				params.add(user.getDept().getId());
			}
			if (user.getJob() != null && StringUtils.isNotEmpty(user.getJob().getCode())
					&& !user.getJob().getCode().equals("0")) {
				hql.append("and u.job.code = ?");
				params.add(user.getJob().getCode());
			}
			return userDao.findByPage(hql.toString(), page, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteUser(String userIds) {
		String[] strings = userIds.split(",");
		try {
			userDao.deleteUserByLogic(strings);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addUser(User user, WebRequest webrequest) {
		try {
			user.setCreateDate(new Date());
			User createUser = (User) webrequest.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			user.setCreater(createUser);
			user.setDelFlag((short) 1);
			System.out.println(user);

			userDao.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(User user, WebRequest webRequest) {
		try {
			User u = userDao.get(User.class, user.getUserId());
			u.setName(user.getName());
			u.setPassWord(user.getPassWord());
			// 设置修改时间
			u.setModifyDate(new Date());
			User modityUser = (User) webRequest.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			u.setModifier(modityUser);
			u.setName(user.getName());
			u.setEmail(user.getEmail());
			u.setSex(user.getSex());
			u.setTel(user.getTel());
			u.setPhone(user.getPhone());
			u.setQuestion(user.getQuestion());
			u.setAnswer(user.getAnswer());
			u.setQqNum(user.getQqNum());
			// 密码进行MD5加密
			user.setPassWord(user.getPassWord());
			u.setDept(user.getDept());
			u.setJob(user.getJob());
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void activeUser(User user, WebRequest webrequest) {
		try {
			User u = userDao.get(User.class, user.getUserId());
			u.setStatus(user.getStatus());
			User cName = (User) webrequest.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			u.setCheckDate(new Date());
			u.setChecker(cName);
			// 修改人以及修改时间
			u.setModifyDate(new Date());
			u.setModifier(cName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("激活异常：" + e.getMessage());
		}

	}

	/***************************** 角色模块 ************************************/
	// 分页查询
	@Override
	public List<Role> selectRoleByPage(Role role, PageModel pagemodel) {
		try {
			String hql = "from Role where delFlag = 1 ";
			List<Role> roles = roleDao.findByPage(hql, pagemodel, null);
			if (roles != null) {
				for (int i = 0; i < roles.size(); i++) {
					// 因为创建人以及修改人是懒加载，因此页面中没法获取创建人以及修改人的信息
					// if(roles.get(i).getCreater()!=null) { roles.get(i).getCreater().getName();}
					if (roles.get(i).getCreater() != null) {
						roles.get(i).getCreater().getName();
					}
					if (roles.get(i).getModifier() != null) {
						roles.get(i).getModifier().getName();
					}
				}
			}
			return roles;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询异常：" + e.getMessage());
		}
	}

	// 删除操作
	@Override
	public void deleteRole(String roleIds) {
		String[] strings = roleIds.split(",");
		try {
			roleDao.deleteUserByLogic(strings);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addRole(Role role, WebRequest webquest) {
		try {
			role.setCreateDate(new Date());
			User cUser = (User) webquest.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			role.setCreater(cUser);

			roleDao.save(role);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加异常：" + e.getMessage());
		}

	}

	@Override
	public Role getRoelByID(Long roleId) {
		try {
			return roleDao.get(Role.class, roleId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询异常：" + e.getMessage());
		}
	}

	@Override
	public void upDateRole(Role role, WebRequest webquest) {
		try {
			Role r = roleDao.get(Role.class, role.getId());
			r.setName(role.getName());
			r.setRemark(role.getRemark());
			// 设置修改时间
			r.setModifyDate(new Date());
			User cUser = (User) webquest.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			r.setModifier(cUser);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("更新异常：" + e.getMessage());
		}

	}

	@Override
	public List<User> selectRoleUser(Long id, PageModel pagemodel) {
		try {
			List<User> users = roleDao.selectRolePageById(id, pagemodel);
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("角色页面查询异常：" + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fkjava.ims.identity.service.IdentityService#getUnBindUserById(java.lang.
	 * Long, org.fkjava.ims.common.pager.PageModel)
	 */
	@Override
	public List<User> getUnBindUserById(Long id, PageModel pagemodel) {
		try {
			List<User> users = roleDao.selectUnBindPageById(id, pagemodel);
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("角色绑定查询异常：" + e.getMessage());
		}
	}

	// 绑定操作
	@Override
	public void bindUsers(Long id, String userIds) {
		try {
			Role role = roleDao.get(Role.class, id);
			Set<User> users = role.getUsers();
			String[] split = userIds.split(",");
			for (int i = 0; i < split.length; i++) {
				User user = userDao.get(User.class, split[i]);
				users.add(user);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("角色绑定异常：" + e.getMessage());
		}

	}

	// 解绑操作
	@Override
	public void unBindUser(Long id, String userIds) {
		try {
			Role role = roleDao.get(Role.class, id);
			Set<User> users = role.getUsers();
			String[] split = userIds.split(",");
			for (int i = 0; i < split.length; i++) {
				User user = userDao.get(User.class, split[i]);
				users.remove(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("角色解绑异常：" + e.getMessage());
		}
	}

	@Override
	public String getajaxLoadModule() {
		try {
			List<Module> mds = moduleDao.findAllModule();
			// 将模块信息封装成 json格式的字符窜
			JSONArray jsonArr = new JSONArray();
			for (int i = 0; i < mds.size(); i++) {
				JSONObject jso = new JSONObject();
				Module module = mds.get(i);
				String code = module.getCode();
				jso.put("id", code);

				jso.put("pid", code.length() == 4 ? "1" : code.substring(0, code.length() - 4));
				jso.put("name", module.getName());

				jsonArr.add(jso);
			}
			return jsonArr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取模块信息出错：" + e.getMessage());
		}
	}

	@Override
	public void userUpdate(User user, WebRequest webrequest) {
		try {
			// 持久状态下更新资料
			User u = userDao.get(User.class, user.getUserId());
			u.setAnswer(user.getAnswer());
			u.setEmail(user.getEmail());
			u.setName(user.getName());
			u.setPhone(user.getPhone());
			u.setQqNum(user.getQqNum());
			u.setQuestion(user.getQuestion());
			u.setTel(user.getTel());
			webrequest.setAttribute(ConstantUtil.SESSION_USER, u, WebRequest.SCOPE_SESSION);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("用户更新信息出错：" + e.getMessage());
		}

	}

	// 获取模块信息
	@Override
	public List<Module> getModulesByParent(String pCode, PageModel pageModel) {
		try {
			List<Module> modules = moduleDao.getModulesByParent(pCode, pageModel);
			return modules;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取模块信息出错：" + e.getMessage());
		}
	}

	@Override
	public Module getModuleByCode(String code) {
		try {
			return moduleDao.get(Module.class, code);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询模块信息出错：" + e.getMessage());
		}
	}

	@Override
	public void updateModule(Module module, WebRequest webRequest) {
		try {
			Module m = moduleDao.get(Module.class, module.getCode());
			m.setName(module.getName());
			m.setUrl(module.getUrl());
			m.setRemark(module.getRemark());
			User cName = (User) webRequest.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			m.setModifyDate(new Date());
			m.setModifier(cName);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("更新模块信息出错：" + e.getMessage());
		}

	}

	@Override
	public void deleteModuleById(String codes) {
		try {
			moduleDao.deleteModuleByIds(codes);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除出错：" + e.getMessage());
		}
	}

	@Override
	public void addModule(Module module, String pCode, WebRequest webRequest) {
		// 思路 寻找最大的id 然后加1 但是要进行复杂的字符串拼接确保位数正确
		try {
			// 定义新添加的模块的code

			String newCode = "";
			pCode = pCode == null ? "" : pCode;
			String maxCode = moduleDao.findMaxCode(pCode);
			if (StringUtils.isEmpty(maxCode)) {
				newCode = pCode + "0001";
			} else {
				System.out.println("maxCode:" + maxCode + "  pCode:" + pCode);
				String code = maxCode.substring(maxCode.length() - 4, maxCode.length());
				int codeInt = Integer.valueOf(code) + 1;
				System.out.println("code:" + code + "  codeInt:" + codeInt);
				String finalcode = String.valueOf(codeInt);
				// 此处不能用finalcaode length 因为会变化
				for (int i = 0; i < 4 - String.valueOf(codeInt).length(); i++) {
					finalcode = "0" + finalcode;
				}
				newCode = maxCode.substring(0, maxCode.length() - 4) + finalcode;
				System.out.println("---------------finalcode:" + finalcode + "  newCode:" + newCode);
			}
			module.setCode(newCode);
			module.setCreateDate(new Date());
			module.setCreater((User) webRequest.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION));
			moduleDao.save(module);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加出错：" + e.getMessage());
		}

	}

	/*********************************** 权限模块 ******************************/
	// 异步获取权限模块
	@Override
	public String getLoadPopedom() {
		try {
			List<Module> mds = moduleDao.findAllPopeDom();
			// 将模块信息封装成 json格式的字符窜
			JSONArray jsonArr = new JSONArray();
			for (int i = 0; i < mds.size(); i++) {
				JSONObject jso = new JSONObject();
				Module module = mds.get(i);
				String code = module.getCode();
				jso.put("id", code);
				jso.put("pid", code.length() == 4 ? "1" : code.substring(0, code.length() - 4));
				jso.put("name", module.getName());
				jsonArr.add(jso);
			}

			return jsonArr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取出错：" + e.getMessage());
		}
	}

	// 测试用
	@Override
	public List<Module> getModulebyId(String id) {
		System.out.println("2333333333");
		try {

			String hql = "from Popedom where ROLE_ID=" + id;
			List<Popedom> pps = popedomDao.find(hql);
			List<Module> mds = new ArrayList<>();
			for (int i = 0; i < pps.size(); i++) {
				Module m = new Module();
				m.setCode(pps.get(i).getOpera().getCode());
				m.setName(pps.get(i).getOpera().getName());
				m.setUrl(pps.get(i).getOpera().getUrl());
				m.setCreateDate(pps.get(i).getOpera().getCreateDate());
				m.setCreater(pps.get(i).getOpera().getCreater());
				m.setModifier(pps.get(i).getOpera().getModifier());
				mds.add(m);
			}
			return mds;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取出错：" + e.getMessage());
		}
	}

	@Override
	public List<Module> getAllSonModule(String pCode) {
		try {
			List<Module> mds = popedomDao.findAllSonModules(pCode);

			return mds;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取出错：" + e.getMessage());
		}
	}

	@Override
	public List<String> findCodeById(Long id, String pCode) {
		try {
			List<Popedom> pps = popedomDao.findCodeById(id, pCode);
			List<String> sts = new ArrayList<>();
			for (int i = 0; i < pps.size(); i++) {
				String s = "";
				s = pps.get(i).getOpera().getCode();
				sts.add(s);
			}

			return sts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取出错：" + e.getMessage());
		}
	}

	@Override
	public void bindPopedom(Long id, String pCode, String codes, WebRequest webRequest) {
		
		try {
			popedomDao.deleteModuleById(id,pCode);
			if(StringUtils.isNotEmpty(codes)) {
				String[] mcodes = codes.split(",");
				//添加角色信息
				Role r = new Role();
				r.setId(id);
				//指定二级模块信息
				Module m = new Module();
				m.setCode(pCode);
				for (int i = 0; i < mcodes.length; i++) {
					Popedom p = new Popedom();
					p.setRole(r);
					p.setModule(m);
					Module thirdModule = new Module();
					thirdModule.setCode(mcodes[i]);
					p.setOpera(thirdModule);
					p.setCreateDate(new Date());
					p.setCreater((User)webRequest.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION));
					popedomDao.save(p);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("绑定异常："+e.getMessage());
		}

	}

	@Override
	public Map<Module, List<Module>> findMenuOperas(WebRequest webRequest) {
		try {
			User user = (User)webRequest.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
			List<String> codes = popedomDao.findMenuOperas(user.getUserId());
			if(codes !=null&& codes.size() >0) {
				Map<Module, List<Module>> mapMenus = new HashMap<>();
				//存放 二级模块的数组
				List<Module> list = new ArrayList<>();
				//下面这种方法只能在排序后才能这样干
				for (int i = 0; i < codes.size(); i++) {
					//根据二级模块code获取模块信息
					String code = codes.get(i);
					Module module = moduleDao.get(Module.class, code);
					//根据二级模块code获取一级模块
					String finalcode = code.substring(0, 4);
					Module fmodule = moduleDao.get(Module.class, finalcode);
					//判断一级模块是否在map集合中存在
					if(!mapMenus.containsKey(fmodule)) {
					 list = new ArrayList<>();
					 mapMenus.put(fmodule, list);
					}
					list.add(module);
					
				}
				return mapMenus;
			}
			  return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询异常："+e.getMessage());
		}
	}

	@Override
	public String ajaxLoadModules(String pCode, WebRequest webRequest) {
	    try {
	    	User user = (User)webRequest.getAttribute(ConstantUtil.SESSION_USER, WebRequest.SCOPE_SESSION);
	    	List<String> codes = popedomDao.ajaxLoadModules(user.getUserId(),pCode);
	    	if(codes !=null&& codes.size() >0) {
	    		JSONObject jso = new JSONObject();
	    		for (int i = 0; i < codes.size(); i++) {
	    			String code = codes.get(i);
	    			Module module = moduleDao.get(Module.class, code);
	    			String url = module.getUrl();
	    			jso.put("urls", url);
				}
	    		return jso.toString();
	    	}
	    	return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询异常："+e.getMessage());
		}
	}

	@Override
	public List<String> findPageOperas() {
		try {
			User user = (User)((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(ConstantUtil.SESSION_USER);
			List<String> codes = popedomDao.findPageOperas(user.getUserId());
			System.out.println("------------operas2333----------"+codes);
			List<String> list = new ArrayList<>();
			if(codes !=null&& codes.size() >0) {
				for (int i = 0; i < codes.size(); i++) {
				  String code = codes.get(i);
				  Module m = moduleDao.get(Module.class, code);
				  String url = m.getUrl();
				  list.add(url);
				}
				return list;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询异常："+e.getMessage());
		}
		}
	
	}



