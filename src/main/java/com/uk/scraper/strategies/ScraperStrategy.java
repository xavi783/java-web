package com.uk.scraper.strategies;

import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.uk.database.mongo.MongoMapper;
import com.uk.database.mongo.MongoSettler;

public class ScraperStrategy extends MongoSettler implements MongoMapper{

	private DBObject mongoObject;
	
	private String _id, query;
	private Map<String,String> subject;
		
	public ScraperStrategy() {
		mongoObject = new BasicDBObject();
	}

	public ScraperStrategy(BasicDBObject mongoObject, String _id, String query,
			Map<String, String> subject) {
		this.mongoObject = mongoObject;
		this._id = _id;
		this.query = query;
		this.subject = subject;
	}

	@Override
	public MongoMapper map(){
		((BasicDBObject)mongoObject)
			.append("_id",this._id)
			.append("query",this.query)
			.append("subject",this.subject);
		return this;		
	}
	
	@Override
	public MongoMapper mapFrom(Map<String,Object> object){
		mongoObject.putAll(object);
		return this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MongoMapper convert(DBObject source){
		this._id = (String) source.get("_id");
		this.query = (String) source.get("query");
		this.subject = (Map<String,String>)source.get("subject");
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DBObject> T getMongoObject() {
		return (T)mongoObject;
	}

	@Override
	public <T extends DBObject> ScraperStrategy setMongoObject(T mongoObject) {
		this.mongoObject = mongoObject;
		return this;
	}

	public String get_id() {
		return _id;
	}

	public ScraperStrategy set_id(String _id) {
		this._id = _id;
		return this;
	}

	public String getQuery() {
		return query;
	}

	public ScraperStrategy setQuery(String query) {
		this.query = query;
		return this;
	}

	public Map<String, String> getSubject() {
		return subject;
	}

	public ScraperStrategy setSubject(Map<String, String> subject) {
		this.subject = subject;
		return this;
	}
	
}
