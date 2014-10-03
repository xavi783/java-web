package com.uk.thymeleaf.templating;

import java.util.List;

public class HtmlTemplate {
	private String title;
	private String[] replace;
	private List<?> header, content, footer;
		
	public HtmlTemplate() {
	}
		
	public HtmlTemplate(String title, String[] replace) {
		this.title = title;
		this.replace = replace;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getReplace() {
		return replace;
	}

	public void setReplace(String[] replace) {
		this.replace = replace;
	}

	public List<?> getHeader() {
		return header;
	}

	public void setHeader(List<?> header) {
		this.header = header;
	}

	public List<?> getContent() {
		return content;
	}

	public void setContent(List<?> content) {
		this.content = content;
	}

	public List<?> getFooter() {
		return footer;
	}

	public void setFooter(List<?> footer) {
		this.footer = footer;
	}
	
}
