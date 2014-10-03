package com.uk.database.mongo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.uk.database.DBUtils;
import com.uk.database.mongo.MongoCRUD;

@Repository
public class PlainMongoCRUD<T extends DBObject> implements MongoCRUD<T> {
	
	private Class<T> clazz;
	private String collectionName;
		
	@Autowired private DBUtils<DB> mongodao;
	private DBCollection dbCollection;
	
	public PlainMongoCRUD(){
	}

	public PlainMongoCRUD(DBUtils<DB> mongodao){
		this.mongodao = mongodao;
	}
	
	@Override
	public PlainMongoCRUD<T> save(T object) {
		this.dbCollection.save(object);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PlainMongoCRUD<T> fetch(T object) {
		object = (T)this.dbCollection.find(object).next();
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(){
		DBCursor cursor = this.dbCollection.find();
		if(!cursor.hasNext()) return null;
		return (List<T>) cursor.toArray();
	}; //OPTIONS	

	@Override
	@SuppressWarnings("unchecked")
	public <U extends DBObject> List<T> search(U pattern) {
		DBCursor cursor = this.dbCollection.find(pattern);
		if(!cursor.hasNext()) return null;
		return (List<T>)cursor.toArray();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T searchById(String _id){
		DBCursor cursor = this.dbCollection.find(new BasicDBObject("_id", _id));
		if(!cursor.hasNext()) return null;
		return (T)cursor.next();
	}
	
	@Override
	public DBUtils<DB> getMongodao() {
		return mongodao;
	}
	
	@Override
	public void setMongodao(DBUtils<DB> mongodao) {
		this.mongodao = mongodao;
	}

	@Override
	public DBCollection getDbCollection() {
		return dbCollection;
	}

	@Override
	public void setDbCollection(DBCollection dbCollection) {
		this.dbCollection = dbCollection;
	}
	
	@Override
	public String getCollectionName(){
		return this.collectionName;
	};
	
	@Override
	public void setCollectionName(String collectionName){
		this.collectionName = collectionName;
		this.setDbCollection(mongodao.getSession().getCollection(collectionName));
	};
	
	@Override
	public Class<T> getClazz() {
		return clazz;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setClazz(Class<?> clazz) {
		this.clazz = (Class<T>)clazz;
	}
	
	public Boolean exist(String _id){
		return this.dbCollection.find(new BasicDBObject("_id", _id)).count()>0;
	};

	public Boolean exist(T object){
		return this.dbCollection.find(object).count()>0;
	};

}
