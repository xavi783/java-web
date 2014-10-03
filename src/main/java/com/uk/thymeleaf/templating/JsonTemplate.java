package com.uk.thymeleaf.templating;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonTemplate<T> implements Template {
	
	private JsonNode rootNode;
	private final String SEP = ".";

	public String get(String query){
		String s;
		JsonNode current = rootNode;
		StringBuilder sb = new StringBuilder();
		Pattern p = Pattern.compile("\\w+\\[\\d\\](\\..)*");
		StringTokenizer tokenizer = new StringTokenizer((new StringBuilder(query)).toString(), SEP);
		
		while (tokenizer.hasMoreTokens()) {
			s = tokenizer.nextToken();			
			current = (!p.matcher(s).matches()) ? 
					  current.get(s) :
					  current.get(sb.replace(0,sb.length(),s).substring(0,sb.indexOf("["))).
					  		  get(Integer.parseInt(sb.substring(sb.indexOf("[")+1,sb.indexOf("]"))));
			if((current.isArray()||current.isContainerNode()) && !tokenizer.hasMoreTokens()){
				return current.toString();
			}
	    }
		String ans = current.isNumber()?String.valueOf(current.numberValue()):
					 (current.isBoolean()?(current.asBoolean()?"true":"false"):current.textValue());
		return ans;
	}

	public JsonNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(JsonNode rootNode) {
		this.rootNode = rootNode;
	}
	
	@SuppressWarnings("hiding")
	protected <T extends String, File, JsonParser, Reader, URL> void map(T file){
		ObjectMapper mapper = new ObjectMapper();
		try {
			rootNode = mapper.readValue(file, JsonNode.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
