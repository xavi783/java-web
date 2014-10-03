package com.uk.control.utils;

import java.util.List;

public class Router {
	
	private String view;
	private List<String> urls;
	
	public Router() {
	}
	
	public Router(String view, List<String> urls) {
		this.view = view;
		this.urls = urls;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String[] getUrls() {
		return (String[])urls.toArray();
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

}
