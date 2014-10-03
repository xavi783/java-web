package com.uk.login.dao;

import java.util.List;

import org.hibernate.Session;

import com.uk.database.DBUtils;
import com.uk.login.Authorities;
import com.uk.login.User;

public interface AuthoritiesDao {

	public abstract DBUtils<Session> getDao();

	public abstract void setDao(DBUtils<Session> dao);

	public abstract List<?> getAuthoritiesByUsername(User user);

	public abstract List<?> getAuthoritiesByRole(String role);

	public abstract void save(Authorities authority);

	public abstract void update(Authorities authority);

	public abstract void delete(Authorities authority);

}