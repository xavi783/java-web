package com.uk.scraper.strategies.dao;

import com.mongodb.DB;
import com.uk.database.DBUtils;
import com.uk.database.mongo.impl.BasicMongoCRUD;
import com.uk.scraper.strategies.ScraperStrategy;

public class ScraperStrategyDao extends BasicMongoCRUD<ScraperStrategy> {

	private String collection = "scrapping";
	
	public ScraperStrategyDao(){
		super();
		super.setDbCollection(super.getMongodao().getSession().getCollection(collection));
	}

	public ScraperStrategyDao(DBUtils<DB> mongodao){
		super(mongodao);
		super.setDbCollection(super.getMongodao().getSession().getCollection(collection));
	}
	
}
