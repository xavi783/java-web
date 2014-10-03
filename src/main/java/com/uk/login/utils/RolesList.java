package com.uk.login.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uk.login.LoginDetail;
import com.uk.login.dao.LoginDetailsDao;

public class RolesList {
	private @Autowired LoginDetailsDao loginDetailsDao;
	private List<ListHeaders> columns;
	private List<RoleListDataSource> datasource;
	private final String[] headerNames = {"authority", "successURL", "priority"};
		
	public RolesList() {
	}
	
	public RolesList(LoginDetailsDao loginDetailsDao,
			List<ListHeaders> columns, List<RoleListDataSource> datasource) {
		this.loginDetailsDao = loginDetailsDao;
		this.columns = columns;
		this.datasource = datasource;
	}
	
	@JsonIgnore
	public LoginDetailsDao getLoginDetailsDao() {
		return loginDetailsDao;
	}
	
	@JsonIgnore
	public void setLoginDetailsDao(LoginDetailsDao loginDetailsDao) {
		this.loginDetailsDao = loginDetailsDao;
	}
	
	public List<ListHeaders> getColumns() {
		return columns;
	}
	
	public void setColumns(List<ListHeaders> columns) {
		this.columns = columns;
	}
	
	public List<RoleListDataSource> getDatasource() {
		return datasource;
	}
	
	public void setDatasource(List<RoleListDataSource> datasource) {
		this.datasource = datasource;
	}
	
	public RolesList buildHeaders(){
		columns = new ArrayList<ListHeaders>();
		for(String s : headerNames){
			columns.add(new ListHeaders(s,s,true));
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public RolesList buildDataSource(){
		datasource = new ArrayList<RoleListDataSource>();
		List<LoginDetail> allRoles = (List<LoginDetail>)loginDetailsDao.getAllRoles();		
		for(LoginDetail a : allRoles){
			datasource.add(this.new RoleListDataSource(a.getAuthority(),a.getSuccessURL(),Integer.toString(a.getPriority())));
		}
		return this;
	}
	
	public class RoleListDataSource{
		private String authority, successURL, priority;
		
		public RoleListDataSource() {
		}
		public RoleListDataSource(String authority, String successURL,
				String priority) {
			this.authority = authority;
			this.successURL = successURL;
			this.priority = priority;
		}
		public String getAuthority() {
			return authority;
		}
		public void setAuthority(String authority) {
			this.authority = authority;
		}
		public String getSuccessURL() {
			return successURL;
		}
		public void setSuccessURL(String successURL) {
			this.successURL = successURL;
		}
		public String getPriority() {
			return priority;
		}
		public void setPriority(String priority) {
			this.priority = priority;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((authority == null) ? 0 : authority.hashCode());
			result = prime * result
					+ ((priority == null) ? 0 : priority.hashCode());
			result = prime * result
					+ ((successURL == null) ? 0 : successURL.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RoleListDataSource other = (RoleListDataSource) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (authority == null) {
				if (other.authority != null)
					return false;
			} else if (!authority.equals(other.authority))
				return false;
			if (priority == null) {
				if (other.priority != null)
					return false;
			} else if (!priority.equals(other.priority))
				return false;
			if (successURL == null) {
				if (other.successURL != null)
					return false;
			} else if (!successURL.equals(other.successURL))
				return false;
			return true;
		}
		private RolesList getOuterType() {
			return RolesList.this;
		}
		@Override
		public String toString() {
			return "{authority: " + authority
					+ ", successURL: " + successURL + ", priority: " + priority
					+ "}";
		}
		
	}

}
