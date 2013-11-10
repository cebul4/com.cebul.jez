package com.cebul.jez.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.Kategoria;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.ProduktyKupTeraz;
import com.cebul.jez.entity.ProduktyLicytuj;
import com.cebul.jez.entity.User;
import com.cebul.jez.entity.Zdjecie;

@Repository
public class TestDatabaseDao 
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
	public void testData1()
	{
		Session session = getSessionFactory();
		
		
		Kategoria k = new Kategoria("Elektronikaasda", null);
		Kategoria k1 = new Kategoria("Dom1234", k);
		k1.setParentKategory(k);
		session.save(k);
		session.save(k1);
		
		User u = (User) session.get(User.class, 2);
		Zdjecie z = new Zdjecie();
		z.setZdjecie(new File("test.txt"));
		session.save(z);
		
		ProduktyKupTeraz p = new ProduktyKupTeraz("pierwszy produkt", "taki sobie tma pridkut", 
				12.0, new Date(), k, z, u);
		
		session.save(p);
		ProduktyLicytuj p1 = new ProduktyLicytuj("drugi produkt", "dfrugii grudii taki sobie tma pridkut", 
				12.0, new Date(), k, z, u);
		
		Collection<Zdjecie> zdj = new ArrayList<Zdjecie>();
		zdj.add(z);
		p1.setZdjecia(zdj);
		
		session.save(p1);		
		
		
		
	}
	
}
