package com.uk.shop.mongo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.uk.database.mongo.MongoMapper;

public class CommentsMongo implements MongoMapper {
	
	private String _id;
	private List<CommentsMongo.Comment> comments;	
	private BasicDBObject mongoObject = new BasicDBObject();
	
	public CommentsMongo(){
		comments = new ArrayList<CommentsMongo.Comment>();
	}
	
	@Override
	public MongoMapper map(){
		this.mongoObject
			.append("_id",this._id)
			.append("comments", this.comments);
		return this;		
	}
	
	@Override
	public MongoMapper mapFrom(Map<String,Object> object){
		mongoObject.putAll(object);
		return this;
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public MongoMapper convert(DBObject object) {
		this._id         = (String) object.get("_id");
		this.comments    = (List<CommentsMongo.Comment>) object.get("comments");
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DBObject getMongoObject() {
		return mongoObject;
	}

	@Override
	public MongoMapper setMongoObject(DBObject mongoObject) {
		this.mongoObject = (BasicDBObject)mongoObject;
		return this;
	}
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public List<CommentsMongo.Comment> getComments() {
		return comments;
	}

	public void setComments(List<CommentsMongo.Comment> comments) {
		this.comments = comments;
	}
	
	public class Comment implements MongoMapper{
		
		private SimpleDateFormat sdf;
		private BasicDBObject mongoObject = new BasicDBObject();
		private String authorName, authorRef, content;
		private Date pubdate;		
		
		public Comment(){
			pubdate = new Date();
		}
		
		public Comment(String authorName, String authorRef, String content) {
			this();
			this.authorName = authorName;
			this.authorRef = authorRef;
			this.content = content;
		}
		
		@Override
		public MongoMapper map() {
			this.mongoObject
				.append("authorName",this.authorName)
				.append("authorRef",this.authorRef)
				.append("pubdate",this.pubdate)
				.append("content",this.content);
			return this;
		}
		
		@Override
		public MongoMapper mapFrom(Map<String, Object> object) {
			this.authorName = object.get("authorName").toString();
			this.authorRef = object.get("authorRef").toString();
			this.content = object.get("content").toString();
			this.map();
			return this;
		}
		
		@Override
		public MongoMapper convert(DBObject object) {
			this.authorName = (String) object.get("authorName");
			this.authorRef  = (String) object.get("authorName");			
			this.content    = (String) object.get("content");
			try {
				this.pubdate = sdf.parse((String)object.get("pubdate"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return this;
		}

		@Override
		@SuppressWarnings("unchecked")
		public DBObject getMongoObject() {
			return this.mongoObject;
		}
		
		@Override
		public <T extends DBObject> MongoMapper setMongoObject(T mongoObject) {
			this.mongoObject = (BasicDBObject) mongoObject;
			return this;
		}

		public String getAuthorName() {
			return authorName;
		}

		public void setAuthorName(String authorName) {
			this.authorName = authorName;
		}

		public String getAuthorRef() {
			return authorRef;
		}

		public void setAuthorRef(String authorRef) {
			this.authorRef = authorRef;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			content = (content.length()==255)?content.substring(0, 251)+"...":content;
			this.content = content;
		}

		public Date getPubdate() {
			return pubdate;
		}
		
	}
}
