package com.cebul.jez.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.DokumentZamowienia;
import com.cebul.jez.entity.Kategoria;
import com.cebul.jez.entity.Komentarz;
import com.cebul.jez.entity.Platnosc;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.User;
import com.cebul.jez.entity.Zamowienie;
import com.cebul.jez.flows.Tmp;

@Repository
public class ZamowienieDao extends Dao 
{
	public List<Platnosc> getPlatnosci()
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Platnosc");
		List<Platnosc> result = query.list();
		return result;
	}
	public List<DokumentZamowienia> getDokumenty()
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM DokumentZamowienia");
		List<DokumentZamowienia> result = query.list();
		return result;
	}
	public Platnosc getPlatnoscLike(String s)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Platnosc WHERE rodzajPlatnosci = :p ")
				.setParameter("p", s);
		List<Platnosc> result = query.list();
		return result.get(0);
	}
	public DokumentZamowienia getDokumentLike(String s)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM DokumentZamowienia WHERE nazwa = :p ")
				.setParameter("p", s);
		List<DokumentZamowienia> result = query.list();
		return result.get(0);
	}
	public boolean zapiszZamowienie(Zamowienie z)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(z);
		return true;
	}
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
	public boolean dodajKomentarz(Integer idProduktu, String komentarz, Integer ocena, User nadawca)
	{
		Session session = sessionFactory.getCurrentSession();
		Produkty p = (Produkty) session.get(Produkty.class, idProduktu);
		Komentarz k = new Komentarz(komentarz, ocena, nadawca, p.getUser(), p);
		session.save(k);
		
		return true;
	}
}
