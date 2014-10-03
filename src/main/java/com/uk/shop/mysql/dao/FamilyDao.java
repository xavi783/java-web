package com.uk.shop.mysql.dao;

import org.hibernate.Session;

import com.uk.database.DBUtils;
import com.uk.database.mysql.impl.BasicMySqlCRUD;
import com.uk.shop.mysql.Family;

public class FamilyDao extends BasicMySqlCRUD<Integer, Family> {

	public FamilyDao(){
		try {
			setClazz(Class.forName("com.uk.shop.mysql.Family"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public FamilyDao(DBUtils<Session> dao){
		this();
		this.setDao(dao);
	}
	
}
