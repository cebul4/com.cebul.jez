package com.cebul.jez.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.User;

@Repository
public class UserDao extends Dao
{
	
	public void saveUser()
	{
		Session session = sessionFactory.getCurrentSession();
		User u = new User();
		session.save(u);
	}
	public boolean addUser(User u)
	{
		Session session = sessionFactory.getCurrentSession();
		boolean exsist = isExist(u);
		if(!exsist)
		{
			session.save(u);
			return true;
		}
		return false;
	}
	public boolean isExist(User user)
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM users WHERE login = :username ").
				addEntity(User.class).setParameter("username", user.getLogin());
		List<User> result = query.list();
		if(!result.isEmpty())
		{
			return true;
		}
		return false;
	}
	public void activeUser(Integer id)
	{
		Session session = sessionFactory.getCurrentSession();
		User u = (User) session.get(User.class, id);
		if(u.getEnabled() == 0)
		{
			u.setEnabled(1);
			session.update(u);
		}	
	}
	public User getUser(String login)
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM users WHERE login = :username ").
				addEntity(User.class).setParameter("username", login);
		List<User> result = query.list();
		if(result.size() == 0)
			return null;
		
		return result.get(0);
	}
	public User getUser(Integer id)
	{
		Session session = getSessionFactory();
		User u = (User) session.get(User.class, id);
		System.out.println("test w dao "+u.getLogin());
		return u;
	}
	public boolean isUserExsist(String login)
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM users WHERE login = :username ").
				addEntity(User.class).setParameter("username", login);
		List<User> result = query.list();
		if(!result.isEmpty())
		{
			
			return true;
		}
		return false;
	}
	public boolean updateUser(User user)
	{
		Session session = getSessionFactory();
		//session.saveOrUpdate(user);
		User updateU = (User) session.get(User.class, user.getId());
		updateU.setImie(user.getImie());
		updateU.setNazwisko(user.getNazwisko());
		updateU.setAdres(user.getAdres());
		updateU.setPlec(user.getPlec());
		updateU.setRok(user.getRok());
		updateU.setMiesiac(user.getMiesiac());
		updateU.setDzien(user.getDzien());
		
		session.update(updateU);
		
		return true;
	}
}
