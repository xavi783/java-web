package com.uk.database.mysql.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.uk.database.DBUtils;
import com.uk.database.mysql.Identificable;
import com.uk.database.mysql.MySqlCRUD;

@Repository
public class BasicMySqlCRUD<U,T extends Identificable<U>> implements MySqlCRUD<U,T> {

	@Autowired private DBUtils<Session> dao;
	private Class<T> clazz;

	public BasicMySqlCRUD() {
	}

	public BasicMySqlCRUD(DBUtils<Session> dao, Class<T> clazz) {
		this.dao = dao;
		this.clazz = clazz;
	}

	@Override
	public DBUtils<Session> getDao() {
		return dao;
	}

	@Override
	public void setDao(DBUtils<Session> dao) {
		this.dao = dao;
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		T obj = null;
		List<T> list = null;
		try {
			obj = this.clazz.newInstance();
			list = dao.getSession()
		 			   .createQuery("FROM " + obj.getClass().getName())
		 			   .list();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if(list!=null && !list.isEmpty()){
			return list;
		}
		return null;
	};
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public T findById(U id) {
		T obj;
		try {
			obj = this.clazz.newInstance();
			List<T> list = dao.getSession()
		 			   .createQuery("FROM " + obj.getClass().getName() + " WHERE id=:id")
		 			   .setParameter("id", id)
		 			   .list();
			if(!list.isEmpty()){ return list.get(0); }
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked" })
	public <V> List<T> findByField(String fieldname, V field){
		T obj;
		try {
			obj = this.clazz.newInstance();
			List<T> list = dao.getSession().createQuery("FROM " + obj.getClass().getName() + " WHERE " + fieldname + "=:field")
							.setParameter("field", field)
							.list();
			if(!list.isEmpty()){return list;}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	};

	@Override
	public MySqlCRUD<U,T> save(T object) {
		Session s = this.getDao().getSession();
		Transaction tx = s.getTransaction();
		tx.begin();
		s.save(object);
		tx.commit();
		s.flush();
		return this;
	}
	
	@Override
	public MySqlCRUD<U,T> persist(T object) {
		Session s = this.getDao().getSession();
		Transaction tx = s.getTransaction();
		tx.begin();
		s.persist(object);
		tx.commit();
		s.flush();
		return this;
	}

	@Override
	public MySqlCRUD<U,T> update(T object) {
		Session s = this.getDao().getSession();
		Transaction tx = s.getTransaction();
		tx.begin();
		s.update(object);
		tx.commit();
		s.flush();
		return this;
	}

	@Override
	public MySqlCRUD<U,T> delete(T object) {
		Session s = this.getDao().getSession();
		Transaction tx = s.getTransaction();
		tx.begin();
		s.delete(object);
		tx.commit();
		s.flush();
		return this;
	}

	@Override
	public Boolean exist(T object) {
		return object.getId()!=null && findById(object.getId())!=null;
	}

	protected Class<T> getClazz() {
		return clazz;
	}

	@SuppressWarnings("unchecked")
	protected void setClazz(Class<?> clazz) {
		this.clazz = (Class<T>)clazz;
	}
}
