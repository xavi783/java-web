package com.uk.shop.mysql.dao;

import org.hibernate.Session;

import com.uk.database.DBUtils;
import com.uk.database.mysql.impl.BasicMySqlCRUD;
import com.uk.shop.mysql.Product;

public class ProductDao extends BasicMySqlCRUD<Integer, Product> {
		
	public ProductDao(){
		try {
			setClazz(Class.forName("com.uk.shop.mysql.Product"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ProductDao(DBUtils<Session> dao){
		this();
		this.setDao(dao);
	}
	
}
