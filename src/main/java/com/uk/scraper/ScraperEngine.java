package com.uk.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.uk.scraper.strategies.Query;

public abstract class ScraperEngine {
	
	@SuppressWarnings({ "unchecked" })
	public static <T extends Document, U extends String> Map<String,T> getDocuments(Map<String, U> urlMap){
		Map<String,T> responseMap = new HashMap<String,T>(); 
		try {
			for(Entry<String, U> e : urlMap.entrySet()){
				responseMap.put(e.getKey(),(T)Jsoup.connect(e.getValue().toString()).get());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseMap;
	}
	
	@SuppressWarnings({ "unchecked", "hiding" })
	public static <T extends List<?>, U extends String, Document> Map<String,T> getHref(Map<String,U> urlMap, String query){
		List<String> href = new ArrayList<String>();
		Map<String,List<String>> linkMap = new HashMap<String,List<String>>();
		
		Class<?> c = urlMap.entrySet().iterator().next().getValue().getClass();		
		Set<Entry<String,Document>> target = (Set<Entry<String, Document>>)(c.equals(String.class) ? ScraperEngine.getDocuments(urlMap).entrySet() : urlMap.entrySet());
		
		for(Entry<String, Document> doc : target){
			Elements el = ((Element)doc.getValue()).select(query);
			for(Element e : el){
				href.add(e.attr("href"));
			};
			linkMap.put(doc.getKey(), href);
			href = new ArrayList<String>();
		};
		return (Map<String, T>)linkMap;
	}

	public static <U,T> T performQuery(U subject, Query<U,T> query){		
		return query.perform(subject);	
	}
}
