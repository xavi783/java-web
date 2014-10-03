package scraper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.DB;
import org.hibernate.Session;
import com.uk.database.DBUtils;
import com.uk.scraper.strategies.Query;
import com.uk.scraper.strategies.queries.IndexShopQuery;
import com.uk.scraper.strategies.queries.ShopArticleQuery;
import com.uk.shop.mongo.ProductMongo;
import com.uk.shop.mongo.dao.ProductMongoDao;

import general.BaseTest;

public class ScraperTest extends BaseTest {

	@Autowired private DBUtils<DB> mongodao;
	@Autowired private DBUtils<Session> dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@Ignore
	public void test1(){
		ShopArticleQuery q = new ShopArticleQuery();
		ProductMongo p = q.perform("http://www.packtpub.com/openlayers-3-beginners-guide/book");
		
		System.out.println(p.getSource());
		System.out.println(((Map<String,String[]>)p.getField("overview")).get("content")[0]);
		System.out.println(((Map<String,String[]>)p.getField("overview")).get("content")[1]);
		System.out.println(((Map<String,String>)p.getField("detail")).get("content"));
	}
	
	@Test
	@Ignore
	public void fillMongoDBProduct(){
		System.out.println(mongodao);
		ProductMongoDao productMongoDao = new ProductMongoDao(mongodao);
		ProductMongo product;
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(2, "http://www.packtpub.com/search?keys=Web-Development");
		map.put(3, "http://www.packtpub.com/search?keys=Javascript");
		map.put(4, "http://www.packtpub.com/search?keys=HTML");
		map.put(5, "http://www.packtpub.com/search?keys=CSS");
		map.put(6, "http://www.packtpub.com/search?keys=Big-Data");
		map.put(7, "http://www.packtpub.com/search?keys=Mobile");
		map.put(9, "http://www.packtpub.com/search?keys=Java");
		map.put(10, "http://www.packtpub.com/search?keys=Spring");
		map.put(11, "http://www.packtpub.com/search?keys=Hibernate");
		IndexShopQuery q = new IndexShopQuery(map);

		for (Entry<Integer,List<Query<String, ProductMongo>>> e : q.getResult().entrySet()) {
			for (Query<String,ProductMongo> query : e.getValue()) {
				try{					
					product = query.perform();
					product.map();
					if(!productMongoDao.exist(product)){ productMongoDao.save(product); }
				}catch(RuntimeException ex){
					System.out.println("NOT FOUND - "+query.getSubject());
					continue;
				}
			}
		}		
	}

	@Test
	@Ignore
	public void copyMongo2SQL(){
		/*
		System.out.println(mongodao);
		ProductMongoDao productMongoDao = new ProductMongoDao(mongodao);
		ProductDao productDao = new ProductDao(dao);
		ProductMongo product;
		Product product;

		List<ProductMongo> list = productMongoDao.findAll();

		for (ProductMongo p : list) {
			Product psql = new Product(0, p.get_id(), p.getSource(), false);
			if(productDao.findByField("name",psql.getName())==null){
				productDao.save(psql);
			}			
		}		
		*/
	}
	
	
}
