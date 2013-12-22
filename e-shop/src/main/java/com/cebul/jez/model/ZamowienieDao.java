package com.cebul.jez.model;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.DokumentZamowienia;

import com.cebul.jez.entity.Komentarz;
import com.cebul.jez.entity.Platnosc;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.User;
import com.cebul.jez.entity.Zamowienie;

/**
 * umożliwia pobieranie oraz utrwalanie obiektów Zamowienie z i do bazy danych
 * @author Mateusz
 *
 */
@Repository
public class ZamowienieDao extends Dao 
{
	/**
	 * pobiera dostepne płatności z bazy danych
	 * @return lista dostepnych płatności
	 */
	public List<Platnosc> getPlatnosci()
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Platnosc");
		List<Platnosc> result = query.list();
		return result;
	}
	/**
	 * pobiera dostepne dokumenty z bazy danych
	 * @return lista dostepnyc dokumentów
	 */
	public List<DokumentZamowienia> getDokumenty()
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM DokumentZamowienia");
		List<DokumentZamowienia> result = query.list();
		return result;
	}
	/**
	 * pobiera obiekt platności na podstawie nazwy
	 * @param s nazwa płatności
	 * @return obiekt Platnosc
	 */
	public Platnosc getPlatnoscLike(String s)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Platnosc WHERE rodzajPlatnosci = :p ")
				.setParameter("p", s);
		List<Platnosc> result = query.list();
		return result.get(0);
	}
	/**
	 * pobiera obiekt dokumeny na podstawie nazwy
	 * @param s nazwa dokumentu
	 * @return obiekt DokumentZamowienia
	 */
	public DokumentZamowienia getDokumentLike(String s)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM DokumentZamowienia WHERE nazwa = :p ")
				.setParameter("p", s);
		List<DokumentZamowienia> result = query.list();
		return result.get(0);
	}
	/**
	 * utrwala zamowienie w bazie danych
	 * @param z zamowienie które ma zostac utrwalone
	 * @return zwraca true jeśli operacja się powiedzie, w przeciwnym wypadku false
	 */
	public boolean zapiszZamowienie(Zamowienie z)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(z);
		return true;
	}
	/**
	 * zwraca produkty kótóre nie mają przydzielonego komentarza (które możne komentować dany użytkownik)
	 * @param u użytkownik
	 * @return lista produktów które moga być komentowane
	 */
	public List<Produkty> getNieKomentProd(User u)
	{
		Session session = sessionFactory.getCurrentSession();
		String sql = "FROM Zamowienie z inner join z.nabywca as us inner join fetch z.produkty as p " +
				"where us.id = :id and p.id not in (select pp.id from Komentarz k " +
				"inner join k.produkt as pp inner join k.nadawca as n WHERE n.id = :id ) ";
		System.out.println(sql);
		Query query = session.createQuery(sql).setParameter("id", u.getId());
		
		List<Object> result = new ArrayList<Object>();
		result =  query.list();
		
		List<Zamowienie> result3 = new ArrayList<Zamowienie>();
		List<Produkty> resultFinal = new ArrayList<Produkty>();

		Object[] ob;
		int index = 0;
		for(Object o : result)
		{
			ob = (Object[]) result.get(index);
			for(int i=0;i<ob.length; i++)
			{
				if(ob[i] instanceof Zamowienie)
				{
					result3.add((Zamowienie)ob[i]);
				}
			}
			index++;
		}
		for(Zamowienie z : result3)
		{
			resultFinal.addAll(z.getProdukty());
		}

		return resultFinal;
	}
	/**
	 * pobiera produkt który był kupiowny w dnaycm zamówieniu przez konkretnego uzytkownika
	 * @param u użytkownik
	 * @param idProd identyfikator produktu
	 * @return produkt z zamówienia
	 */
	public Produkty getProduktZZamowienia(User u, Integer idProd)
	{
		Session session = sessionFactory.getCurrentSession();
		String sql = "FROM Zamowienie z inner join z.nabywca as us inner join fetch z.produkty as p " +
				"where us.id = :id and p.id not in (select pp.id from Komentarz k " +
				"inner join k.produkt as pp inner join k.nadawca as n WHERE n.id = :id AND p.id = :idProd ) ";
		System.out.println(sql);
		Query query = session.createQuery(sql).setParameter("id", u.getId()).setParameter("idProd", idProd);
		
		List<Object> result = new ArrayList<Object>();
		result =  query.list();
		
		List<Zamowienie> result3 = new ArrayList<Zamowienie>();
		List<Produkty> resultFinal = new ArrayList<Produkty>();

		Object[] ob;
		int index = 0;
		for(Object o : result)
		{
			ob = (Object[]) result.get(index);
			for(int i=0;i<ob.length; i++)
			{
				if(ob[i] instanceof Zamowienie)
				{
					result3.add((Zamowienie)ob[i]);
				}
			}
			index++;
		}
		for(Zamowienie z : result3)
		{
			resultFinal.addAll(z.getProdukty());
		}
		
		if(resultFinal .size() > 0)
			return resultFinal.get(0);
		
		return null;
	}
	/**
	 * dodaje koemtarz do konkretnrgo produktu 
	 * @param idProduktu identyfikator produktu
	 * @param komentarz komentarz
	 * @param ocena ocena jaką wystawił użytkonik podczas dodawania komentarza
	 * @param nadawca użytkownik który wystawia komentarz
	 * @return zwrac atrue jesli operacja powiedzie się
	 */
	public boolean dodajKomentarz(Integer idProduktu, String komentarz, Integer ocena, User nadawca)
	{
		Session session = sessionFactory.getCurrentSession();
		Produkty p = (Produkty) session.get(Produkty.class, idProduktu);
		Komentarz k = new Komentarz(komentarz, ocena, nadawca, p.getUser(), p);
		session.save(k);
		
		return true;
	}
}
