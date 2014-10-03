package control;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.uk.login.dao.UserDao;

import general.BaseTest;

public class ProfileTest extends BaseTest {
	
	@Autowired protected WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
    private SecurityContext securityContext;
    private MockHttpServletResponse response;
		
	@Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
        this.securityContext = new SecurityContextImpl();
    }
    
	@Test
	@Ignore
    public void signedIn() throws Exception {
        MockHttpSession session = new MockHttpSession();
        Authentication auth     = this.getPrincipal("user","pass");
        this.securityContext.setAuthentication(auth);
        
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, this.securityContext);
        MockHttpServletRequestBuilder builder = get("/sec").session(session);
        this.response = this.mockMvc.perform(builder).andExpect(status().isOk()).andReturn().getResponse();
        System.out.println(response.getContentAsString());
    }
    
    protected Authentication getPrincipal(String username,String password) {   
    	AuthenticationManager auth    = (AuthenticationManager)this.webApplicationContext.getBean("authenticationManager");
    	BCryptPasswordEncoder encoder = (BCryptPasswordEncoder)this.webApplicationContext.getBean("bCryptPasswordEncoder");
    	String encodedPassword        = ((UserDao)this.webApplicationContext.getBean("userDao")).findPasswordByUsername(username);
    	
    	password = encoder.matches(password, encodedPassword) ? encodedPassword : password;
    	
    	return auth.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
