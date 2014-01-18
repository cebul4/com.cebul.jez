package com.cebul.jez.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import com.cebul.jez.entity.Kategoria;

/**
 * umożliwia pobieranie oraz utrwalanie obiektów Komentarz z i do bazy danych
 * @author Mateusz
 *
 */
@Repository
public class KategorieDao extends Dao
{
	/**
	 * pobiera listę kategori oznaczonych jako główne
	 * @return lista kategorii głownych
	 */
	public List<Kategoria> getMainKategory()
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Kategorie WHERE IdParentKat is null").
				addEntity(Kategoria.class);
		List<Kategoria> result = query.list();
		return result;
		
	}
	/**
	 * pobiera kategorię główną dla podanej podkategorii
	 * @param podkategoria 
	 * @return zwraca kategorie główną
	 */
	public Kategoria getMainKategory(Kategoria podkategoria)
	{
		Session session = getSessionFactory();
		Query query = session.createQuery("from Kategoria WHERE id = :idKat")
				.setParameter("idKat", podkategoria.getId() );
		Kategoria result = (Kategoria) query.list().get(0);
		return result;
	}
	/**
	 * pobiera podkategorie dla kategori głównej
	 * @param parent identyfiaktor kategorii głównej
	 * @return lista podkategorii
	 */
	public List<Kategoria> getPodKategory(Integer parent)
	{
		
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Kategorie WHERE IdParentKat = :parent").
				addEntity(Kategoria.class).setParameter("parent", parent);
			
		List<Kategoria> result = query.list();
	
		return result;
	}
	/**
	 * pobiera obiekt kategorii na podstwie podanego identyfiaktora
	 * @param id identyfikator kategorii
	 * @return obiekt Kategorii
	 */
	public Kategoria getKategory(Integer id)
	{
		Session session = getSessionFactory();
		return (Kategoria)session.get(Kategoria.class, id);
	}
	public List<String> getKategoryString(Integer id)
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Kategorie WHERE Id = :id").addEntity(Kategoria.class).setParameter("id", id);
		List<String> result = query.list();
		
		return result;
		
	}
	/**
	 * dodaje kategorię do bazy
	 * @param k kategoria która ma być utrwalona
	 * @return zwraca true jeśli operacja się powiedzie, w przeciwnym wypaku false
	 */
	public boolean addKategoria(Kategoria k)
	{
		Session session = sessionFactory.getCurrentSession();
		boolean exsist = isExist(k);
		if(!exsist)
		{
			
			session.save(k);
			return true;
		}
		return false;
	}
	/**
	 * sprawdza czy podana kategoria isteje w bazie danych
	 * @param k kategoria która ma zostać sprawdzona
	 * @return zwraca true jeśli kategoria istenieje w bazie dnaych, false w przeciwnym wypadku
	 */
	public boolean isExist(Kategoria k)
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Kategorie WHERE nazwa = :catname ").
				addEntity(Kategoria.class).setParameter("catname", k.getNazwa());
		List<Kategoria> result = query.list();
		if(!result.isEmpty())
		{
			return true;
		}
		return false;
	}
	/**
	 * aktualizuje utrwalone w bazie dnaych obiekt kategorii
	 * @param kat obiekt który ma zostac zaktualizowany w bazie dnacyh
	 * @return zwraca tru jeśli aktualizacja się powiedzie, w przeciwnym wypadku false
	 */
	public boolean updateCategory(Kategoria kat)
	{
		Session session = getSessionFactory();
		
		
		Kategoria updateCat = (Kategoria) session.get(Kategoria.class, kat.getId());
		
		updateCat.setNazwa(kat.getNazwa());
		updateCat.setParentKategory(kat.getParentKategory());
		
		session.update(updateCat);
		
		return true;
	}	
	
	public List<Kategoria> getAll()
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Kategorie").
				addEntity(Kategoria.class);
		List<Kategoria> result = query.list();
		return result;
		
	}
	
}
