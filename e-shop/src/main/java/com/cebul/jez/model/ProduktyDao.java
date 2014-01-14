package com.cebul.jez.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.webflow.engine.History;

import com.cebul.jez.entity.Hist_Wyszuk;
import com.cebul.jez.entity.Kategoria;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.ProduktyKupTeraz;
import com.cebul.jez.entity.ProduktyLicytuj;
import com.cebul.jez.entity.User;
import com.cebul.jez.entity.Zamowienie;
import com.cebul.jez.entity.Zdjecie;

/**
 * umożliwia pobieranie oraz utrwalanie obiektów Produkty z i do bazy danych
 * @author Mateusz
 *
 */
@Repository
public class ProduktyDao extends Dao
{
	/**
	 * pobiera nazwy produktów z bazy danych na podstawie słowa kluczowego "like"
	 * @param like słowo kluczowe
	 * @return lista nazw produktów pasujacych do wzorca
	 */
	public List<String> getProduktyLike(String like)
	{
		String l = "%"+like+"%";
		Session session = getSessionFactory();
		Query query = session.createQuery("select p.nazwa from Produkty p where " +
				" p.nazwa LIKE :like and  (p.id in (select i2.id from ProduktyKupTeraz i2 WHERE i2.kupiony = false) " +
				"or p.id in (select id2.id from ProduktyLicytuj id2 WHERE id2.dataZakonczenia >= CURRENT_DATE() ))  GROUP BY p.nazwa ")
				.setParameter("like", l);
		List<String> result = new ArrayList<String>();
		if(!query.list().isEmpty())
			result = (List<String>) query.list();
		//System.out.println(result.get(0).getNazwa());
		return result;
	}
	/**
	 * pobiera nazwy produktów z bazy danych na podstawie słowa kluczowego "like" oraz nalezących do danej kategorii
	 * @param like słowo kluczowe
	 * @param kategoria kategoria do której ma należeć produkt
	 * @return lista nazw produktów pasujacych do kryterium
	 */
	public List<String> getProduktyLike(String like, Integer kategoria)
	{
		String l = "%"+like+"%";
		Session session = getSessionFactory();
		Query query = session.createQuery("select p.nazwa from Produkty p " +
				"left join fetch p.kategorie as kat where " +
				"kat.id IN (select k.id from Kategoria k left join k.parentKategory as parentK " +
				"WHERE parentK.id = :kategoria ) and p.nazwa LIKE :like " +
				"and (p.id in (select i2.id from ProduktyKupTeraz i2 WHERE i2.kupiony = false ) " +
				"or p.id in (select id2.id from ProduktyLicytuj id2 id2.dataZakonczenia >= CURRENT_DATE() ) GROUP BY p.nazwa )  ")
				.setParameter("like", l).setParameter("kategoria", kategoria);
		List<String> result = new ArrayList<String>();
		if(!query.list().isEmpty())
			result = query.list();
		
		return result;
	}
	/**
	 * pobiera produkty pasujące do danego wzorca oraz należące do danej kategorii
	 * @param like wzorzec
	 * @param kategoria kategoria do której ma należeć wzorzec
	 * @return lista produktów
	 */
	public List<Produkty> getFullProduktyLike(String like, Integer kategoria)
	{
		String l = "%"+like+"%";
		Session session = getSessionFactory();
		
		// wpisanie do tabeli Historia wyszukiwania
		Kategoria k = (Kategoria) session.get(Kategoria.class, kategoria);
		Hist_Wyszuk hw = new Hist_Wyszuk();
		hw.setData(new Date());
		hw.setKategoria(k);
		session.save(hw);
		//////
		
		Query query = session.createQuery("from Produkty p " +
				"left join fetch p.kategorie as kat where " +
				"kat.id IN (select k.id from Kategoria k left join k.parentKategory as parentK " +
				"WHERE parentK.id = :kategoria ) and p.nazwa LIKE :like " +
				"and (p.id in (select i2.id from ProduktyKupTeraz i2 WHERE i2.kupiony = false ) " +
				"or p.id in (select id2.id from ProduktyLicytuj id2 WHERE id2.dataZakonczenia >= CURRENT_DATE() )  )  ")
				.setParameter("like", l).setParameter("kategoria", kategoria);
		List<Produkty> result = new ArrayList<Produkty>();
		if(!query.list().isEmpty())
			result = query.list();
		
		return result;
	}
	/**
	 * metoda używana do zaawansowanego wyszukowania produktów na podstawie kliku kryteriów
	 * @param like słowo kluczowe
	 * @param kategoria kategoria do której ma należeć produkt
	 * @param cenaOd minimalna cena produktu
	 * @param cenaDo maksymalna cena produktu
	 * @param kupLic okresla czy produkt ma być typu "Kup teraz" czy "Licytuj"
	 * @param podkat podkategoria do której ma należeć produkt
	 * @return lista produktów 
	 */
	public List<Produkty> getFullProduktyLike(String like, Integer kategoria, Double cenaOd, Double cenaDo, String []kupLic, Integer []podkat)
	{
		String l;
		if(like != null)
			 l = "%"+like+"%";
		else
			l = "%";
		Session session = getSessionFactory();
		
		// wpisanie do tabeli Historia wyszukiwania
		if(kategoria != null)
		{
			Kategoria k = (Kategoria) session.get(Kategoria.class, kategoria);
			Hist_Wyszuk hw = new Hist_Wyszuk();
			hw.setData(new Date());
			hw.setKategoria(k);
			session.save(hw);
		}
		//////
		
		String sql = "from Produkty p " +
				"left join fetch p.kategorie as kat where "+
				"p.nazwa LIKE :like ";
		
		if(cenaOd != null)
		{
			sql += "AND p.cena >= "+cenaOd+" ";
		}
		if(cenaDo != null)
		{
			sql += "AND p.cena <= "+cenaDo+" ";
		}
		if(podkat != null)
		{
			String tmp = "";
			for(int i=0;i<podkat.length;i++)
			{
				if(i == podkat.length-1)
				{
					tmp += podkat[i];
				}else{
					tmp += podkat[i]+",";
				}
			}
			if(podkat.length > 0)
				sql += " AND kat.id IN ("+tmp+") ";
		}else{
			if(kategoria != null && !kategoria.equals(0))
			{
				sql += " AND kat.id IN (select k.id from Kategoria k left join k.parentKategory as parentK " +
					"WHERE parentK.id = "+kategoria+" )";
			}
		}
		if(kupLic != null && kupLic.length>0)
		{
			int tmp = kupLic.length;
			System.out.println("kupLic rozmiar = "+tmp);
			if(tmp < 2)
			{
				if(kupLic[0].equals("kupTeraz"))
				{
					sql += "and p.id in (select i2.id from ProduktyKupTeraz i2 WHERE i2.kupiony = false ) ";
				}else
				{
					sql += "and p.id in (select id2.id from ProduktyLicytuj id2 WHERE id2.dataZakonczenia >= CURRENT_DATE() )   ";
				}
			}else{
				sql += "and (p.id in (select i2.id from ProduktyKupTeraz i2 WHERE i2.kupiony = false ) " +
						"or p.id in (select id2.id from ProduktyLicytuj id2 WHERE id2.dataZakonczenia >= CURRENT_DATE() )  )  ";
			}
		}else{
			sql += "and (p.id in (select i2.id from ProduktyKupTeraz i2 WHERE i2.kupiony = false ) " +
				"or p.id in (select id2.id from ProduktyLicytuj id2 WHERE id2.dataZakonczenia >= CURRENT_DATE() )  )  ";
		}
		System.out.println(sql);
		Query query = session.createQuery(sql)
				.setParameter("like", l);
		List<Produkty> result = new ArrayList<Produkty>();
		if(!query.list().isEmpty())
			result = query.list();
		
		return result;
	}
	/**
	 * pobiera produkty z bazy dnaych na podstawie słowa kluczowego
	 * @param like słowo kluczowe
	 * @return lista produktów
	 */
	public List<Produkty> getFullProduktyLike(String like)
	{
		String l = "%"+like+"%";
		Session session = getSessionFactory();
		
		// wpisanie do tabeli Historia wyszukiwania
			Hist_Wyszuk hw = new Hist_Wyszuk();
			hw.setData(new Date());
			session.save(hw);
		//////
				
		Query query = session.createQuery("from Produkty p " +
				"WHERE p.nazwa LIKE :like " +
				"and (p.id in (select i2.id from ProduktyKupTeraz i2  WHERE i2.kupiony = false ) " +
				"or p.id in (select id2.id from ProduktyLicytuj id2 WHERE id2.dataZakonczenia >= CURRENT_DATE() )  )  ")
				.setParameter("like", l);
		List<Produkty> result = new ArrayList<Produkty>();
		if(!query.list().isEmpty())
			result = query.list();
		
		return result;
	}
	/**
	 * pobiera cztery ostatnio dodane produkty
	 * @return lista produktów
	 */
	public List<Produkty> getLastFourProdukt()
	{
		Session session = getSessionFactory();
		Query query = session.createQuery("from Produkty p where " +
				" (p.id in (select pk.id from ProduktyKupTeraz pk WHERE pk.kupiony = false) " +
				"or p.id in (select pl.id from ProduktyLicytuj pl WHERE pl.dataZakonczenia >= CURRENT_DATE() )) " +
				" ORDER BY p.dataDodania DESC").setMaxResults(6);
		List<Produkty> result = new ArrayList<Produkty>();
		if(!query.list().isEmpty())
			result = (List<Produkty>) query.list();
		return result;
		
	}
	/**
	 * pobiera produkt o określonym identyfikatorze
	 * @param id identyfikator produktu
	 * @return produkt
	 */
	public Produkty getProdukt(Integer id)
	{
		Produkty p = new Produkty();
		Session session = getSessionFactory();
		p = (Produkty) session.get(Produkty.class, id);
		return p;
		
	}
	/**
	 * utrwala w bazie dnaych produkt który jest typu "Kup tearz"
	 * @param p produkt do utrwalenia
	 * @return zwraca true jeśli operacja sie powiedzie, w przeciwnym przypadku false
	 */
	public boolean saveProduktKupTeraz(ProduktyKupTeraz p)
	{
		try{
			Session session = getSessionFactory();
			Kategoria kat = (Kategoria) session.get(Kategoria.class, p.getKategorie().getId());
			p.setKategorie(kat);
			session.saveOrUpdate(p);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	/**
	 * uaktualnia dane dotyczące produktu
	 * @param p produkt który ma zostac uaktualniony
	 * @return zwraca true jesli operacja sie powiedzie, w przeciwnym wypadku false
	 */
	public boolean updateProdukt(Produkty p)
	{
		try{
			Session session = getSessionFactory();
			session.saveOrUpdate(p);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	/**
	 * utrwala obiekt typu "Licytuj" w pazie danych
	 * @param p produkt przeznaczony do utrwalenia
	 * @return zwraca true jesli operacaj sie powiedzi, w przeciwnym wypadku false
	 */
	public boolean saveProduktLicytuj(ProduktyLicytuj p)
	{
		try{
			Session session = getSessionFactory();
			Kategoria kat = (Kategoria) session.get(Kategoria.class, p.getKategorie().getId());
			p.setKategorie(kat);
			session.saveOrUpdate(p);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	/**
	 * pobiera identyfiaktory zdjęć należących do danego produktu
	 * @param p produkt którego identyfiaktory zdjeć mają zostać przeszukane
	 * @return lista identyfiaktorów zdjęć
	 */
	public List<Integer> getZdjeciaId(Produkty p)
	{
		Session session = getSessionFactory();
		Query query = session.createQuery("select z.id from Prod_Zdj pz left join pz.produkt as p left join pz.zdjecie as z WHERE p.id = :idP ")
				.setParameter("idP", p.getId() );
		List<Integer> result = new ArrayList<Integer>();
		if(!query.list().isEmpty())
			result = (List<Integer>) query.list();
		//System.out.println(result.get(0).getNazwa());
		return result;
	}
	/**
	 * pobiera zdjecie z bazy dnaych na podstawie identyfikatora
	 * @param id identyfiaktor zdjęcia
	 * @return obiekt zdjecia
	 */
	public Zdjecie getZdjecie(Integer id)
	{
		Session session = getSessionFactory();
		Query query = session.createQuery("from Zdjecie z where z.id = :idZ ")
				.setParameter("idZ", id);
		Zdjecie result = new Zdjecie();
		if(!query.list().isEmpty())
			result = (Zdjecie) query.list().get(0);
		//System.out.println(result.get(0).getNazwa());
		return result;
	}
	/**
	 * ustawia własność "kupione" na true listy podanych produktów i aktualizuje dane w bazie
	 * @param produkty lista produktów do zaktualizowania
	 */
	public void setKupione(List<Produkty> produkty)
	{
		Session session = getSessionFactory();
		ProduktyKupTeraz prod;
		for(Produkty p : produkty)
		{
			if(p instanceof ProduktyKupTeraz)
			{
				prod = (ProduktyKupTeraz) p;
				prod.setKupiony(true);
				session.saveOrUpdate(prod);
			}
		}
	}
	/**
	 * aktualziuje dane dotyczące produktu typu "Licytuj" 
	 * @param p produkt którego dane maja zostać zaktualizowane 
	 */
	public void updateLicytacja(ProduktyLicytuj p)
	{
		int id = p.getId();
		double cena = p.getCena();
		User u = p.getAktualnyWlasciciel();
		
		//p =null;
		Session session = getSessionFactory();
		//session.evict(p);
		//session.evict(u);
		ProduktyLicytuj prod = (ProduktyLicytuj) getProdukt(id);
		if(prod.getAktualnyWlasciciel() == null || u.getId().intValue() != prod.getAktualnyWlasciciel().getId().intValue())
			prod.setAktualnyWlasciciel(u);
		
		prod.setCena(cena);
		session.update(prod);
	}
	/**
	 * pobiera produkty które zostały sprzedane przez użytkownika
	 * @param u użytkownik
	 * @return lista produktów
	 */
	public List<Produkty> getSprzedaneProdukty(User u)
	{
		Session session = getSessionFactory();
		
		Query query = session.createQuery("from Produkty p inner join p.user as us WHERE us.id = :idW AND " +
				"p.id in (select pk.id from ProduktyKupTeraz pk WHERE pk.kupiony = true) " +
				"OR " +
				"p.id in (select pl.id from ProduktyLicytuj pl left join pl.aktualnyWlasciciel as w " +
				"WHERE pl.dataZakonczenia < CURRENT_DATE() " +
				"AND w.id is not null) ").setParameter("idW", u.getId() );
		
		List<Object> result = new ArrayList<Object>();
		result =  query.list();
		
		List<Produkty> resultFinal = new ArrayList<Produkty>();

		Object[] ob;
		int index = 0;
		for(Object o : result)
		{
			ob = (Object[]) result.get(index);
			for(int i=0;i<ob.length; i++)
			{
				if(ob[i] instanceof Produkty)
				{
					resultFinal.add((Produkty)ob[i]);
				}
			}
			index++;
		}
				
		return resultFinal;
		//return result;
		
	}
	/**
	 * pobiera produkty które wystawił na sprzedaż dnay użytkownik
	 * @param u uzytkownik
	 * @return lista produktów
	 */
	public List<Produkty> getWystawioneProdukty(User u)
	{
		Session session = getSessionFactory();
		
		Query query = session.createQuery("from Produkty p inner join p.user as us WHERE us.id = :idW AND " +
				"p.id in (select pk.id from ProduktyKupTeraz pk WHERE pk.kupiony = false) " +
				"OR " +
				"p.id in (select pl.id from ProduktyLicytuj pl left join pl.aktualnyWlasciciel as w " +
				"WHERE pl.dataZakonczenia >= CURRENT_DATE() " +
				"AND w.id is not null) ").setParameter("idW", u.getId() );
		
		List<Object> result = new ArrayList<Object>();
		result =  query.list();
		
		List<Produkty> resultFinal = new ArrayList<Produkty>();

		Object[] ob;
		int index = 0;
		for(Object o : result)
		{
			ob = (Object[]) result.get(index);
			for(int i=0;i<ob.length; i++)
			{
				if(ob[i] instanceof Produkty)
				{
					resultFinal.add((Produkty)ob[i]);
				}
			}
			index++;
		}
				
		return resultFinal;
		//return result;
		
	}
	/**
	 * pobiera produkty które kupił dnay użytkownik
	 * @param u uzytkownik
	 * @return lista produktów
	 */
	public List<Produkty> getKupioneProdukty(User u)
	{
		Session session = getSessionFactory();
		
		String sql = "from Zamowienie z inner join z.nabywca as nab inner join fetch z.produkty as p WHERE" +
				" nab.id = :idUser";
		Query query = session.createQuery(sql).setParameter("idUser", u.getId() );
		
		//List<Zamowienie> zam = new ArrayList<Zamowienie>();
		//zam = query.list();
		//System.out.println(zam.size());
		
		//List<Produkty> produkty = new ArrayList<Produkty>();
		//for(Zamowienie z: zam)
		//{
		//	produkty.addAll(z.getProdukty());
		//}
		//System.out.println(produkty.size());
		
		List<Object> result = new ArrayList<Object>();
		result =  query.list();
		
		List<Produkty> resultFinal = new ArrayList<Produkty>();
		
		Object[] ob;
		int index = 0;
		for(Object o : result)
		{
			ob = (Object[]) result.get(index);
			
			for(int i=0;i<ob.length; i++)
			{
				//System.out.println(ob[i]);
				if(ob[i] instanceof Zamowienie)
				{
					Zamowienie z = (Zamowienie) ob[i];
					resultFinal.addAll(z.getProdukty());
				}
			}
			index++;
		}
		//System.out.println(resultFinal.size());
		//System.out.println(resultFinal.get(0).getNazwa());
		return resultFinal;
	

	}
	/**
	 * pobiera produkty które wylicytował dnay użytkownik
	 * @param u uzytkownik
	 * @return lista produktów
	 */
	public List<Produkty> getWylicytowane(User u)
	{
		Session session = getSessionFactory();
		
		String sql = "from ProduktyLicytuj pl left join pl.aktualnyWlasciciel as w " +
				"WHERE pl.dataZakonczenia < CURRENT_DATE() " +
				"AND w.id = :idUser) ";
		Query query = session.createQuery(sql).setParameter("idUser", u.getId() );
		
		List<Object> result = new ArrayList<Object>();
		result =  query.list();
		
		List<Produkty> resultFinal = new ArrayList<Produkty>();

		Object[] ob;
		int index = 0;
		for(Object o : result)
		{
			ob = (Object[]) result.get(index);
			for(int i=0;i<ob.length; i++)
			{
				if(ob[i] instanceof Produkty)
				{
					resultFinal.add((Produkty)ob[i]);
				}
			}
			index++;
		}
		//System.out.println(resultFinal.get(0).getNazwa());		
		return resultFinal;
		//return result;
	}
	/**
	 * aktualizuje wspólne dane (dotyczące produktów typu "kup teraz" oraz "licytuj")
	 * @param p produkt którego dane maja zostać zaktualizowane
	 * @return zwraca true jeśli operacja się powiedzie oraz false w przeciwnym wypadku
	 */
	public boolean updateProduktInfo(Produkty p)
	{
		Session session = getSessionFactory();
		Produkty produkt = (Produkty) session.get(Produkty.class, p.getId());
		Kategoria kat = (Kategoria) session.get(Kategoria.class, p.getKategorie().getId());
		//System.out.println(kat.getNazwa());
		produkt.setKategorie(kat);
		produkt.setNazwa(p.getNazwa());
		produkt.setOpis(p.getOpis());
		session.update(produkt);
		
		return true;
	}
	/**
	 * usuwa produkt z bazy danych
	 * @param produktId identyfikator produktu który ma zostać usuniety
	 * @return zwraca true jesli operacja sie powiedzie, false w przeciwnym wypadku
	 */
	public boolean deleteProdukt(Integer produktId)
	{
		Session session = getSessionFactory();
		Produkty p = (Produkty) session.get(Produkty.class, produktId);
		session.delete(p);
		return true;
	}
	
	/**
	 * @author Robert
	 * usuwa produkt z bazy danych
	 * @param produktId identyfikator produktu który ma zostać usuniety
	 * 
	 */
	public void usunProdukt(Integer produktId)
	{
		Session session = getSessionFactory();
		Produkty p = getProdukt(produktId);
		System.out.println("test usuniecia " + p.getNazwa());
		session.delete(p);
		
	}
	
	/**
	 * sprawdza czy produkty nie zostal juz kupiony przez innego użytkownika (ważne podczas zatwierdzania zamówienia)
	 * @param produkty produkt który ma zostać sprawdzony
	 * @return  lista produktów które zostały już kupione i nie moga być kupione ponownie
	 */
	public List<Produkty> sprawdzProdukty(List<Produkty> produkty)
	{
		String s = "";
		int index = 0;
		int len = produkty.size() - 1;
		for(Produkty p : produkty)
		{
			if(index == len)
				s += p.getId().toString();
			else
				s += p.getId().toString()+",";
			index++;
		}
		
		Session session = getSessionFactory();
		Query query = session.createQuery("from Produkty p WHERE " +
				"p.id in (select pk.id from ProduktyKupTeraz pk WHERE pk.kupiony = true AND pk.id in ("+s+") )");
		
		List<Produkty> result = new ArrayList<Produkty>();
		result = (List<Produkty>)query.list();
		
		if(result.size() > 0)
			return result;
		
		return null;
	}
	
}
