package com.uk.login;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.uk.login.pk.AuthoritiesPK;

@Entity
@Table(name="authorities")
public class Authorities implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId private AuthoritiesPK authPK;
	
	public Authorities(){
	}
	
	public Authorities(AuthoritiesPK authPK) {
		this.authPK = authPK;
	}
	
	public Authorities(String username, String authority) {
		this.authPK = new AuthoritiesPK(username,authority);
	}

	public AuthoritiesPK getAuthPK() {
		return authPK;
	}
	public void setAuthPK(AuthoritiesPK authPK) {
		this.authPK = authPK;
	}	
	
	public String getUsername() {
		return this.authPK.getUsername();
	}
	public void setUsername(String username) {
		this.authPK.setUsername(username);
	}
	
	public String getAuthority() {
		return this.authPK.getAuthority();
	}
	public void setAuthority(String authority) {
		this.authPK.setAuthority(authority);
	}
	
	@Override
	public String toString() {
		return "{authPK: " + authPK + "}";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authPK == null) ? 0 : authPK.hashCode());
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
		Authorities other = (Authorities) obj;
		if (authPK == null) {
			if (other.authPK != null)
				return false;
		} else if (!authPK.equals(other.authPK))
			return false;
		return true;
	}
}
