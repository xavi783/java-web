package com.uk.database.mongo;

import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.uk.database.DBUtils;

public interface MongoCRUD<T> {
	
	public DBUtils<DB> getMongodao();

	public void setMongodao(DBUtils<DB> mongodao);
	
	public DBCollection getDbCollection();
	
	public void setDbCollection(DBCollection dbCollection);
	
	public String getCollectionName();
	
	public void setCollectionName(String collectionName);
	
	public Class<T> getClazz();

	public void setClazz(Class<?> clazz);
		
	public MongoCRUD<T> save(T object); //POST
	
	public MongoCRUD<T> fetch(T object); //GET

	public List<T> findAll(); //OPTIONS	

	public <U extends DBObject> List<T> search(U pattern); //OPTIONS	
	
	public T searchById(String _id); //GET{id}
	
	public Boolean exist(String _id);

	public Boolean exist(T object);
	
}
