package com.uk.database.mongo;

import java.util.Map;

import com.mongodb.DBObject;

public interface MongoMapper {

	MongoMapper map();
	
	MongoMapper mapFrom(Map<String,Object> object);
	
	<T extends DBObject> MongoMapper convert(T object);
	
	<T extends DBObject> T getMongoObject();
	
	<T extends DBObject> MongoMapper setMongoObject(T mongoObject);
	
}
