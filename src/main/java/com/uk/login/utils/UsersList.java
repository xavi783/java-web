package com.uk.login.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uk.login.Authorities;
import com.uk.login.User;
import com.uk.login.dao.AuthoritiesDao;
import com.uk.login.dao.UserDao;

@Component
public class UsersList {
	
	private @Autowired UserDao userDao;
	private @Autowired AuthoritiesDao authDao;
	private List<ListHeaders> columns;
	private List<UserListDataSource> datasource;
	private final String[] headerNames = {"username", "email", "authorities"};
	
	public UsersList() {
	}

	public UsersList(UserDao userDao, AuthoritiesDao authDao,
			List<ListHeaders> columns, List<UserListDataSource> datasource) {
		this.userDao = userDao;
		this.authDao = authDao;
		this.columns = columns;
		this.datasource = datasource;
	}
	
	@JsonIgnore
	public UserDao getUserDao() {
		return userDao;
	}

	@JsonIgnore
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@JsonIgnore
	@Autowired
	public AuthoritiesDao getAuthDao() {
		return authDao;
	}

	@JsonIgnore
	public void setAuthDao(AuthoritiesDao authDao) {
		this.authDao = authDao;
	}

	public List<ListHeaders> getColumns() {
		return columns;
	}

	public void setColumns(List<ListHeaders> columns) {
		this.columns = columns;
	}

	public List<UserListDataSource> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<UserListDataSource> datasource) {
		this.datasource = datasource;
	}

	public UsersList buildHeaders(){
		columns = new ArrayList<ListHeaders>();
		for(String s : headerNames){
			columns.add(new ListHeaders(s,s,true));
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public UsersList buildDataSource(){
		datasource = new ArrayList<UserListDataSource>();
		StringBuilder s = new StringBuilder();
		List<User> allUsers = userDao.getAllUsers();
		for(User u : allUsers){			
			s.delete(0, s.length());
			List<Authorities> autList = (List<Authorities>)authDao.getAuthoritiesByUsername(u);
			for(Authorities a : autList){
				s.append(a.getAuthority()+", ");
			}
			datasource.add(this.new UserListDataSource(u.getUsername(),u.getEmail(),s.substring(0, s.length()-2)));
		}
		return this;
	}
	
	public class UserListDataSource{
		private String username, email, authorities;
		
		public UserListDataSource() {
		}
		public UserListDataSource(String username, String email, String authorities) {
			this.username = username;
			this.email = email;
			this.authorities = authorities;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getAuthorities() {
			return authorities;
		}
		public void setAuthorities(String authorities) {
			this.authorities = authorities;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((authorities == null) ? 0 : authorities.hashCode());
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result
					+ ((username == null) ? 0 : username.hashCode());
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
			UserListDataSource other = (UserListDataSource) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (authorities == null) {
				if (other.authorities != null)
					return false;
			} else if (!authorities.equals(other.authorities))
				return false;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			if (username == null) {
				if (other.username != null)
					return false;
			} else if (!username.equals(other.username))
				return false;
			return true;
		}
		private UsersList getOuterType() {
			return UsersList.this;
		}
		@Override
		public String toString() {
			return "{username: " + username + ", email: "
					+ email + ", authorities:" + authorities + "}";
		}		
	}
}
