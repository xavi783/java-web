package com.uk.login.dao;

import java.util.List;

import org.hibernate.Session;

import com.uk.database.DBUtils;
import com.uk.login.User;

public interface UserDao {

	public abstract DBUtils<Session> getDao();

	public abstract void setDao(DBUtils<Session> dao);

	public abstract User findUserByUsername(String username);
	
	public abstract String findPasswordByUsername(String username);
	
	public abstract List<User> getAllUsers();

	public abstract void save(User user);

	public abstract void update(User user);

	public abstract void delete(User user);

}