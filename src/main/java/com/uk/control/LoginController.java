package com.uk.control;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.uk.login.Authorities;
import com.uk.login.LoginDetail;
import com.uk.login.User;
import com.uk.login.dao.AuthoritiesDao;
import com.uk.login.dao.LoginDetailsDao;
import com.uk.login.dao.UserDao;

@Controller
public class LoginController {
	
	Logger logger = LoggerFactory.getLogger(LoginController.class); 
	
	@Autowired private UserDao userDao;
	@Autowired private AuthoritiesDao authDao;
	@Autowired private LoginDetail loginDetail;
	@Autowired private LoginDetailsDao loginDetailsDao;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired @Qualifier("authenticationManager") private AuthenticationManager authenticationManager;
	
	@RequestMapping(value = {"/login","**/login"}, method = RequestMethod.POST, consumes="application/json", produces="application/json")
    public @ResponseBody LoginDetail login(@RequestBody User user) {
    	return userAuthentication(user);
    }
	
	@RequestMapping(value = {"/signin","**/signin"}, method = RequestMethod.POST, consumes="application/json", produces="application/json")
    public @ResponseBody LoginDetail signin(@RequestBody User user) {
    	user.setEnabled(true);
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	userDao.save(user);
    	authDao.save(new Authorities(user.getUsername(),"USER"));
    	return userAuthentication(user);
    }	
	
	public LoginDetail userAuthentication(User user){
		String encodedPassword = userDao.findPasswordByUsername(user.getUsername());
		if (bCryptPasswordEncoder.matches(user.getPassword(), encodedPassword)){
			user.setPassword(encodedPassword);
		}
    	try {
        	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            List<String> authorities = new ArrayList<String>();
            for(GrantedAuthority auth:authentication.getAuthorities()){
            	authorities.add(auth.getAuthority());
            };
            if(authentication.isAuthenticated()){
            	loginDetail.setLogged(true);
            	loginDetail.setSuccessURL(loginDetailsDao.getLandingByRoles(authorities));
            }else{
            	loginDetail.setLogged(false);
            }
        } catch (AuthenticationException ex) {
        	loginDetail.setLogged(false);
        }
    	loginDetail.setUsername(user.getUsername());    	
        return loginDetail;
	}
	
}
