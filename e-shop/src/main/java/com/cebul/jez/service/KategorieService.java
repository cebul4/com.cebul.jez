package com.cebul.jez.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cebul.jez.entity.Kategoria;
import com.cebul.jez.model.KategorieDao;

@Service
public class KategorieService 
{
	@Autowired
	private KategorieDao kategorieDao;

	public KategorieDao getKategorieDao() {
		return kategorieDao;
	}

	public void setKategorieDao(KategorieDao kategorieDao) {
		this.kategorieDao = kategorieDao;
	}
	@Transactional
	public List<Kategoria> getMainKategory()
	{
		return kategorieDao.getMainKategory();
	}
	@Transactional
	public List<Kategoria> getPodKategory(Integer parent)
	{
		return kategorieDao.getPodKategory(parent);
	}
	@Transactional
	public Kategoria getMainKategory(Kategoria podkategoria)
	{
		return kategorieDao.getMainKategory(podkategoria);
	}
	@Transactional
	public Kategoria getKategory(Integer id)
	{
		return kategorieDao.getKategory(id);
	}
	@Transactional
	public boolean addKategoria(Kategoria k)
	{
		return kategorieDao.addKategoria(k);
	}
	@Transactional
	public boolean updateCategory(Kategoria k)
	{
		return kategorieDao.updateCategory(k);
	}
	@Transactional
	public List<Kategoria> getAll()
	{
		return kategorieDao.getAll();
	}
	@Transactional
	public List<String> getKategoryString(Integer id)
	{
		return kategorieDao.getKategoryString(id);
	}
}
