package com.uk.scraper.strategies.queries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.uk.scraper.strategies.Query;
import com.uk.shop.mongo.ProductMongo;

public class IndexShopQuery implements Query<Map<Integer,String>,Map<Integer,List<Query<String,ProductMongo>>>> {
	
	private Map<Integer,String> subject;
	private Map<Integer,List<Query<String, ProductMongo>>> map;
	private List<Query<String, ProductMongo>> list;

	public IndexShopQuery() {
	}

	public IndexShopQuery(Map<Integer, String> subject) {
		this.subject = subject;
	}

	@Override
	public Map<Integer,List<Query<String, ProductMongo>>> perform() {
		this.perform(this.subject);
		return null;
	}

	@Override
	public Map<Integer,List<Query<String, ProductMongo>>> perform(Map<Integer,String> subject) {
		this.subject = subject;
		this.build();
		for (Entry<Integer, String> e : subject.entrySet()) {
			try {
				Elements el = Jsoup.connect(e.getValue()).get().select(".solr-search-result a");
				for (Element element : el) {
					this.list.add(new ShopArticleQuery(element.attr("href"),e.getKey()));
				}
				this.map.put(e.getKey(), list);
				this.list = new ArrayList<Query<String, ProductMongo>>();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return this.map;
	}

	@Override
	public Map<Integer,String> getSubject() {
		return subject;
	}

	@Override
	public Query<Map<Integer, String>, Map<Integer,List<Query<String, ProductMongo>>>> setSubject(
			Map<Integer, String> subject) {
		this.subject = subject;
		return this;
	}
	
	public Map<Integer,List<Query<String, ProductMongo>>> getResult(){
		return this.map;
	}
	
	@Override
	public Map<Integer,List<Query<String, ProductMongo>>> build() {
		this.map = new HashMap<Integer,List<Query<String, ProductMongo>>>();
		this.list = new ArrayList<Query<String, ProductMongo>>();
		return this.map;
	}

	@Override
	public String toString() {
		return "IndexShopQuery [subject=" + subject + ", list=" + list + "]";
	}
	
}
