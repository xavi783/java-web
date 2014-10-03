package com.uk.database.mysql;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.uk.database.DBUtils;

public interface MySqlCRUD<U,T extends Identificable<U>> {

	public abstract DBUtils<Session> getDao();

	public abstract void setDao(DBUtils<Session> dao);
	
	public abstract List<T> findAll();
	
	public abstract T findById(U id);
	
	public abstract <V> List<T> findByField(String fieldname, V field);

	public abstract MySqlCRUD<U,T> save(T object) throws ConstraintViolationException;
	
	public MySqlCRUD<U,T> persist(T object) throws ConstraintViolationException;

	public abstract MySqlCRUD<U,T> update(T object);

	public abstract MySqlCRUD<U,T> delete(T object);
	
	public abstract Boolean exist(T object);
	
}
