package com.uk.login.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uk.database.DBUtils;
import com.uk.login.Authorities;
import com.uk.login.User;
import com.uk.login.dao.AuthoritiesDao;

public class AuthoritiesDaoImpl implements AuthoritiesDao {	
			
	@Autowired private DBUtils<Session> dao;	
	
	/* (non-Javadoc)
	 * @see com.uk.logging.dao.impl.AuthoritiesDao#getDao()
	 */
	@Override
	public DBUtils<Session> getDao() {
		return dao;
	}
	/* (non-Javadoc)
	 * @see com.uk.logging.dao.impl.AuthoritiesDao#setDao(com.uk.database.DatabaseUtils)
	 */
	@Override
	public void setDao(DBUtils<Session> dao){
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.uk.logging.dao.impl.AuthoritiesDao#getAuthoritiesByUsername(com.uk.logging.User)
	 */
	@Override
	@Transactional
	public List<?> getAuthoritiesByUsername(User user) {
        return dao.getSession()
                .createQuery("FROM Authorities WHERE username=:username")
                .setParameter("username", user.getUsername())
                .list();
    }

	/* (non-Javadoc)
	 * @see com.uk.logging.dao.impl.AuthoritiesDao#getAuthoritiesByRole(java.lang.String)
	 */
	@Override
	@Transactional
	public List<?> getAuthoritiesByRole(String role) {
        return dao.getSession()
                .createQuery("FROM Authorities WHERE authority=:authority")
                .setParameter("authority", role)
                .list();
    }

	/* (non-Javadoc)
	 * @see com.uk.logging.dao.impl.AuthoritiesDao#save(com.uk.logging.Authorities)
	 */
	@Override
	@Transactional
	public void save(Authorities authority){
		dao.getSession().save(authority);
	}
 
	/* (non-Javadoc)
	 * @see com.uk.logging.dao.impl.AuthoritiesDao#update(com.uk.logging.Authorities)
	 */
	@Override
	@Transactional
	public void update(Authorities authority){
		dao.getSession().update(authority);
	}
 
	/* (non-Javadoc)
	 * @see com.uk.logging.dao.impl.AuthoritiesDao#delete(com.uk.logging.Authorities)
	 */
	@Override
	@Transactional
	public void delete(Authorities authority){
		dao.getSession().delete(authority);
	}

	@Override
	public String toString() {
		return "AuthoritiesDaoImpl: {dao: " + dao + "}";
	}	
	
}
