package com.cebul.jez.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import com.cebul.jez.entity.Kategoria;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.ProduktyKupTeraz;
import com.cebul.jez.entity.ProduktyLicytuj;
import com.cebul.jez.entity.User;
import com.cebul.jez.entity.Zdjecie;

/**
 * służy do testowania podstawowych opercaji na abzie dnaych aby sprawdzic integralność danych
 * @author Mateusz
 *
 */
@Repository
public class TestDatabaseDao extends Dao
{

	/**
	 * metoda testujaca podstawowe operacja na bazie dnaych
	 */
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
		//z.setZdjecie(new File("test.txt"));
		session.save(z);
		
		ProduktyKupTeraz p = new ProduktyKupTeraz("pierwszy produkt", "taki sobie tma pridkut", 
				12.0, new Date(), k, z, u);
		
		session.save(p);
		ProduktyLicytuj p1 = new ProduktyLicytuj("drugi produkt", "dfrugii grudii taki sobie tma pridkut", 
				12.0, new Date(), k, z, u, new Date());
		
		List<Zdjecie> zdj = new ArrayList<Zdjecie>();
		zdj.add(z);
		p1.setZdjecia(zdj);
		
		Produkty prod = (Produkty) session.get(Produkty.class, 1);
		if(prod instanceof ProduktyKupTeraz)
		{
			System.out.println("jestem kup teraz");
			ProduktyKupTeraz pk = (ProduktyKupTeraz) prod;
			System.out.println(pk.isKupiony());
		}
		if(prod instanceof ProduktyLicytuj)
		{
			System.out.println("jestem licytuj");
		}		
		session.save(p1);		

	}
	
}
