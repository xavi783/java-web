package com.uk.database.mongo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.uk.database.DBUtils;
import com.uk.database.mongo.MongoCRUD;
import com.uk.database.mongo.MongoMapper;

@Service
public class BasicMongoCRUD<T extends MongoMapper> implements MongoCRUD<T> {
	
	private Class<T> clazz;
	private String collectionName;
		
	@Autowired private DBUtils<DB> mongodao;
	private DBCollection dbCollection;
	
	public BasicMongoCRUD(){
	}

	public BasicMongoCRUD(DBUtils<DB> mongodao){
		this.mongodao = mongodao;
	}
	
	@Override
	public BasicMongoCRUD<T> save(T object) {
		this.dbCollection.save(object.getMongoObject());
		return this;
	}

	@Override
	public BasicMongoCRUD<T> fetch(T object) {
		object.convert(this.dbCollection.find(new BasicDBObject("_id",object.getMongoObject().get("_id"))).next());
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(){
		T object;
		List<DBObject> list = this.dbCollection.find().toArray();
		if(list.isEmpty()){ 
			return null;
		}else{
			List<T> aList = new ArrayList<T>();
			for(DBObject l : list){
				try {
					object = this.clazz.newInstance();
					aList.add((T) object.convert(l)); //mapear error al convertir
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return aList;
		}
	}; //OPTIONS	

	@Override
	@SuppressWarnings("unchecked")
	public <U extends DBObject> List<T> search(U pattern) {
		T object;
		List<U> list = (List<U>)this.dbCollection.find(pattern).toArray();
		if(list.isEmpty()){ 
			return null;
		}else{
			List<T> aList = new ArrayList<T>();
			for(U l : list){
				try {
					object = this.clazz.newInstance();
					aList.add((T) object.convert(l));
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return aList;
		}		
	}
	
	@Override
	public T searchById(String _id){
		T object = null;
		try {
			object = this.clazz.newInstance();
			DBCursor cursor = this.dbCollection.find(new BasicDBObject("_id", _id));
			if(!cursor.hasNext()) return null;
			object.convert(cursor.next());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}		
		return object;
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
		return this.dbCollection.find(object.getMongoObject()).count()>0;
	};
	
}
