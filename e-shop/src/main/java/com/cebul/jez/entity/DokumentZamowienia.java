package com.cebul.jez.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Obiekt reprezentujacy tabelę DokumentZamowienia w bazie danych
 * zawiera możliwe do wyboru dokumenty potwierdzajace zakup
 * @author Mateusz
 *
 */
@Entity
@Table(name="DokumentZamowienia")
public class DokumentZamowienia implements Serializable
{
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@Column(name="Nazwa")
	@NotNull
	private String nazwa;
	
	public DokumentZamowienia()
	{
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	
	
	
}
