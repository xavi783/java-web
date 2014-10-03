package com.uk.scraper.strategies.queries;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.uk.scraper.strategies.Query;
import com.uk.shop.mongo.ProductMongo;

public class ShopArticleQuery implements Query<String,ProductMongo> {
	
	private Document doc = null;
	private ProductMongo article;
	private String subject;
	
	private Integer category_id;
	private String _id, title, source, image, price;
	private Map<String,Object> offer, overview, content, author, learn, detail, approach, isfor;

	public ShopArticleQuery() {
		this.article = new ProductMongo();
		this.initialize();
	}
	
	public ShopArticleQuery(String subject) {
		this.article = new ProductMongo();
		this.setSubject(subject);
		this.initialize();
	}
	
	public ShopArticleQuery(String subject, Integer category_id) {
		this.article = new ProductMongo();
		this.setSubject(subject);
		this.initialize();
		this.category_id = category_id;
	}

	@Override
	public ProductMongo perform() {
		return perform(this.subject);
	}

	@Override
	public ProductMongo perform(String subject) {
		try {
			this.doc = Jsoup.connect(subject).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.build();
		this.article.setCategory_id(this.category_id);
		return this.article;
	}

	@Override
	public String getSubject() {
		return this.subject;
	}

	@Override
	public Query<String,ProductMongo> setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public ProductMongo build(){
		this
			.get_id()
			.getTitle()
			.getSource()
			.getImage()
			.getPrice()
			.getOffer()
			.getOverview()
			.getContent()
			.getAuthor()
			.getLearn()
			.getDetail()
			.getApproach()
			.getIsfor();
		this.article.map();
		return this.article;
	}

	protected void initialize(){
		this.offer = new HashMap<String,Object>();
		this.offer.put("discount","");
		this.offer.put("original","");

		this.overview = new HashMap<String,Object>();
		this.overview.put("title","");
		this.overview.put("content","");

		this.content = new HashMap<String,Object>(overview);
		this.author = new HashMap<String,Object>(overview);
		this.learn = new HashMap<String,Object>(overview);
		this.detail = new HashMap<String,Object>(overview);
		this.approach = new HashMap<String,Object>(overview);
		this.isfor = new HashMap<String,Object>(overview);
	}

	protected ShopArticleQuery get_id() {
		this._id = doc.select(".breadcrumb a").last().attr("href");
		this._id = this._id.substring(1);
		this._id = this._id.substring(0,_id.indexOf("/"));
		this.article.set_id(_id);
		return this;
	}

	protected ShopArticleQuery getTitle() {
		this.title = doc.select("#content-header h1").get(0).html();
		this.article.setTitle(this.title);
		return this;
	}

	protected ShopArticleQuery getSource() {
		this.source = "http://www.packtpub.com"+doc.select("#breadcrumb .breadcrumb a").last().attr("href");
		this.article.setSource(this.source);
		return this;
	}

	protected ShopArticleQuery getImage() {
		this.image = doc.select(".cover-images .bookimage").get(0).attr("src");
		this.article.setImage(this.image);
		return this;
	}

	protected ShopArticleQuery getPrice() {
		this.price = doc.select(".price-choice .real-price .larger").get(0).html();
		this.article.setPrice(this.price);
		return this;
	}

	protected ShopArticleQuery getOffer() {
		this.article.setField("offer","discount",doc.select(".price-choice .real-price .saving").get(0).html().replaceAll("€", ""));
		this.article.setField("offer","original",doc.select(".price-choice .price-inner .price").get(0).html().replaceAll("[?:save|!|\\s]", ""));
		return this;
	}

	protected ShopArticleQuery getOverview() {
		this.article.setField("overview","title", doc.select(".details-tabs #tab-overview").html());
		Elements e = doc.select("#panel-overview .overview_right div");
		e.select("a[href]").removeAttr("href");
		String[] s = {doc.select("#panel-overview .overview_left ul").html(),e.html()};
		this.article.setField("overview","content", s);
		return this;
	}

	protected ShopArticleQuery getContent() {
		this.article.setField("content","title", doc.select(".details-tabs #tab-toc").html());
		this.article.setField("content","content", doc.select("#panel-toc").html());
		return this;
	}

	protected ShopArticleQuery getAuthor() {
		this.article.setField("author","title", doc.select(".details-tabs #tab-author").html());
		this.article.setField("author","content", doc.select("#panel-author").html());
		return this;
	}

	protected ShopArticleQuery getLearn() {
		this.article.setField("learn","title", doc.select("#learn .titlebar h2").html());
		this.article.setField("learn","content", doc.select("#learn ul").html());
		return this;
	}

	protected ShopArticleQuery getDetail() {
		this.article.setField("detail","title", doc.select("#in_detail .titlebar h2").html());
		Elements e = doc.select("#in_detail");
		e.select(".titlebar").remove();
		this.article.setField("detail","content", e.html());
		return this;
	}

	protected ShopArticleQuery getApproach() {
		this.article.setField("approach","title", doc.select("#approach .titlebar h2").html());
		this.article.setField("approach","content", doc.select("#approach").select(".titlebar").remove().html());
		return this;
	}

	protected ShopArticleQuery getIsfor() {
		this.article.setField("isfor","title", doc.select("#audience .titlebar h2").html());
		this.article.setField("isfor","content", doc.select("#audience").select(".titlebar").remove().html());
		return this;
	}

	@Override
	public String toString() {
		return "ShopArticleQuery [subject: " + this.subject + ", category_id: " + this.category_id + "]";
	}

}
