package com.uk.control.cookies;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uk.thymeleaf.templating.JsonTemplate;
import com.uk.thymeleaf.templating.MongoDBTemplate;

public class CookiesNotify {
	
	private String url = "cookiesAccepted";
	private String content =  "<p></p>";
	private Boolean accepted = false;
	@JsonIgnore private MongoDBTemplate template;
	
	public CookiesNotify() {
	}
	
	public CookiesNotify(String url, String content, Boolean accepted) {
		this.url = url;
		this.content = content;
		this.accepted = accepted;
	}
	
	public CookiesNotify(MongoDBTemplate template) {
		this.setTemplate(template);
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Boolean isAccepted() {
		return accepted;
	}
	
	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	
	@SuppressWarnings("rawtypes")
	public JsonTemplate getTemplate() {
		return template;
	}
	

	public void setTemplate(MongoDBTemplate template) {
		this.template = template;
		this.setUrl(template.get("url"));
		this.setAccepted(template.get("accepted").equals("true"));
		this.setContent(template.get("content"));
	}

	@Override
	public String toString() {
		return "CookiesNotify [url=" + url + ", content=" + content
				+ ", accepted=" + accepted + "]";
	}
	
}
