package com.cebul.jez.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Zamowienie implements Serializable
{
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@Column(name="Data")
	@NotNull
	private Date data;
	
	@Column(name="Koszt")
	@NotNull
	private Double koszt;

	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="DokumentZamowienia")
	private DokumentZamowienia dokumentZamowienia;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="Platnosc")
	private Platnosc platnosc;

	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="Nabywca")
	private User nabywca;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	
	private List<Produkty> produkty = new ArrayList<Produkty>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public DokumentZamowienia getDokumentZamowienia() {
		return dokumentZamowienia;
	}

	public void setDokumentZamowienia(DokumentZamowienia dokumentZamowienia) {
		this.dokumentZamowienia = dokumentZamowienia;
	}
	

	public Platnosc getPlatnosc() {
		return platnosc;
	}

	public void setPlatnosc(Platnosc platnosc) {
		this.platnosc = platnosc;
	}

	public User getNabywca() {
		return nabywca;
	}

	public void setNabywca(User nabywca) {
		this.nabywca = nabywca;
	}

	public List<Produkty> getProdukty() {
		return produkty;
	}

	public void setProdukty(List<Produkty> produkty) {
		this.produkty = produkty;
	}

	public Double getKoszt() {
		return koszt;
	}

	public void setKoszt(Double koszt) {
		this.koszt = koszt;
	}
	
	
	
}
