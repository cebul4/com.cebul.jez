package com.cebul.jez.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="kategorie")
public class Kategoria 
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
		nazwa = nazwa;
	}
	
	public Kategoria getParentKategory() {
		return parentKategory;
	}

	public void setParentKategory(Kategoria parentKategory) {
		this.parentKategory = parentKategory;
	}
	
	
}
