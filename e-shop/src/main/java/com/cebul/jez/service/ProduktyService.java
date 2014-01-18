package com.cebul.jez.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.ProduktyKupTeraz;
import com.cebul.jez.entity.ProduktyLicytuj;
import com.cebul.jez.entity.User;
import com.cebul.jez.entity.Zdjecie;
import com.cebul.jez.model.ProduktyDao;


@Service
public class ProduktyService 
{
	@Autowired
	private ProduktyDao produktyDao;
	
	@Transactional
	public List<String> getProduktyLike(String like)
	{
		return  produktyDao.getProduktyLike(like);
	}
	@Transactional
	public List<String> getProduktyLike(String like,Integer kategoria)
	{
		return produktyDao.getProduktyLike(like, kategoria);
	}
	@Transactional
	public boolean saveProduktKupTeraz(ProduktyKupTeraz p)
	{
		 return produktyDao.saveProduktKupTeraz(p);
	}
	@Transactional
	public Produkty getProdukt(Integer id)
	{
		return produktyDao.getProdukt(id);
	}
	@Transactional
	public boolean updateProdukt(Produkty p)
	{
		return produktyDao.updateProdukt(p);
	}
	@Transactional
	public boolean saveProduktLicytuj(ProduktyLicytuj p)
	{
		return produktyDao.saveProduktLicytuj(p);
	}
	@Transactional
	public List<Produkty> getLastFourProdukt()
	{
		return produktyDao.getLastFourProdukt();
	}
	@Transactional
	public List<Integer> getZdjeciaId(Produkty p)
	{
		return produktyDao.getZdjeciaId(p);
	}
	@Transactional
	public Zdjecie getZdjecie(Integer id)
	{
		return produktyDao.getZdjecie(id);
	}
	@Transactional
	public List<Produkty> getFullProduktyLike(String like, Integer kategoria)
	{
		return produktyDao.getFullProduktyLike(like, kategoria);
	}
	@Transactional
	public List<Produkty> getFullProduktyLike(String like)
	{
		return produktyDao.getFullProduktyLike(like);
	}
	@Transactional
	public void setKupione(List<Produkty> produkty)
	{
		produktyDao.setKupione(produkty);
	}
	@Transactional
	public void updateLicytacja(ProduktyLicytuj p)
	{
		produktyDao.updateLicytacja(p);
	}
	@Transactional
	public List<Produkty> getSprzedaneProdukty(User u)
	{
		return produktyDao.getSprzedaneProdukty(u);
	}
	@Transactional
	public List<Produkty> getWystawioneProdukty(User u)
	{
		return produktyDao.getWystawioneProdukty(u);
	}
	@Transactional
	public boolean updateProduktInfo(Produkty p)
	{
		return produktyDao.updateProduktInfo(p);
	}
	@Transactional
	public boolean deleteProdukt(Integer produktId)
	{
		return produktyDao.deleteProdukt(produktId);
	}
	@Transactional
	public List<Produkty> getFullProduktyLike(String like, Integer kategoria, Double cenaOd, Double cenaDo, String []kupLic, Integer []podkat)
	{
		return produktyDao.getFullProduktyLike(like, kategoria, cenaOd, cenaDo, kupLic, podkat);
	}
	@Transactional
	public List<Produkty> sprawdzProdukty(List<Produkty> produkty)
	{
		return produktyDao.sprawdzProdukty(produkty);
	}
	@Transactional
	public List<Produkty> getKupioneProdukty(User u)
	{
		return produktyDao.getKupioneProdukty(u);
	}
	@Transactional
	public List<Produkty> getWylicytowane(User u)
	{
		return produktyDao.getWylicytowane(u);
	}
	@Transactional
	public void usunProdukt(Integer produktId)
	{
		 produktyDao.usunProdukt(produktId);
	}
	@Transactional
	public List<String> getAllProducts()
	{
		return produktyDao.getAllProducts();
	}
	
}
