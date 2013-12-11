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
}
