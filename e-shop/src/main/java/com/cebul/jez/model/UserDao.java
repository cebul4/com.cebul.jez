package com.cebul.jez.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cebul.jez.entity.User;

/**
 * umożliwia pobieranie oraz utrwalanie obiektów User z i do bazy danych
 * @author Mateusz
 *
 */
@Repository
public class UserDao extends Dao
{
/**
 * zapisuje pustego użytkonika do bazyd danych
 */
	public void saveUser()
	{
		Session session = sessionFactory.getCurrentSession();
		User u = new User();
		session.save(u);
	}
	/**
	 * utrwala obiekt uzytkownika w bazie dnaych
	 * @param u uzytkownik
	 * @return zwraca true jeśli operacja sie uda, false w przeciwnym wypadku
	 */
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
	/**
	 * sprawdza czy użytkownik istnieje w bazie dnaych
	 * @param user użytkownik
	 * @return zwraca true jesli użytkonik isteniej w baze, w przeciwnym wypadku false
	 */
	public boolean isExist(User user)
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Users WHERE login = :username ").
				addEntity(User.class).setParameter("username", user.getLogin());
		List<User> result = query.list();
		if(!result.isEmpty())
		{
			return true;
		}
		return false;
	}
	/**
	 * aktywuje konto usera
	 * @param id identyfiaktor uzytkownika
	 */
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
	/**
	 * pobiera obiekt użytkownika na podstawie loginu
	 * @param login 
	 * @return obiekt użytkownika
	 */
	public User getUser(String login)
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Users WHERE login = :username ").
				addEntity(User.class).setParameter("username", login);
		List<User> result = query.list();
		if(result.size() == 0)
			return null;
		
		return result.get(0);
	}
	/**
	 * pobiera obiekt użytkownika na podstawie identyfikatora
	 * @param id identyfiaktor użytkownika
	 * @return obiekt użytkownika
	 */
	public User getUser(Integer id)
	{
		Session session = getSessionFactory();
		User u = (User) session.get(User.class, id);
		System.out.println("test w dao "+u.getLogin());
		return u;
	}
	/**
	 * sprawdza czy użytkonwik istnieje w bazie dnaych na podstawie loginu
	 * @param login
	 * @return zwraca true jeśli użytkownik istneieje, w przeciwnym wypadku false
	 */
	public boolean isUserExsist(String login)
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Users WHERE login = :username ").
				addEntity(User.class).setParameter("username", login);
		List<User> result = query.list();
		if(!result.isEmpty())
		{
			
			return true;
		}
		return false;
	}
	/**
	 * uaktualnia dane uzytkownika w bazie dnaych
	 * @param user uzytkownik którego dane mają zostac uatualnione
	 * @return
	 */
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
	/**
	 * @author Robert
	 * ustawia range użytkownika na "admin" na podstawie podanego loginu
	 * @param login 
	 */
	public void setAdmin(String login)
	{
		Session session = getSessionFactory();
		User u = getUser(login);
		
		u.getRanga();
		
		
		 if (u.getRanga().equals("user"))
		 {
			 u.setRanga("admin");
			 session.update(u);
			 
		 }
		
	}
	
	/**
	 * funkcja blokuje użytkownika określonego przez podane id, poprzez ustawienie pola Enabled na 0
	 * @author Robert
	 * @param id - id użytkownika
	 */
	public void blockUser(Integer id)
	{
		Session session = getSessionFactory();
		User u = getUser(id);
		
		//u.getEnabled();
		
		
		 if (u.getEnabled() == 1)
		 {
			 u.setEnabled(0);
			 session.update(u);
			 
		 }
		
	}
	public List<String> getUsers()
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT Login FROM users WHERE ranga LIKE 'user' ");
		List<String> result = query.list();
		
		return result;
	}
	
	public List<String> getAllUsers()
	{
		Session session = getSessionFactory();
		Query query = session.createSQLQuery("SELECT * FROM Users");
			
		List<String> result = query.list();
		if(result.size() == 0)
			return null;
		
		return result;
	}
	
	public void unblockUser(Integer id)
	{
		Session session = getSessionFactory();
		User u = getUser(id);
		
		//u.getEnabled();
		
		
		 if (u.getEnabled() == 0)
		 {
			 u.setEnabled(1);
			 session.update(u);
			 
		 }
		
	}
}
