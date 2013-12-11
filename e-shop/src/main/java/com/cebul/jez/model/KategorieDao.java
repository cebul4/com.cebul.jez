package com.cebul.jez.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.Kategoria;

@Repository
public class KategorieDao 
{
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSessionFactory()
	{
		return sessionFactory.getCurrentSession();
	}
	
	private void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public List<Kategoria> getMainKategory()
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Kategorie WHERE IdParentKat is null").
				addEntity(Kategoria.class);
		List<Kategoria> result = query.list();
		return result;
		
	}
	public Kategoria getMainKategory(Kategoria podkategoria)
	{
		Session session = getSessionFactory();
		Query query = session.createQuery("from Kategoria WHERE id = :idKat")
				.setParameter("idKat", podkategoria.getId() );
		Kategoria result = (Kategoria) query.list().get(0);
		return result;
	}
	public List<Kategoria> getPodKategory(Integer parent)
	{
		
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Kategorie WHERE IdParentKat = :parent").
				addEntity(Kategoria.class).setParameter("parent", parent);
			
		List<Kategoria> result = query.list();
	
		return result;
	}
	public Kategoria getKategory(Integer id)
	{
		Session session = getSessionFactory();
		return (Kategoria)session.get(Kategoria.class, id);
	}
	
}
