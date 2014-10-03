package com.uk.blog.mysql.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import com.uk.blog.mysql.Articles;
import com.uk.blog.mysql.Bookshelf;
import com.uk.blog.mysql.Categories;
import com.uk.database.DBUtils;
import com.uk.database.mysql.impl.BasicMySqlCRUD;

public class BookshelfDao extends BasicMySqlCRUD<Integer,Bookshelf> {
	
	// idArticles, idCategories;
	private CategoriesDao cdao;
	private ArticlesDao adao;
	
	public BookshelfDao(){
		try {
			setClazz(Class.forName("com.uk.blog.mysql.Bookshelf"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public BookshelfDao(DBUtils<Session> dao){
		this();
		this.setDao(dao);
	}
	
	@Override
	public void setDao(DBUtils<Session> dao) {
		super.setDao(dao);
		cdao = new CategoriesDao(this.getDao());
		adao = new ArticlesDao(this.getDao());
	}	

	@SuppressWarnings("unchecked")
	public Bookshelf get(Articles article, Categories category){
		List<Bookshelf> list = getDao().getSession()
		 			   .createQuery("FROM Bookshelf WHERE idArticles=:ida and idCategories=:idc")
		 			   .setParameter("ida", article.getId())
		 			   .setParameter("idc", category.getId())
		 			   .list();
		if(!list.isEmpty()){ return list.get(0);}
		return null;
	}
	
	public List<? extends Categories> locate(Articles article){
		List<Categories> list =  new ArrayList<Categories>();
		List<Bookshelf> shelf = findByField("idArticles",article.getId());
		if(shelf!=null && !shelf.isEmpty()){
			for (Bookshelf tome : shelf) {
				list.add(cdao.findById(tome.getIdCategories()));
			}
		}
		return list;
	};

	public List<? extends Articles> review(Categories category){
		List<Articles> list =  new ArrayList<Articles>();
		List<Bookshelf> shelf = findByField("idCategories",category.getId());
		if(shelf!=null && !shelf.isEmpty()){
			for (Bookshelf tome : shelf) {
				list.add(adao.findById(tome.getIdArticles()));
			}
		}
		return list;
	};
	
	public BookshelfDao store(Articles article, Categories category){
		this.save(new Bookshelf(article.getId(),category.getId()));
		return this;
	};
	
	public BookshelfDao store(List<Articles> articles, Categories category){
		try {
			Session s = this.getDao().getSession();
			Transaction tx = s.getTransaction();
			tx.begin();
			for (Articles article : articles) {
				this.persist(new Bookshelf(article.getId(),category.getId()));
			}
			tx.commit();
			s.flush();
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return this;
	};
	
	public BookshelfDao store(Articles article, List<Categories> categories){
		try {
			Session s = this.getDao().getSession();
			Transaction tx = s.getTransaction();
			tx.begin();
			for (Categories category : categories) {
				this.persist(new Bookshelf(article.getId(),category.getId()));
			}
			tx.commit();
			s.flush();
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return this;
	};
	
	@Transactional
	public BookshelfDao remove(Articles article, Categories category){
		Bookshelf b = this.get(article,category);
		Session s = this.getDao().getSession();
				Transaction tx = s.getTransaction();
				tx.begin();
					s.delete(b);
				tx.commit();
			s.flush();
		return this;
	};
	
	public BookshelfDao remove(List<Articles> articles, Categories category){
		Session s = this.getDao().getSession();
		List<Bookshelf> list = new ArrayList<Bookshelf>();
		for (Articles article : articles) {
			list.add(this.get(article,category));
		}
		try {
			Transaction tx = s.getTransaction();
			tx.begin();
			for (Bookshelf b : list) {
				s.delete(b);
			}
			tx.commit();
			s.flush();
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return this;
	};
	
	public BookshelfDao remove(Articles article, List<Categories> categories){
		Session s = this.getDao().getSession();
		try {
			Transaction tx = s.getTransaction();
			tx.begin();
			for (Categories category : categories) {
				this.delete(new Bookshelf(article.getId(),category.getId()));
			}
			tx.commit();
			s.flush();
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return this;
	};
	
	public Boolean isEmpty(Categories category){
		return this.review(category).isEmpty();
	};

	public Boolean exist(Articles article, Categories category){
		return this.get(article, category)!=null;
	}
}
