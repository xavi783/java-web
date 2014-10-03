package control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import general.BaseTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.mongodb.util.JSON;

public class CartControllerTest extends BaseTest {
	
	@Autowired protected WebApplicationContext wac;	
	private MockMvc mockMvc;
	private MockHttpServletResponse response;
	
	@Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }
	
	@Test
	@Ignore
	public void add(){
		Map<String,String> item = new HashMap<String,String>();
		item.put("_id", "javascript-and-json-essentials");
		item.put("quant", "2"); 
		
		try {
			response = mockMvc.perform(post("/en/shop/addItem")
									   .accept(MediaType.APPLICATION_JSON)
									   .contentType(MediaType.APPLICATION_JSON)
									   .content(JSON.serialize(item).getBytes()))
			        .andExpect(status().isOk())
			        .andReturn().getResponse();
			System.out.println(response.getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	@Ignore
	public void remove(){
		Map<String,String> item = new HashMap<String,String>();
		item.put("_id", "javascript-and-json-essentials");
		item.put("quant", "2"); 
		
		try {
			response = mockMvc.perform(post("/en/shop/removeItem")
									   .accept(MediaType.APPLICATION_JSON)
									   .contentType(MediaType.APPLICATION_JSON)
									   .content(JSON.serialize(item).getBytes()))
			        .andExpect(status().isOk())
			        .andReturn().getResponse();
			System.out.println(response.getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
