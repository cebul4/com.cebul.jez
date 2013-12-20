package com.cebul.jez.flows;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * przechowuje najpotrzebniejsze informacje o 
 * uzytkowniku aby nie przekazywać całego obiektu użytkownnika
 * @author Mateusz
 *
 */
@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserShortInfo implements Serializable
{
	private Integer id;
	private String login;
	private String ranga;
	private String email;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getRanga() {
		return ranga;
	}
	public void setRanga(String ranga) {
		this.ranga = ranga;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEmpty()
	{
		if(id == null) 
			return true;
		
		return false;
	}
}
