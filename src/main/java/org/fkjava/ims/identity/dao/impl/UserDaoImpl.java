package org.fkjava.ims.identity.dao.impl;



import java.util.List;

import org.fkjava.ims.common.dao.impl.BaseDaoImpl;
import org.fkjava.ims.identity.bean.User;
import org.fkjava.ims.identity.dao.UserDao;
import org.hibernate.Session;
import org.junit.Test;
import org.springframework.stereotype.Service;
@Service
public class UserDaoImpl  extends BaseDaoImpl implements UserDao {

	@Override
	public List<User> findBySelectUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Test
	public void testOnly() {
	
	}

	@Override
	public void deleteUserByLogic(String[] strings) {
	StringBuffer hql = new StringBuffer();
	hql.append("update User set delFlag = 0 where   ");
		for (int i = 0; i < strings.length; i++) {
			hql.append(i==0 ?"userId= '"+strings[i]+"'":" or userId = '"+strings[i]+"'");
		}
		this.bulkUpdate(hql.toString(),null);
	}

	
	}


