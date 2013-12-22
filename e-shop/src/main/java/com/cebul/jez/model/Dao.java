package com.cebul.jez.model;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * klasa bedąca bazą dla reszty klas DAO
 * umozliwia pobranie referencji do obiektu sesji hibernate
 * wykorzystuje wstrzykiwnaie zależności
 * wstrzykuje SessionFactory, bean odpowiedzialny za zarządzanie sesją
 * @author Mateusz
 *
 */
@Repository
public abstract class Dao 
{
	@Autowired
	protected SessionFactory sessionFactory;

	/**
	 * pobiera otwartą sesję
	 * @return sesja hibernate
	 */
	protected Session getSessionFactory()
	{
		return sessionFactory.getCurrentSession();
	}
	/**
	 * ustawia fabrykę sesji
	 * @param sessionFactory fabryka sesji
	 */
	protected void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
}
