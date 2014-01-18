package com.cebul.jez.service;

import java.util.List;

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
		return userDao.getUser(login);
	}
	@Transactional
	public boolean isUserExsist(String login)
	{
		return userDao.isUserExsist(login);
	}
	@Transactional
	public User getUser(Integer id)
	{
		return userDao.getUser(id);
	}
	@Transactional
	public boolean updateUser(User user)
	{
		return userDao.updateUser(user);
	}
	
	@Transactional
	public void setAdmin(String login)
	{
		 userDao.setAdmin(login);
	}
	
	@Transactional
	public void blockUser(Integer id)
	{
		 userDao.blockUser(id);
	}
	
	@Transactional
	public void unblockUser(Integer id)
	{
		 userDao.unblockUser(id);
	}
	@Transactional
	public List<String> getUsers()
	{
		return userDao.getUsers();
	}
	@Transactional
	public List<String> getAllUsers()
	{
		return userDao.getAllUsers();
	}
}
