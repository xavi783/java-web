package com.uk.control;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static com.google.common.base.Predicates.in;
import static com.google.common.collect.Maps.filterKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.common.collect.ImmutableMap;
import com.mongodb.DBObject;
import com.uk.database.mongo.MongoCRUD;
import com.uk.shop.mongo.ProductMongo;
import com.stripe.model.Token;

@Controller
@Scope("session")
@SessionAttributes("cart")
public final class StripeCartController extends SectionController implements CartController<ProductMongo, Map<String,String>> {
	
	@Autowired private MongoCRUD<ProductMongo> productMongoDao;
	private List<Map<String, String>> cart;
	private List<ProductMongo> productsCart;
	private Token token;
	private final Map<String,String> productPattern;
	private final String langPfx = "/{lang:(?:es|en)?}";
	private static final ImmutableMap<String,String> Views = ImmutableMap.of(
			"checkout", "html/shop/cart",
			"confirm", "html/shop/cart",
			"process", "/shop/checkout/"
	); 
	
	{
		productsCart   = new ArrayList<ProductMongo>();
		cart           = new ArrayList<Map<String,String>>();
		productPattern = new HashMap<String,String>();
		
		productPattern.put("_id", "");
		productPattern.put("quant", "");
		productPattern.put("price", "");
		productPattern.put("x", "<span class='x-icon'></span>");
	}

	@Override
	public List<Map<String,String>> getCart() {
		return this.cart;
	}

	@Override
	public ImmutableMap<String,String> getViews() {
		return Views;
	}

	@Override
	@RequestMapping(value = {"/addItem","*/addItem","**/addItem"}, method = POST, consumes="application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
	public synchronized @ResponseBody List<Map<String,String>> add(@RequestBody Map<String,String> item) {
		//TODO: ensure non-blocking atomic modification
		ProductMongo pm = new ProductMongo();
		this.buildProduct(pm, item);
		cart.add(item);
		productsCart.add(pm);
		return cart;
	}

	@Override
	@RequestMapping(value = {"/removeItem","*/removeItem","**/removeItem"}, method = POST, consumes="application/json", produces="application/json")
	public synchronized @ResponseBody List<Map<String,String>> remove(@RequestBody Map<String,String> item) {
		//TODO: ensure non-blocking atomic modification
		ProductMongo pm = new ProductMongo();
		this.buildProduct(pm, item);
		cart.remove(item);
		productsCart.remove(pm);
		return cart;
	}

	@Override
	@RequestMapping(value = {"/removeItemByPos","*/removeItemByPos","**/removeItemByPos"}, method = POST, consumes="application/json", produces="application/json")
	public synchronized @ResponseBody List<Map<String,String>> remove(@RequestBody Integer position) {
		cart.remove(position);
		productsCart.remove(position);
		return cart;
	}
	
	
	@Override
	@RequestMapping(value = {langPfx+"**cart/checkout{/?}",}, method = POST)
	public String checkout(Model model, HttpServletRequest request, HttpServletResponse response) {
		populateModelWithCarts(model,"checkout");
		populate(model, "/shop/search", this.getLang());
		return Views.get("checkout");
	}
	

	@Override
	@RequestMapping(value = {langPfx+"/shop**/confirm{/?}"}, method = GET)
	public String confirm(Model model, HttpServletRequest request, HttpServletResponse response) {
		populateModelWithCarts(model,"confirm");
		populate(model, "/shop/search", getLang());
		return Views.get("confirm");
	}
	

	@Override
	@RequestMapping(value = "/v1/tokens", method = POST, consumes="application/json")
	public <T> StripeCartController process(Model model, HttpServletRequest request, HttpServletResponse response, @RequestBody T token) {
		this.token = (Token)token;
		try {
			response.sendRedirect(Views.get("process"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public StripeCartController buildProduct(ProductMongo productMongo, Map<String,String> item){
		Map<String,String> product;
		DBObject productDB;
		
		product      = new HashMap<String,String>(productPattern);	
		productMongo = productMongoDao.searchById(item.get("_id"));
		productDB    = productMongo.map().getMongoObject();
		
		product.putAll( filterKeys( productDB.toMap(), in(product.keySet()) ) );
		product.put("quant", item.get("quant"));
		product.put("price", product.get("price").replaceAll("[\\W&&[^\\.]]", "") );
		
		item = product;		
		return this;
	}

	
	private void populateModelWithCarts(Model model, String view){
		model.addAttribute("cart" ,this.cart);
		model.addAttribute("token",this.token);
		model.addAttribute("prods",this.productsCart);
		model.addAttribute("view" ,view);
	}

}
