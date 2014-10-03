package com.uk.thymeleaf.templating;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.uk.database.DBUtils;

public class MongoDBTemplate extends JsonTemplate<String> implements Cloneable{
	
	@Autowired private DBUtils<DB> mongodao;
	private String collection;
	private String templateId;
	private DBCollection dbc;
	
	public MongoDBTemplate() {
	}

	public MongoDBTemplate(DBUtils<DB> mongodao) {
		this.mongodao = mongodao;
	}

	public MongoDBTemplate(DBUtils<DB> mongodao, String collection) {
		this.mongodao = mongodao;
		this.setCollection(collection);
	}

	public DBUtils<DB> getMongodao() {
		return mongodao;
	}

	public void setMongodao(DBUtils<DB> mongodao) {
		this.mongodao = mongodao;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
		this.dbc = this.mongodao.getSession().getCollection(this.collection);
	}

	public String getTemplateId() {
		return templateId;
	}

	public MongoDBTemplate setTemplateId(String templateId) {
		this.templateId = templateId;
		super.map(this.getTemplate(this.templateId));
		return this;
	}
	
	public DBCollection getDbc() {
		return dbc;
	}

	public void setDbc(DBCollection dbc) {
		this.dbc = dbc;
	}

	public String getTemplate(String template){
		return this.dbc.find(new BasicDBObject("_id", template)).next().toString();
	}
	
}
