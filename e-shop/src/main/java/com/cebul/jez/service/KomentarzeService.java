package com.cebul.jez.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cebul.jez.entity.Komentarz;
import com.cebul.jez.entity.User;
import com.cebul.jez.model.KomentarzeDao;

@Service
public class KomentarzeService {
	
	@Autowired
	private KomentarzeDao komentarzeDao;
	
	@Transactional
	public List<Komentarz> pobierzOtrzymaneKomentarze(User u)
	{
		return komentarzeDao.pobierzOtrzymaneKomentarze(u);
	}
	@Transactional
	public List<Komentarz> pobierzWystawioneKomentarze(User u)
	{
		return komentarzeDao.pobierzWystawioneKomentarze(u);
	}
}
