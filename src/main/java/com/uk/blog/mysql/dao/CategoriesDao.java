package com.uk.blog.mysql.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.uk.blog.mysql.Categories;
import com.uk.database.DBUtils;
import com.uk.database.mysql.impl.BasicMySqlCRUD;

@Repository
public class CategoriesDao extends BasicMySqlCRUD<Integer,Categories>{	

	public CategoriesDao(){
		try {
			setClazz(Class.forName("com.uk.blog.mysql.Categories"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public CategoriesDao(DBUtils<Session> dao){
		this();
		this.setDao(dao);
	}

	@SuppressWarnings("unchecked")
	public <T extends Categories> T findByName(String name){
		return (T)findByField("label", name);	
	};
}
