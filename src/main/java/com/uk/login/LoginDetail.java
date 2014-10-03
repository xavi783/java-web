package com.uk.login;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component
@Scope("session")
@Table(name="roles")
public class LoginDetail implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	private boolean logged;
	private String username;
	private String authority, successURL;
	private int priority;
	
    public LoginDetail() {
	}
    
	public LoginDetail(boolean logged, String username, String authority, String successURL, int priority) {
		this.logged = logged;
		this.username = username;
		this.authority = authority;
		this.successURL = successURL;
		this.priority = priority;
	}	
	
	@Transient
	public boolean isLogged() {
        return logged;
    }
	public void setLogged(boolean logged) {
        this.logged = logged;
    }		
	
	@Transient
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Id
	@Column(name="authority")
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Column(name="successURL")
	public String getSuccessURL() {
		return successURL;
	}
	public void setSuccessURL(String successURL) {
		this.successURL = successURL;
	}
	
	@Column(name="priority")
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "LoginDetail [logged=" + logged + ", username=" + username
				+ ", authority=" + authority + ", successURL=" + successURL
				+ ", priority=" + priority + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + (logged ? 1231 : 1237);
		result = prime * result + priority;
		result = prime * result
				+ ((successURL == null) ? 0 : successURL.hashCode());
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
		LoginDetail other = (LoginDetail) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (logged != other.logged)
			return false;
		if (priority != other.priority)
			return false;
		if (successURL == null) {
			if (other.successURL != null)
				return false;
		} else if (!successURL.equals(other.successURL))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}