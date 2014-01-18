package com.cebul.jez.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.Hist_Wyszuk;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.Zamowienie;


@Repository
public class Hist_WyszDao extends Dao
{
	
	public List<Hist_Wyszuk> getHistory()
	{
		Session session = getSessionFactory();
		
		Query query = session.createQuery("from Hist_Wyszuk");
		
		List<Hist_Wyszuk> result = new ArrayList<Hist_Wyszuk>();
		result = (List<Hist_Wyszuk>) query.list();
		
		System.out.println(result.get(0));
		
		return result;
	}
	public List<Produkty> getProdukty()
	{
		Session session = getSessionFactory();
		
		String sql = "from Zamowienie z inner join z.nabywca as nab inner join fetch z.produkty as p";
		Query query = session.createQuery(sql);
		
		
		
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
				//System.out.println(ob[i].toString());
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
		//System.out.println(resultFinal.get(0).getKategorie().getId());
		return resultFinal;
	

	}

}
