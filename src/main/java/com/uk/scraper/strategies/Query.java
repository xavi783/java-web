package com.uk.scraper.strategies;

public interface Query<U,T> {
	
	public T perform();
	
	public T perform(U subject);
	
	public U getSubject();
	
	public Query<U,T> setSubject(U subject);

	public T build();

}
