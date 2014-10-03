package com.uk.control;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;

@Controller
public class TestController extends SectionController {
	
	@RequestMapping(value = "/sec", method = {GET,POST}, produces="text/plain")
	public @ResponseBody String edition(Model model, HttpServletRequest request, HttpServletResponse response) {
		SecurityContext secContext = (SecurityContext)request.getSession().getAttribute(SPRING_SECURITY_CONTEXT_KEY);
		Authentication auth = secContext.getAuthentication();
		User user = (User)auth.getPrincipal();
		return user.toString();
	}

}
