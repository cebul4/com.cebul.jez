package com.cebul.jez.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.cebul.jez.entity.Hist_Wyszuk;
import com.cebul.jez.model.Hist_WyszDao;


@Service
public class HistService 
{

	@Autowired
	private Hist_WyszDao histDao;
	
	
	@Transactional
	public List<Hist_Wyszuk> getHistory()
	{
		return histDao.getHistory();
	}
}
