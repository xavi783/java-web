package com.uk.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.uk.thymeleaf.templating.MongoDBTemplate;

@Controller
public class SectionController {
	
	@Autowired @Qualifier("mongoTemplate") private MongoDBTemplate mongoTemplate;
	@Autowired @Qualifier("footerTemplate") private MongoDBTemplate footerTemplate;
	
	protected final String langPfx = "/{lang:(?:es|en)?}";
	private String lang = "en";
	
	public void populate(Model model, String _id){
		model.addAttribute("template", mongoTemplate.setTemplateId(_id+this.lang.toUpperCase()));
		model.addAttribute("footer", this.getFooterTemplate());	
		model.addAttribute("language",this.getLang());
	}
	
	public void populate(Model model, String _id, String lang){
		this.setLang(lang);
		model.addAttribute("template", mongoTemplate.setTemplateId(_id+this.lang.toUpperCase()));
		model.addAttribute("footer", this.getFooterTemplate());	
		model.addAttribute("language",this.getLang());
	}
	
	public void populateTemplate(Model model, String _id){
		model.addAttribute("template", mongoTemplate.setTemplateId(_id));
	}
	
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public MongoDBTemplate getFooterTemplate() {
		this.footerTemplate.setTemplateId("footer"+this.lang.toUpperCase());
		return this.footerTemplate;
	}

	public void setFooterTemplate(MongoDBTemplate footerTemplate) {
		this.footerTemplate = footerTemplate;
	}
	
	public MongoDBTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoDBTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public String getLangPfx() {
		return langPfx;
	}

}
