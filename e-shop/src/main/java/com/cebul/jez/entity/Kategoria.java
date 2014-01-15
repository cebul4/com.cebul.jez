package com.cebul.jez.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *  obiekt reprezentujacy tabelę kategorie w bazie danych
 *  zawiera kategorie do których przyporządkowywane są produkty
 * @author Mateusz
 *
 */
@Entity
@Table(name="kategorie")
public class Kategoria implements Serializable
{
	@Id
	@GeneratedValue
	@Column(name="Id")
	private int id;
	
	@Column(name="Nazwa")
	@NotNull
	private String nazwa;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="IdParentKat")
	private Kategoria parentKategory;

	public Kategoria()
	{
		
	}
	public Kategoria(String nazwa, Kategoria parent)
	{
		this.nazwa = nazwa;
		this.parentKategory = parent;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	
	public Kategoria getParentKategory() {
		return parentKategory;
	}

	public void setParentKategory(Kategoria parentKategory) {
		this.parentKategory = parentKategory;
	}
	
	
}
