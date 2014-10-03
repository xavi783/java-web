package com.uk.blog.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uk.blog.mysql.dao.CategoriesDao;
import com.uk.blog.mysql.Categories;

import static com.uk.blog.utils.CategoriesUtils.*;

public class CategoriesBuilder{

	private @Autowired CategoriesDao categoriesDao;
	private List<Categories> list;

	public CategoriesBuilder() {
	}
	
	public CategoriesBuilder(CategoriesDao categoriesDao) {
		this.categoriesDao = categoriesDao;
	}
	
	public CategoriesDao getCategoriesDao() {
		return categoriesDao;
	}

	public void setCategoriesDao(CategoriesDao categoriesDao) {
		this.categoriesDao = categoriesDao;
	}

	@SuppressWarnings({ "unchecked" })
	public Categories[] populate(){
		list = (ArrayList<Categories>)categoriesDao.findAll();
		List<Categories> terminals = null;
		
		while(terminals==null||!terminals.isEmpty()){
			terminals = (List<Categories>)getTerminals(list);
			for(Categories c : terminals){
				c.addToParentNode(getCategoryById(list,c.getParent()));
			}
			list.removeAll(terminals);
		}
		
		if(!list.isEmpty()){
			Categories[] array = new Categories[list.size()];
			list.toArray(array);
			return array;
		} else {
			throw new RuntimeException("Root node is lost");
		}
	}

}
