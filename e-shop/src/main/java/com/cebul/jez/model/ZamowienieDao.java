package com.cebul.jez.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.DokumentZamowienia;
import com.cebul.jez.entity.Kategoria;
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
		return null;
	}
}
