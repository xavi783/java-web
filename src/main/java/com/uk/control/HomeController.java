package com.uk.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.uk.control.cookies.CookiesNotify;
import com.uk.control.utils.RedirectUtils;


/**
 * Handles requests for the application home page.
 */
@Controller
@Scope("session")
@SessionAttributes("cookiesAccepted")
public class HomeController extends SectionController {
		
	private final String langPfx = "/{lang:(?:es|en)}";
	private CookiesNotify cookiesAccepted;
	
	@RequestMapping(value = {"/setlang","*/setlang","**/setlang"}, method = GET)
	public void lang(Model model, HttpServletRequest request, HttpServletResponse response) {
		if(request.getParameter("lang")!=null){
			this.setLang(request.getParameter("lang"));
		}		
		String path = request.getRequestURI()
						.replaceFirst("/web(?:/es|/en)?", "")
						.replaceFirst("/setlang", "");
		try {
			path = ("/web/"+super.getLang()+"/"+path).replaceFirst("//", "/");
			path = path.charAt(path.length()-1)=='/'?path:path+"/";
			response.sendRedirect(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/","/login"}, method = GET)
	public String home(Model model) {
		populate(model, "/home");
		return "html/home";
	}
	
	@RequestMapping(value = {langPfx,langPfx+"/login"}, method = GET)
	public String home(Model model, @PathVariable String lang) {
		populate(model, "/home", lang);
		return "html/home";
	}
	
	@RequestMapping(value = {langPfx+"styleguide"}, method = GET)
	public String styles(Model model) {
		return "jsp/styleguide";
	}
	
	@RequestMapping(value = {"*/profile"}, method = GET)
	public String profile(Model model, HttpServletRequest request){
		return "/blog/edition";
	}
	
	@RequestMapping(value = {"*cookiesAccepted","/*cookiesAccepted","/cookiesAccepted","*/cookiesAccepted"}, method = POST, consumes="application/json", produces="application/json")
	public @ResponseBody CookiesNotify setCookiesAccepted(Model model, @RequestBody CookiesNotify cookiesAccepted) {
		this.cookiesAccepted = cookiesAccepted;
		model.addAttribute("cookiesAccepted",this.cookiesAccepted);
		return this.cookiesAccepted;
	}
	
	@RequestMapping(value = {"*cookiesAccepted","/*cookiesAccepted","/cookiesAccepted","*/cookiesAccepted"}, method = GET, produces="application/json")
	public @ResponseBody CookiesNotify getCookiesAccepted(Model model) {
		if(this.cookiesAccepted==null){
			this.cookiesAccepted = new CookiesNotify(getMongoTemplate().setTemplateId("cookiesAccepted"+getLang().toUpperCase()));
		}
		model.addAttribute("cookiesAccepted",this.cookiesAccepted);
		return this.cookiesAccepted;
	}

	public CookiesNotify getCookiesAccepted() {
		return cookiesAccepted;
	}
	
}
