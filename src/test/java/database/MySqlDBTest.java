package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.uk.database.DBUtils;
import com.uk.database.mysql.MySqlCRUD;
import com.uk.blog.mysql.Articles;
import com.uk.blog.mysql.Categories;
import com.uk.blog.mysql.dao.ArticlesDao;
import com.uk.blog.mysql.dao.BookshelfDao;
import com.uk.blog.mysql.dao.CategoriesDao;
import com.uk.shop.mysql.Family;
import com.uk.shop.mysql.Product;
import com.uk.shop.mysql.dao.ProductDao;

import general.BaseTest;

public class MySqlDBTest extends BaseTest {

	@Autowired private DBUtils<Session> dao;
	private BookshelfDao bscrud;
	private ArticlesDao acrud;
	private CategoriesDao ccrud;
	@Autowired private MySqlCRUD<Integer,Product> pcrud;
	@Autowired private MySqlCRUD<Integer,Family> familiesDao;
	
	private Articles a1, a2;
	private List<Articles> list;
	private Categories c;

	@Before
	public void setUp(){
		if (bscrud==null) { bscrud = new BookshelfDao(dao); }
		if (acrud==null) {  acrud  = new ArticlesDao(dao); }
		if (ccrud==null) {  ccrud  = new CategoriesDao(dao); }
		
		
		/*
		a1 = new Articles("Title 1", "1", "me", "1", new Date());
		System.out.println(acrud.exist(a1));
		if(!acrud.exist(a1)){ acrud.save(a1); }	
		a1 = acrud.findByTitle(a1.getTitle());
		
		a2 = new Articles("Title 2", "2", "me", "1", new Date());
		if(!acrud.exist(a2)){ acrud.save(a2); }			
		a2 = acrud.findByTitle(a2.getTitle());
		
		if (list==null || list.isEmpty()) {
			Articles[] a = {a1,a2};
			list = Arrays.asList(a);
		}
		if (c==null) { c = ccrud.findById(1); }
		if (!bscrud.exist(a1,c)) { bscrud.store(a1,c); }
		if (!bscrud.exist(a2,c)) { bscrud.store(a2,c); }
		*/
	}
	
	@Test
	@Ignore
	public void test1(){
		//pcrud.save(new Product(0, "New Product", null, false));
		System.out.println("\n TEST-1: " + pcrud.findByField("name","New Product") + "\n");
		System.out.println("\n TEST-1: " + familiesDao.findByField("caption","All Books") + "\n");
	}
	
	@Test
	@Ignore
	public void test2(){
		System.out.println("\n TEST-2: " +  bscrud.locate(acrud.findById(1)) + "\n");
	}
	
	@Test
	@Ignore
	public void test3(){
		System.out.println("\n TEST-3: " +   bscrud.review(ccrud.findById(1))  + "\n");
	}
	
	@Test
	@Ignore
	public void test4(){
		System.out.println("\n TEST-4: " +   bscrud.review(c)  + "\n");
		
		System.out.println(bscrud.review(c).equals(list));
		assertEquals(bscrud.review(c),list);
	}
	
	@Test
	@Ignore
	public void test5(){
		@SuppressWarnings("unchecked")
		List<Articles> list = (List<Articles>)bscrud.review(c);
		System.out.println(bscrud.review(c));
		bscrud.remove(list, c);
		System.out.println(bscrud.review(c));
		assertTrue(bscrud.review(c).isEmpty());
	}
	
	@Test
	@Ignore
	public void test6(){
		a1.setTitle("Title 1-1");
		acrud.update(a1);
		System.out.println("UPDATE - "+acrud.findByTitle(a1.getTitle()));
		acrud.delete(a1);
		System.out.println("DELETE - "+acrud.findByTitle(a1.getTitle()));
	}
	
}

