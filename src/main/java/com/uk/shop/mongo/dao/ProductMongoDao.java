package com.uk.shop.mongo.dao;

import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.uk.database.DBUtils;
import com.uk.database.mongo.impl.BasicMongoCRUD;
import com.uk.shop.mongo.ProductMongo;

@Repository
public class ProductMongoDao extends BasicMongoCRUD<ProductMongo> {
	
	public ProductMongoDao(){
		try {
			setClazz(Class.forName("com.uk.shop.mongo.ProductMongo"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ProductMongoDao(DBUtils<DB> mongodao){
		super.setMongodao(mongodao);
	}
	
}
