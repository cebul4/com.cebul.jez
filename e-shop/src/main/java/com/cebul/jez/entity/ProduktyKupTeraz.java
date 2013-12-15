package com.cebul.jez.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="ProduktyKupTeraz")
@PrimaryKeyJoinColumn(name="Id")
public class ProduktyKupTeraz extends Produkty implements Serializable
{

	@Column(name="Kupiony")
	private boolean kupiony;
	
	public ProduktyKupTeraz() {
		super();
		this.kupiony = false;
	}
	public ProduktyKupTeraz(String nazwa, String opis, Double cena, Date data, Kategoria kategoria,
			Zdjecie zdjecie, User user)
	{
		super(nazwa, opis, cena, data, kategoria, zdjecie, user);
		this.kupiony = false;
	}
	public boolean isKupiony() {
		return kupiony;
	}
	public void setKupiony(boolean kupiony) {
		this.kupiony = kupiony;
	}

	@Override
	public boolean equals(Object o) {
		
		if(o == null)
	        return false;

	    if(!(o instanceof ProduktyKupTeraz))
	        return false;
	    
	    ProduktyKupTeraz p = (ProduktyKupTeraz) o;
	    
	    if(!getId().equals(p.getId()) )
	    		return false;
	    
	    return true;
	}
	@Override
	public int hashcode() {
	    int hash = 3;
	    hash = 47 * hash + getNazwa().hashCode() + getId().hashCode();
	    return hash;
	}
	
}
