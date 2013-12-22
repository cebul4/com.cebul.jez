package com.cebul.jez.model;

import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.Zdjecie;

/**
 * umożliwia pobieranie oraz utrwalanie obiektów Zdjecie z i do bazy danych
 * @author Mateusz
 *
 */
@Repository
public class ZdjecieDao extends Dao
{
	
	/**
	 * zapisuje zdjecie w bazie dnaych
	 * @param zdj zdjecie przeznaczone do zapisu
	 * @return zwraca true jesli operacja sie powiedzie, false w przeciwnym wypadku
	 */
	public boolean saveZdjecie(Zdjecie zdj)
	{
		try{
			Session session = getSessionFactory();
			session.save(zdj);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	/**
	 * pobiera zdjęcie z bazy danych o podanym identyfiaktorze
	 * @param id identyfikator zdjęcia
	 * @return obiekt Zdjecie
	 */
	public Zdjecie getZdjecie(Integer id)
	{
		Zdjecie zdj = new Zdjecie();
		Session session = getSessionFactory();
		zdj = (Zdjecie) session.get(Zdjecie.class, id);
		return zdj;
		
	}
}
