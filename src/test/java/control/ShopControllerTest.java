package control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import general.BaseTest;

public class ShopControllerTest extends BaseTest {

    @Autowired protected WebApplicationContext wac;	
	private MockMvc mockMvc;
	//private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	private Boolean printResponse = false;
	
	private String
	home     = "html/shop/home",
	category = "html/shop/search",
	product  = "html/shop/article",
	edition  = "html/shop/edition/";
	
	@Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    @Ignore
	public void getHome() throws Exception {
		response = mockMvc.perform(get("/en/shop/"))
		        .andExpect(status().isOk())
		        .andExpect(view().name(home))
		        .andReturn().getResponse();
		/*
		response = mockMvc.perform(get("/en/shop/"))
		        .andExpect(status().isOk())
		        .andExpect(view().name(home))
		        .andReturn().getResponse();
		
		mockMvc.perform(get("/shop"))
    		.andExpect(status().isNotFound());
		mockMvc.perform(get("/shop/"))
        	.andExpect(status().isNotFound());
		mockMvc.perform(get("/es/es/shop/"))
			.andExpect(status().isNotFound());
		*/
        if (printResponse) {
        	System.out.println(response.getContentAsString());
		}
	}
    
    @Test
    @Ignore
	public void getCategory() throws Exception {
		response = mockMvc.perform(get("/en/shop/category/web-development/"))
		        .andExpect(status().isOk())
		        .andExpect(view().name(category))
		        .andReturn().getResponse();
		
		mockMvc.perform(get("/shop/category/web-development/"))
			.andExpect(status().isNotFound());
		mockMvc.perform(get("/es/shop/category/web-development"))
			.andExpect(status().isNotFound());
		
		if (printResponse) {
        	System.out.println(response.getContentAsString());
		}
	}

    @Test
    @Ignore
	public void getCategoryTest() throws Exception {
    	mockMvc.perform(get("/en/shop/category/test/"))
    	        .andExpect(status().isNotFound());
	}
    
    @Test
    @Ignore
	public void getCategoryAll() throws Exception {
    	response = mockMvc.perform(get("/en/shop/category/all-books/"))
    	        .andExpect(status().isOk())
    	        .andExpect(view().name(category))
    	        .andReturn().getResponse();
		
		if (printResponse) {
        	System.out.println(response.getContentAsString());
		}
	}

    @Test
    @Ignore
	public void getProduct() throws Exception {
		response = mockMvc.perform(get("/en/shop/category/web-development/java-ee-7-first-look"))
		        .andExpect(status().isOk())
		        .andExpect(view().name(product))
		        .andReturn().getResponse();
		
		mockMvc.perform(get("/shop/category/web-development/java-ee-7-first-look"))
			.andExpect(status().isNotFound());
		mockMvc.perform(get("/en/shop/category/web-development/java-ee-7-first-look/"))
			.andExpect(status().isOk());
		mockMvc.perform(get("/shop/category/web-development/esto-no-lleva-a-ningun-lado"))
			.andExpect(status().isNotFound());
		
		if (printResponse) {
        	System.out.println(response.getContentAsString());
		}
	}
    
    @Test
    @Ignore
	public void getMenuBar() throws Exception {    	
		response = mockMvc.perform(post("/en/shop/categories/")).andReturn().getResponse();		
		if (true) {
        	System.out.println(response.getContentAsString());
		}
		response = mockMvc.perform(post("/es/shop/categories/")).andReturn().getResponse();		
		if (true) {
        	System.out.println(response.getContentAsString());
		}
	}

	public String getEdition() {
		return edition;
	}
    
    
	
}
