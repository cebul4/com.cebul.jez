package com.cebul.jez.model;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class Dao 
{
	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSessionFactory()
	{
		return sessionFactory.getCurrentSession();
	}
	
	protected void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
}
