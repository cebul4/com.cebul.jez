package com.cebul.jez.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cebul.jez.entity.DokumentZamowienia;
import com.cebul.jez.entity.Platnosc;
import com.cebul.jez.entity.Zamowienie;
import com.cebul.jez.model.ZamowienieDao;

@Service
public class ZamowienieService 
{
	@Autowired
	private ZamowienieDao zamowienieDao;
	
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
	public boolean zapiszZamowienie(Zamowienie z)
	{
		return zamowienieDao.zapiszZamowienie(z);
	}
}
