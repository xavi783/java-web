package com.uk.thymeleaf.templating;

import java.io.File;
import java.io.IOException;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFileTemplate extends JsonTemplate<File> implements ResourceLoaderAware {
	
	private String filename;
	private ResourceLoader resourceLoader;
	private File file;
	
	public JsonFileTemplate(){	
	}
	
	public JsonFileTemplate(String filename){
		this.filename = filename;  
		this.retrieve();		
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
		this.retrieve();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		this.map(file);
	}
		
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;		
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new JsonFileTemplate();
	}

	private void retrieve(){		
		try {
			file = resourceLoader.getResource(filename).getFile();
			this.map(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void map(File file){
		ObjectMapper mapper = new ObjectMapper();
		try {
			super.setRootNode(mapper.readValue(file, JsonNode.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
