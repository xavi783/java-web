package com.uk.blog.mysql.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.uk.blog.mysql.Articles;
import com.uk.database.DBUtils;
import com.uk.database.mysql.impl.BasicMySqlCRUD;

@Repository
public class ArticlesDao extends BasicMySqlCRUD<Integer,Articles>{
	
	public ArticlesDao(){
		try {
			setClazz(Class.forName("com.uk.blog.mysql.Articles"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArticlesDao(DBUtils<Session> dao){
		this.setDao(dao);
		try {
			setClazz(Class.forName("com.uk.blog.mysql.Articles"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Articles> T findByTitle(String title){
		List<Articles> list = super.findByField("title", title);
		if (list!=null && !list.isEmpty()) {
			return (T)list.get(0);
		} else {
			return null;
		}
	};
	
	@SuppressWarnings("unchecked")
	public <T extends Articles> List<T> findByAuthor(String author){
		List<Articles> list = super.findByField("author", author);
		if (list!=null && !list.isEmpty()) {
			return (List<T>)list;
		} else {
			return null;
		}
	};

	@Override
	public Boolean exist(Articles object) {
		return this.findByTitle(object.getTitle())!=null || super.exist(object);
	}

}
