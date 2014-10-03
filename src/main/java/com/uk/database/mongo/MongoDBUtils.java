package com.uk.database.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.uk.database.DBUtils;

public class MongoDBUtils implements DBUtils<DB> {
	
	private int port;
	private char[] password;
	private String host, db, user, source;
	private ServerAddress adress;
	private MongoClient mongoClient;
	private List<MongoCredential> credentials  = new ArrayList<MongoCredential>();
	
	public MongoDBUtils() {
	}

	public MongoDBUtils(String host, String db, int port) {
		this.host = host;
		this.db = db;
		this.port = port;
	}
	
	public MongoDBUtils(String host, String db, int port, String user, String source, char[] password) {
		this.host = host;
		this.db = db;
		this.port = port;
		this.user = user;
		this.source = source;
		this.password = password;
	}

	@Override
	public DB getSession() {
		try { adress = new ServerAddress(host, port); } catch (UnknownHostException e) { e.printStackTrace(); }
		if (this.authenticate()!=null)
			credentials.add(this.authenticate());
		mongoClient = (!credentials.isEmpty())?new MongoClient(adress,credentials):new MongoClient(adress);
		return mongoClient.getDB(this.db);
	}
	
	private MongoCredential authenticate(){
		return this.authenticate(this.user,this.source,this.password);
	}
	
	private MongoCredential authenticate(String user, String source, char[] password){
		if(user!=null && !user.equals("") && password!=null && !password.equals(' ') && source!=null && !source.equals("")){
			return MongoCredential.createMongoCRCredential(user,source,password);
		}else{
			return null;
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	
	public String getUser() {
		return user;
	}
	

	public void setUser(String user) {
		this.user = user;
	}
	

	public String getSource() {
		return source;
	}
	

	public void setSource(String source) {
		this.source = source;
	}
	

	public char[] getPassword() {
		return password;
	}
	

	public void setPassword(char[] password) {
		this.password = password;
	}
	
}
