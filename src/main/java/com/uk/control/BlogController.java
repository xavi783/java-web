package com.uk.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.uk.blog.mysql.TextEditor;
import com.uk.blog.utils.CategoriesBuilder;
import com.uk.blog.utils.TreeNode;
import com.uk.control.utils.RedirectUtils;
import com.uk.login.utils.RolesList;
import com.uk.login.utils.UsersList;

@Controller
public class BlogController extends SectionController {	
		
	@Autowired private CategoriesBuilder categoriesBuilder;
	@Autowired private UsersList userList;
	@Autowired private RolesList rolesList;

	protected final String langPfx = "/{lang:(?:es|en)?}";
	private String defaultEditionURL = "writeArticles";
			
	@RequestMapping(value = {langPfx+"/blog",langPfx+"/blog/"}, method = GET)
	public String blog(Model model, @PathVariable String lang) {
		populate(model, "/blog/home", lang);
		return "html/blog/home";
	}
	
	@RequestMapping(value = {langPfx+"/blog/edition",langPfx+"/blog/edition/*"}, method = RequestMethod.GET)
	public String edition(Model model, HttpServletRequest request) {		
		String path = RedirectUtils.getLastPath(request.getRequestURI(),defaultEditionURL);
		path=path.equals("edition")?defaultEditionURL:path;
		this.populateTemplate(model, "/blog/editionEN");
		return "html/blog/edition/"+path;
	}
		
	@RequestMapping(value = langPfx+"/blog/edition/editor/save", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody Boolean saveEditor(@RequestBody String editorData) {
		TextEditor editor = new TextEditor().setValue(editorData).setSaved(true);
		return editor.isSaved();
	}

	// -- SEND REST DATA
	@RequestMapping(value = {langPfx+"/blog/**/categories",langPfx+"/blog/categories",langPfx+"**/blog/categories"}, method = GET, produces="application/json")
	public @ResponseBody TreeNode[] getCategories() {
		 return categoriesBuilder.populate();
	}
	
	@RequestMapping(value = {langPfx+"/blog/**/usersData",langPfx+"/blog/usersData",langPfx+"**/blog/usersData"}, method = GET, produces="application/json")
	public @ResponseBody UsersList getUsersData() {
		return userList.buildHeaders().buildDataSource();
	}
	
	@RequestMapping(value = {langPfx+"/blog/**/rolesData",langPfx+"/blog/rolesData",langPfx+"**/blog/rolesData"},method = GET, produces="application/json")
	public @ResponseBody RolesList getRolesData() {		 
		return rolesList.buildHeaders().buildDataSource();
	}
	
}
