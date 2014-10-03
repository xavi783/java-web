package com.uk.login.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.uk.database.DBUtils;
import com.uk.login.LoginDetail;
import com.uk.login.dao.LoginDetailsDao;

@Repository
public class LoginDetailsDaoImpl implements LoginDetailsDao {

	@Autowired private DBUtils<Session> dao;	
	Logger logger = LoggerFactory.getLogger(LoginDetailsDaoImpl.class);
	
	@Override
	public DBUtils<Session> getDao() {
		return dao;
	}

	@Override
	public void setDao(DBUtils<Session> dao) {
		this.dao = dao;
	}
	
	@Override
	@Transactional
	public List<?> getAllRoles() {
        return dao.getSession()
        	   .createQuery("FROM LoginDetail")
        	   .list();
	}
	
	@Override
	@Transactional
	public String getLandingByRole(String role) {
		String landing = null;
		if(role!=null && role!=""){
	        List<?> list = dao.getSession()
	        			   .createQuery("FROM LoginDetail WHERE authority=:role")
	        			   .setParameter("role", role)
	        			   .list();
	        if(!list.isEmpty()){landing = (String)list.get(0);}
		}
		return landing;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String getLandingByRoles(Collection<? extends String> roles) {
		String landing = null;
		NavigableMap<Integer,String> order = new TreeMap<Integer,String>();
		if(roles!=null && !roles.isEmpty()){
	        List<LoginDetail> list = (List<LoginDetail>)dao.getSession()
	        			   .createQuery("FROM LoginDetail WHERE authority IN :roles")
	        			   .setParameterList("roles", roles)
	        			   .list();
	        for(LoginDetail d:list){
	        	order.put(d.getPriority(), d.getSuccessURL());
	        }	        
	        landing = order.descendingMap().lastEntry().getValue();
		}
		return landing;
	}

	@Override
	@Transactional
	public LoginDetailsDao setLandingByRole(String role, String Landing, int priority) {
		LoginDetail loginDetail = new LoginDetail();
		loginDetail.setAuthority(role);
		loginDetail.setSuccessURL(Landing);
		loginDetail.setPriority(priority);
		dao.getSession().save(loginDetail);
		return this;
	}
}
