package com.uk.database.mongo;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class MongoSettler {
	
	@SuppressWarnings("unchecked")
	public void setField(String object, String fieldname, Object value){
		for (Field field : this.getClass().getDeclaredFields()) {
		    if (object != null && object.equals(field.getName())) {
		    	try {
		    		field.setAccessible(true);
		    		if (field.get(this) instanceof Map){
		    			Map<String,Object> mymap = (Map<String,Object>)field.get(this);
		    			if(mymap.containsKey(fieldname)){
		    				mymap.put(fieldname, value);
		    				field.set(this, mymap);
		    			}else{
		    				System.err.println("WARNING: "+object+" not contains "+fieldname+" field");
		    			}		
		    		}else{
		    			System.err.println("WARNING: "+object+" is not a field map of this object");
		    		}
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		    }
		    field.setAccessible(false);
		}
	}
	
	public Object getField(String object){
		for (Field field : this.getClass().getDeclaredFields()) {
		    if (object != null && object.equals(field.getName())) {
		    	try {
		    		field.setAccessible(true);
					Object o = field.get(this);
					if(field.get(this) instanceof Map){
						return o;
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		    }
		    field.setAccessible(false);
		}
		return null;
	}

}
