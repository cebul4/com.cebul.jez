package com.cebul.jez.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cebul.jez.entity.DokumentZamowienia;
import com.cebul.jez.entity.Platnosc;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.User;
import com.cebul.jez.entity.Zamowienie;
import com.cebul.jez.model.ZamowienieDao;
import com.cebul.jez.useful.ShoppingCart;

@Service
public class ZamowienieService 
{
	@Autowired
	private ZamowienieDao zamowienieDao;
	
	@Autowired
	private ProduktyService produktyService;
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Transactional
	public List<Platnosc> getPlatnosci()
	{
		return zamowienieDao.getPlatnosci();
	}
	@Transactional
	public List<DokumentZamowienia> getDokumenty()
	{
		return zamowienieDao.getDokumenty();
	}
	@Transactional
	public Platnosc getPlatnoscLike(String s)
	{
		return zamowienieDao.getPlatnoscLike(s);
	}
	@Transactional
	public DokumentZamowienia getDokumentLike(String s)
	{
		return zamowienieDao.getDokumentLike(s);
	}
	@Transactional
	public List<Produkty> zapiszZamowienie(Zamowienie z)
	{
		//sprawdzenie
		List<Produkty> produkty = z.getProdukty();
		List<Produkty> juzKupione = produktyService.sprawdzProdukty(produkty);
		System.out.println("juz kupione to = "+juzKupione);
		if(juzKupione == null)
		{
			produktyService.setKupione(produkty);
			zamowienieDao.zapiszZamowienie(z);
		}else{
			return juzKupione;
		}
		
		return null;
	}
	@Transactional
	public List<Produkty> getNieKomentProd(User u)
	{
		
		return zamowienieDao.getNieKomentProd(u);
	}
	@Transactional
	public boolean dodajKomentarz(Integer idProduktu, String komentarz, Integer ocena, User nadawca)
	{
		return zamowienieDao.dodajKomentarz(idProduktu, komentarz, ocena, nadawca);
	}
	@Transactional
	public boolean getProduktZZamowienia(User u, Integer idProd)
	{
		if( zamowienieDao.getProduktZZamowienia(u, idProd) == null)
			return false;
		return true;
	}
	@Transactional
	public List<Zamowienie> getZamowienie()
	{
		return zamowienieDao.getZamowienie();
	}
}
