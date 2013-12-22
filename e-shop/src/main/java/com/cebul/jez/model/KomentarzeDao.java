package com.cebul.jez.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.Komentarz;
import com.cebul.jez.entity.User;

/**
 * umożliwia pobieranie oraz utrwalanie obiektów Komentarz z i do bazy danych
 * @author Mateusz Cebularz
 *
 */
@Repository
public class KomentarzeDao extends Dao {

	/**
	 * pobiera komentarze jakie otrzymał dany uzytkownik 
	 * @param u uzytkownik
	 * @return lista otrzymanych komentarzy
	 */
	public List<Komentarz> pobierzOtrzymaneKomentarze(User u)
	{
		Session session = getSessionFactory();
		List<Komentarz> kom = new ArrayList<Komentarz>();
		kom = session.createCriteria(Komentarz.class).add(Restrictions.eq("odbiorca", u)).list();
		System.out.println(kom.size());
		return kom;
	}
	/**
	 * pobiera komentarze jakie wystawił dany uzytkownik
	 * @param u użytkownik
	 * @return lista wystawionych komentarzy
	 */
	public List<Komentarz> pobierzWystawioneKomentarze(User u)
	{
		Session session = getSessionFactory();
		List<Komentarz> kom = new ArrayList<Komentarz>();
		kom = session.createCriteria(Komentarz.class).add(Restrictions.eq("nadawca", u)).list();
		System.out.println(kom.size());
		return kom;
	}
}
