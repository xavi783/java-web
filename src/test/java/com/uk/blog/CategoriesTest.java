package com.uk.blog;

import general.BaseTest;

import java.util.List;

import org.junit.Test;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

import com.uk.blog.mysql.dao.CategoriesDao;
import com.uk.blog.mysql.Categories;
import com.uk.blog.utils.CategoriesBuilder;


public class CategoriesTest extends BaseTest{
	
	private @Autowired CategoriesDao categoriesDao;
	
	@Test
	@Ignore
	public void getAllCategories() {
		List<Categories> list = categoriesDao.findAll();
		for(Categories c : list){
			System.out.println(c.toString());
		}
	} 
	
	@Test
	@Ignore
    public void populate() {
		Categories[] cb = (new CategoriesBuilder(categoriesDao)).populate();
		for (Categories c : cb) {
			System.out.println(c);
		}
    }

}