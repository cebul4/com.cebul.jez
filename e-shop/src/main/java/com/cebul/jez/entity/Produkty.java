package com.cebul.jez.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="Produkty")
@Inheritance(strategy=InheritanceType.JOINED)
public class Produkty implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@Column(name="Nazwa")
	@Size(min=3, max=20, message="Nazwa produktu musi składać się z od 3 do 20 znaków.")
	private String nazwa;
	
	@Column(name="Opis", columnDefinition="TEXT")
	@NotNull(message="Opis produktu nie moze byc pusty")
	private String opis;
	
	@Column(name="Cena")
	@NotNull(message="Cena produltu nie moze byc pusta")
	@Min(value=1, message="Minimalna cena musi być równa 1 zł")
	private Double cena;
	
	@Column(name="DataDodania")
	@NotNull
	private Date dataDodania;
	
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name="IdKat")
	private Kategoria kategorie;
	
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name="IdZdjGlow")
	private Zdjecie zdjecie;
	
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="IdWlas")
	private User user;

	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name = "prod_zdj", joinColumns = { @JoinColumn(name = "IdProdukt") }, 
	inverseJoinColumns = { @JoinColumn(name = "IdZdjecia") })
	private List<Zdjecie> zdjecia = new ArrayList<Zdjecie>();
	
	public List<Zdjecie> getZdjecia() {
		return zdjecia;
	}
	public void setZdjecia(List<Zdjecie> zdjecia) {
		this.zdjecia = zdjecia;
	}
	public Produkty()
	{
		this.kategorie = new Kategoria();
		this.dataDodania = new Date();
		//this.kategorie.setId(1);
	}
	public Produkty(String nazwa, String opis, Double cena, Date data, Kategoria kategoria,
			Zdjecie zdjecie, User user)
	{
		this.nazwa = nazwa;
		this.opis = opis;
		this.cena= cena;
		this.dataDodania = data;
		this.kategorie = kategoria;
		this.zdjecie = zdjecie;
		this.user = user;
		this.dataDodania = new Date();
	}
	public Kategoria getKategorie() {
		return kategorie;
	}

	public void setKategorie(Kategoria kategorie) {
		this.kategorie = kategorie;
	}

	public Zdjecie getZdjecie() {
		return zdjecie;
	}

	public void setZdjecie(Zdjecie zdjecie) {
		this.zdjecie = zdjecie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public Date getDataDodania() {
		return dataDodania;
	}

	public void setDataDodania(Date dataDodania) {
		this.dataDodania = dataDodania;
	}
	public void addZdjecie(Zdjecie zdj)
	{
		this.zdjecia.add(zdj);
	}
	public void removeZdj(Integer []rem)
	{
		List<Zdjecie> remZdj = new ArrayList<Zdjecie>();
		for(int i=0; i<rem.length; i++)
		{
			for(Zdjecie z : zdjecia)
			{
				if(z.getId().equals(rem[i]))
				{
					remZdj.add(z);
					if(z.getId().equals(zdjecie.getId()) )
					{
						setZdjecie(null);
					}
					//zdjecia.remove(z);
				}
			}
		}
		zdjecia.removeAll(remZdj);
		if(zdjecia.size() > 0)
		{
			setZdjecie(getZdjecia().get(0));
		}else{
			setZdjecie(null);
		}
	}
	@Override
	public boolean equals(Object o) {
		
		if(o == null)
	        return false;

	    if(!(o instanceof Produkty))
	        return false;
	    
	    Produkty p = (Produkty) o;
	    
	    if(!getId().equals(p.getId()) )
	    		return false;
	    
	    return true;
	}
	
	public int hashcode() {
	    int hash = 3;
	    hash = 47 * hash + getNazwa().hashCode() + getId().hashCode();
	    return hash;
	}
	
}
