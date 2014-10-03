package com.uk.login.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import com.uk.database.DBUtils;

public interface LoginDetailsDao {
	
	public abstract DBUtils<Session> getDao();

	public abstract void setDao(DBUtils<Session> dao);
	
	public abstract List<?> getAllRoles();
	
	public abstract String getLandingByRole(String role);
	
	public abstract String getLandingByRoles(Collection<? extends String> roles);
	
	public abstract LoginDetailsDao setLandingByRole(String role, String Landing, int priority);
}
