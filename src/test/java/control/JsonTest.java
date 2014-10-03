package control;

import static org.junit.Assert.assertEquals;
import general.BaseTest;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.mongodb.DB;
import com.uk.control.SectionController;
import com.uk.control.cookies.CookiesNotify;
import com.uk.database.DBUtils;
import com.uk.shop.mongo.ProductMongo;
import com.uk.shop.mongo.dao.ProductMongoDao;
import com.uk.thymeleaf.templating.JsonFileTemplate;
import com.uk.thymeleaf.templating.MongoDBTemplate;

public class JsonTest extends BaseTest{
	
	@Value("${home.template}") String homeTemplate;
	@Autowired private JsonFileTemplate jsonTemplate;
	@Autowired private MongoDBTemplate mongoTemplate;
	@Autowired private DBUtils<DB> mongodao;
	
	@Test
	@Ignore
	public void mappingTest() {
		jsonTemplate.setFilename(homeTemplate);
		System.out.println(jsonTemplate.get("title"));
	}
	
	@Test
	@Ignore
	public void mappingTest2() {
		//mongoTemplate.setTemplateId("/homeES");
		CookiesNotify cookiesAccepted = new CookiesNotify(mongoTemplate.setTemplateId("cookiesAccepted"+"en".toUpperCase()));
		System.out.println(cookiesAccepted);
	}	
	
	@Test
	@Ignore
	public void test1(){
		mongoTemplate.setTemplateId("/homeES");
		((ObjectNode) mongoTemplate.getRootNode()).set("title", new TextNode("success"));
		System.out.println(mongoTemplate.get("title"));
		assertEquals(mongoTemplate.get("title"),"success");
		
	}
	
	@Test
	@Ignore
	public void test2(){
		MongoDBTemplate footerTemplate = new MongoDBTemplate(mongodao);
		footerTemplate.setCollection("templates");
		footerTemplate.setTemplateId("footerEN");
	}
	
	@Test
	@Ignore
	public void test3(){
		SectionController sc = new SectionController();
		System.out.println(sc.getFooterTemplate().get("footer[0]"));
	}
	
	@Test
	@Ignore
	public void test4(){
		MongoDBTemplate articleTemplate = new MongoDBTemplate(mongodao);
		articleTemplate.setCollection("articles");
		ProductMongoDao adao = new ProductMongoDao(mongodao);
		ProductMongo a = adao.searchById("openlayers-3-beginners-guide");
		articleTemplate.setTemplateId(a.get_id());
		System.out.println(articleTemplate.get("title"));
	}
}
