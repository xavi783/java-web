package com.uk.control;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.mongodb.BasicDBObject;
import com.uk.blog.utils.CategoriesBuilder;
import com.uk.control.utils.RedirectUtils;
import com.uk.database.mongo.MongoCRUD;
import com.uk.database.mongo.impl.PlainMongoCRUD;
import com.uk.database.mysql.MySqlCRUD;
import com.uk.shop.mongo.CommentsMongo;
import com.uk.shop.mongo.ProductMongo;
import com.uk.shop.mysql.Family;
import com.uk.shop.mysql.Product;

@Controller
public final class ShopController extends SectionController{
		
	@Autowired private CategoriesBuilder categoriesBuilder;
	@Autowired private MongoCRUD<ProductMongo> productMongoDao;
	@Autowired private MongoCRUD<CommentsMongo> commentsMongoDao;
	@Autowired private MySqlCRUD<Integer,Product> productDao;
	@Autowired private MySqlCRUD<Integer,Family> familiesDao;
	
	private final String langPfx = "/{lang:(?:es|en)?}";
	private String defaultEditionURL = "writeArticles";
	
	private String 
		home     = "html/shop/home",
		category = "html/shop/search",
		product  = "html/shop/article",
		edition  = "html/shop/edition/",
		menuBar  = "/shop/categories/",
		allCategories = "all-books/";
				
	@RequestMapping(value = {langPfx+"/shop",langPfx+"/shop/"}, method = GET)
	public String getHome(Model model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable String lang){
		populate(model, "/shop/home", lang);
		return this.home;
	}
	
	@RequestMapping(value = {langPfx+"/shop/category/{category:\\S+}/"}, method = GET)
	public String getCategory(Model model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable String lang, @PathVariable String category){
		category = RedirectUtils.convertPath(category);
		
		List<Family> listFamilies = familiesDao.findByField("url", category);
		if(listFamilies==null){
			try {
				response.sendError(404);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<ProductMongo> mdbList = (!category.equals(allCategories))?
				productMongoDao.search(new BasicDBObject("category_id",listFamilies.get(0).getId())):
				productMongoDao.findAll();
		if(mdbList==null) mdbList = new ArrayList<ProductMongo>();
			
		model.addAttribute("prods", mdbList);	
		populate(model, "/shop/search", lang);		
		return this.category;
	}
	
	@RequestMapping(value = {langPfx+"/shop/category/{\\S+}/{product:\\S+}"}, method = GET)
	public String getProduct(Model model, HttpServletRequest request, HttpServletResponse response, 
			@PathVariable String lang, @PathVariable String product){		
		
		ProductMongo productMongo = productMongoDao.searchById(product);
		if(productMongo==null){
			try {
				response.sendError(404);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		CommentsMongo commentsMongo = commentsMongoDao.searchById(product);
		if(commentsMongo==null) commentsMongo = new CommentsMongo();
			
		model.addAttribute("prod", productMongo);
		model.addAttribute("comments", commentsMongo);
		populate(model, "/shop/search", lang);				
		
		return this.product;
	}
	
	@RequestMapping(value = {langPfx+"/shop/edition",langPfx+"/shop/edition/*"}, method = GET)
	public String getEdition(Model model, HttpServletRequest request) {
		String path = RedirectUtils.getLastPath(request.getRequestURI(),defaultEditionURL);
		((ObjectNode)getMongoTemplate().getRootNode()).set("title", new TextNode(("Usukuma Blog ") + path));		
		populate(model, "/shop/edition", super.getLang());
		return this.edition+path;
	}
	
	@RequestMapping(value = {langPfx+"/shop/categories/"}, method = {GET,POST}, produces="application/json")
	public @ResponseBody Object getMenuBar(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable String lang) {
		PlainMongoCRUD<BasicDBObject> menu = new PlainMongoCRUD<BasicDBObject>(productMongoDao.getMongodao());
		menu.setCollectionName("templates");		
		return menu.searchById(lang+menuBar).get("root");
	}

	public void setHome(String home) {
		this.home = home;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setProduct(String product) {
		this.product = product;
	}
	
	public void setEdition(String edition) {
		this.edition = edition;
	}
	
}
