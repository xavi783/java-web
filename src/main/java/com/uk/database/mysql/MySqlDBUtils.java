package com.uk.database.mysql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uk.database.DBUtils;

public class MySqlDBUtils implements DBUtils<Session> {

	@Autowired private SessionFactory sessionFactory;	
	private Session session;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/* (non-Javadoc)
	 * @see com.uk.database.mysql.DBUtils#getSession()
	 */
	@Override
	public Session getSession(){
		try{
			session = sessionFactory.getCurrentSession();
		}catch(Exception e){
			session = sessionFactory.openSession();
		}
		return session;
	}

	@Override
	public String toString() {
		return "DatabaseUtils: {sessionFactory=" + sessionFactory + ", session="
				+ session + "}";
	}
}
