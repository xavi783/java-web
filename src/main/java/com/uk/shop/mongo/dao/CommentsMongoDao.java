package com.uk.shop.mongo.dao;

import com.mongodb.DB;
import com.uk.database.DBUtils;
import com.uk.database.mongo.impl.BasicMongoCRUD;
import com.uk.shop.mongo.CommentsMongo;

public class CommentsMongoDao extends BasicMongoCRUD<CommentsMongo> {
	
	public CommentsMongoDao(){
		try {
			setClazz(Class.forName("com.uk.shop.mongo.CommentsMongo"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public CommentsMongoDao(DBUtils<DB> mongodao){
		super.setMongodao(mongodao);
	}

}
