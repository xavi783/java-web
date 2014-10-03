package com.uk.blog.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.uk.blog.mysql.Categories;

public class CategoriesUtils {	
	@SuppressWarnings("unchecked")
	public static Set<? extends Number> getTerminalIds(Set<? extends Number> elements, Set<? extends Number> parents){
		Set<? extends Number> terminals = new HashSet<Integer>((Set<Integer>)elements);
		terminals.removeAll(parents); //todos los elementos que no son padres de ninguno
		return terminals;
	}
	
	public static List<? extends Categories> getTerminals(List<? extends Categories> categories){
		Set<? extends Number> ids = getElementIds(categories);
		ids.removeAll(getParentIds(categories));
		List<? extends Categories> checkedTerminals = getCategoriesByIds(categories,ids);
		List<? extends Categories> uncheckedTerminals = getCategoriesByIds(categories,ids);
		for(Categories c : uncheckedTerminals){
			if(c.getParent()==null){
				checkedTerminals.remove(c);
			}
		}
		return checkedTerminals;
	}

	public static Set<? extends Number> getParentIds(List<? extends Categories> categories){
		Set<Integer> parents = new HashSet<Integer>();		
		for(Categories c : categories){	parents.add(c.getParent());	}
		return parents;
	}
	
	public static List<? extends Categories> getParents(List<? extends Categories> categories){
		return getCategoriesByIds(categories,getParentIds(categories));
	}

	public static Set<? extends Number> getElementIds(List<? extends Categories> categories){
		Set<Integer> elements = new HashSet<Integer>();		
		for(Categories c : categories){
			elements.add(c.getId());
		}
		return elements;
	}

	public static List<? extends Categories> getCategoriesByIds(List<? extends Categories> list, Set<? extends Number> elements){
		List<Categories> sublist = new ArrayList<Categories>();	
		for(Categories c : list){
			if(elements.contains(c.getId())){
				sublist.add(c);
			}
		}
		return sublist;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Number, E extends Categories> E getCategoryById(List<? extends Categories> categories, T id){	
		for(Categories c : categories){
			if(id.equals(c.getId())){
				return (E)c;
			}
		}
		return null;
	}
}
