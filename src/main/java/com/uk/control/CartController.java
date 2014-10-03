package com.uk.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.google.common.collect.ImmutableMap;

public interface CartController<K,V> {
	
	public List<V> getCart();
	
	public ImmutableMap<String,String> getViews();
	
	public List<V> add(V item);
	
	public List<V> remove(V item);
	
	public List<V> remove(Integer position);
	
	public String checkout(Model model, HttpServletRequest request, HttpServletResponse response);
	
	public String confirm(Model model, HttpServletRequest request, HttpServletResponse response);
	
	public <T> CartController<K,V> process(Model model, HttpServletRequest request, HttpServletResponse response, T token);
	
	public CartController<K,V> buildProduct(K product, V item);
}
