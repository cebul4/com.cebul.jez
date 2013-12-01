package com.cebul.jez.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="ProduktyLicytuj")
@PrimaryKeyJoinColumn(name="Id")
public class ProduktyLicytuj extends Produkty implements Serializable
{
	@Column(name="DataZakonczenia")
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataZakonczenia;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="AktualnyWlasciciel")
	private User aktualnyWlasciciel;
	

	public ProduktyLicytuj()
	{
		super();
		//this.dataZakonczenia = new Date();
	}
	public ProduktyLicytuj(String nazwa, String opis, Double cena, Date data, Kategoria kategoria,
			Zdjecie zdjecie, User user, Date dataZakonczenia)
	{
		super(nazwa, opis, cena, data, kategoria, zdjecie, user);
		this.dataZakonczenia = dataZakonczenia;
		
	}
	
	public Date getDataZakonczenia() {
		return dataZakonczenia;
	}

	public void setDataZakonczenia(Date dataZakonczenia) {
		this.dataZakonczenia = dataZakonczenia;
	}
	public User getAktualnyWlasciciel() {
		return aktualnyWlasciciel;
	}
	public void setAktualnyWlasciciel(User aktualnyWlasciciel) {
		this.aktualnyWlasciciel = aktualnyWlasciciel;
	}
	
	
}
