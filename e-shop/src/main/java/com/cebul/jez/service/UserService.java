package com.cebul.jez.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cebul.jez.entity.User;
import com.cebul.jez.model.UserDao;

@Service
public class UserService 
{
	@Autowired
	UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Transactional
	public void saveUser()
	{
		userDao.saveUser();
	}
	@Transactional
	public boolean addUser(User u)
	{
		return userDao.addUser(u);
	}
	@Transactional
	public void activeUser(Integer id)
	{
		userDao.activeUser(id);
	}
	@Transactional
	public User getUser(String login)
	{
		return getUser(login);
	}
	@Transactional
	public boolean isUserExsist(String login)
	{
		return userDao.isUserExsist(login);
	}
}
