package control;

import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.AfterClass;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.uk.control.HomeController;

import general.BaseTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class RoutingTest extends BaseTest {
	
    @Autowired protected WebApplicationContext wac;
	
	private MockMvc mockMvc;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private Model model;
	private RequestMappingHandlerAdapter handler;
	private HandlerMethod handlerMethod;
	private Method method; 
	private HomeController homeController;
	
	private String appName = "/web";
	
	@Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }
	
	@Test
	@Ignore
	public void setUp(){		 
		homeController = new HomeController();
		handler = new RequestMappingHandlerAdapter();
		try {
			method = homeController.getClass().getMethod("home", model.getClass());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		handlerMethod = new HandlerMethod(homeController, method);
		 
		request.setRequestURI("/");
		request.setMethod("GET");
		try {
			handler.handle(request, response, handlerMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<String> hNames = response.getHeaderNames();
		for(String s : hNames){
			System.out.println(s+": "+response.getHeader(s));
		}
	}

    @Test
    @Ignore
    public void language() throws Exception {
    	response = mockMvc.perform(get(appName+"/en/setlang?lang=es"))
                .andExpect(status().isMovedTemporarily())
                .andReturn().getResponse();
    	System.out.println(response.getRedirectedUrl());
        response = mockMvc.perform(get(response.getRedirectedUrl().replace(appName, "")))
                .andExpect(status().isOk())
                .andExpect(view().name("html/home"))
                .andReturn().getResponse();
        
    	response = mockMvc.perform(get(appName+"/en/blog/setlang?lang=es"))
                .andExpect(status().isMovedTemporarily())
                .andReturn().getResponse();
    	System.out.println(response.getRedirectedUrl());
        response = mockMvc.perform(get(response.getRedirectedUrl().replace(appName, "")))
                .andExpect(status().isOk())
                .andExpect(view().name("html/blog/home"))
                .andReturn().getResponse();
        
        response = mockMvc.perform(get(appName+"/en/shop/setlang?lang=es"))
                .andExpect(status().isMovedTemporarily())
                .andReturn().getResponse();
    	System.out.println(response.getRedirectedUrl());
        response = mockMvc.perform(get(response.getRedirectedUrl().replace(appName, "")))
                .andExpect(status().isOk())
                .andExpect(view().name("html/shop/home"))
                .andReturn().getResponse();
    }

}
