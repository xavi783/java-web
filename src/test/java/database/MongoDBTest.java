package database;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.uk.database.DBUtils;
import com.uk.database.mongo.MongoCRUD;
import com.uk.database.mongo.impl.BasicMongoCRUD;
import com.uk.shop.mongo.ProductMongo;
import com.uk.shop.mongo.dao.ProductMongoDao;

import general.BaseTest;

public class MongoDBTest extends BaseTest {
	
	@Autowired private DBUtils<DB> mongodao;
	@Autowired private BasicMongoCRUD<ProductMongo> productMongoDao;
	
	@Test
	@Ignore
	public void connect(){
		System.out.println(mongodao.getSession().getCollectionNames());
	}
	
	@Test
	@Ignore
	public void map(){
		ProductMongo a = new ProductMongo();
		a.setField("offer", "discount", "descuentillo");
		System.out.println(a.map().toString());
	}
	
	@Test
	@Ignore
	public void get(){
		//ProductMongoDao adao = new ProductMongoDao(mongodao);
		//ProductMongo a = adao.searchById("openlayers-3-beginners-guide");
		productMongoDao.findAll();
		System.out.println(productMongoDao.findAll().get(0));
	}
	
	@Test
	@Ignore
	public void get2(){
		List<ProductMongo> a = productMongoDao.search(new BasicDBObject("category_id",2));
		System.out.println(a);
	}

}
