package com.uk.login.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.uk.database.DBUtils;
import com.uk.login.User;
import com.uk.login.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {
		
	@Autowired private DBUtils<Session> dao;	
	
	@Override
	public DBUtils<Session> getDao() {
		return dao;
	}

	@Override
	public void setDao(DBUtils<Session> dao) {
		this.dao = dao;
	}
	
	@Override
	@Transactional
	public User findUserByUsername(String username) {
		User user = null;
		if(username!=null && username!=""){
	        List<?> list = dao.getSession()
	        			   .createQuery("FROM User WHERE username=:username")
	        			   .setParameter("username", username)
	        			   .list();
	        if(!list.isEmpty()){user = (User)list.get(0);}
		}
		return user;
    }
	
	@Override
	@Transactional
	public String findPasswordByUsername(String username) {
		String password = null;
		if(username!=null && username!=""){
	        List<?> list = dao.getSession()
	        			   .createQuery("SELECT password FROM User WHERE username=:username")
	        			   .setParameter("username", username)
	        			   .list();
	        if(!list.isEmpty()){password = (String)list.get(0);}
		}
		return password;
    }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> getAllUsers() {
		List<User> allUsers = null;
        List<?> list = dao.getSession()
        			   .createQuery("FROM User")
        			   .list();
        if(!list.isEmpty()){allUsers = (List<User>)list;}
		return allUsers;
    }

	@Override
	@Transactional
	public void save(User user){
		dao.getSession().save(user);
	}
 
	@Override
	@Transactional
	public void update(User user){
		dao.getSession().update(user);
	}
	
	@Override
	@Transactional
	public void delete(User user){
		dao.getSession().delete(user);
	}

	@Override
	public String toString() {
		return "UserDaoImpl: {dao: " + dao + "}";
	}
}