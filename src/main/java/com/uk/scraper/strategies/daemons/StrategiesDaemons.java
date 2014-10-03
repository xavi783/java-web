package com.uk.scraper.strategies.daemons;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.uk.scraper.ScraperEngine;
import com.uk.scraper.strategies.Query;
import com.uk.scraper.strategies.ScraperStrategy;
import com.uk.scraper.strategies.dao.ScraperStrategyDao;

public class StrategiesDaemons {
	
	private ScraperStrategyDao dao;
	
	public <U,T> StrategiesDaemons multiQuery(String strategy_id, Query<U,T> query){
		ScraperStrategy strategy = dao.searchById(strategy_id);
		
		Map<String,List<U>> links = ScraperEngine.getHref(strategy.getSubject(), strategy.getQuery());
		
		for(Entry<String, List<U>> e : links.entrySet()){
			for (U link : e.getValue()) {
				ScraperEngine.performQuery(link, query);
			}
		}
		return this;
	}

}
