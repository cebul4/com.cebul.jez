package com.cebul.jez.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.Hist_Wyszuk;


@Repository
public class Hist_WyszDao extends Dao
{
	
	public List<Hist_Wyszuk> getHistory()
	{
		Session session = getSessionFactory();
		
		Query query = session.createQuery("select data, idkat from hist_wyszuk");
		
		List<Hist_Wyszuk> result = new ArrayList<Hist_Wyszuk>();
		result = (List<Hist_Wyszuk>) query.list();
		
		System.out.println(result.get(0));
		
		return result;
	}
	

}
