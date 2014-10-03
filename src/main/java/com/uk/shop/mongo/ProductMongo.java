package com.uk.shop.mongo;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.uk.database.mongo.MongoMapper;
import com.uk.database.mongo.MongoSettler;

public class ProductMongo extends MongoSettler implements MongoMapper {
	
	private int category_id;
	private String _id, title, source, image, price;
	private Map<String,Object> offer, overview, content, author, learn, detail, approach, isfor;	
	private BasicDBObject mongoObject = new BasicDBObject();
	private Boolean offered;
	
	public ProductMongo(){
		offer = new HashMap<String,Object>();
		offer.put("discount","");
		offer.put("original","");

		overview = new HashMap<String,Object>();
		overview.put("title","");
		overview.put("content","");

		content = new HashMap<String,Object>(overview);
		author = new HashMap<String,Object>(overview);
		learn = new HashMap<String,Object>(overview);
		detail = new HashMap<String,Object>(overview);
		approach = new HashMap<String,Object>(overview);
		isfor = new HashMap<String,Object>(overview);
	}
	
	@Override
	public MongoMapper map(){
		mongoObject
			.append("_id",this._id)
			.append("category_id", this.category_id)
			.append("title",this.title)
			.append("source",this.source)
			.append("image",this.image)
			.append("price",this.price)
			.append("offer",this.offer)
			.append("overview",this.overview)
			.append("content",this.content)
			.append("author",this.author)
			.append("learn",this.learn)
			.append("detail",this.detail)
			.append("approach",this.approach)
			.append("isfor",this.isfor);
		return this;		
	}
	
	@Override
	public MongoMapper mapFrom(Map<String,Object> object){
		mongoObject.putAll(object);
		return this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MongoMapper convert(DBObject source){
		this._id         = (String) source.get("_id");
		this.category_id = (Integer) source.get("category_id");
		this.title     	 = (String) source.get("title");
		this.source      = (String) source.get("source");
		this.image       = (String) source.get("image");
		this.price       = (String) source.get("price");
		this.offer       = (HashMap<String,Object>)source.get("offer");
		this.overview    = (HashMap<String,Object>)source.get("overview");
		this.content     = (HashMap<String,Object>)source.get("content");
		this.author      = (HashMap<String,Object>)source.get("author");
		this.learn       = (HashMap<String,Object>)source.get("learn");
		this.detail      = (HashMap<String,Object>)source.get("detail");
		this.approach    = (HashMap<String,Object>)source.get("approach");
		this.isfor       = (HashMap<String,Object>)source.get("isfor");
		this.setOffered(!this.offer.get("discount").equals(""));
		return this;
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public Boolean getOffered() {
		return offered;
	}

	public void setOffered(Boolean offered) {
		this.offered = offered;
	}

	@SuppressWarnings("unchecked")
	public DBObject getMongoObject() {
		return mongoObject;
	}
	public ProductMongo setMongoObject(DBObject mongoObject) {
		this.mongoObject = (BasicDBObject)mongoObject;
		return this;
	}

	@Override
	public String toString() {
		return "ProductMongo [ _id=" + _id + "]";
	}
	
}
