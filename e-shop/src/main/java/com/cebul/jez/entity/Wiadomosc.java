package com.cebul.jez.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Wiadomosci")
public class Wiadomosc {

	@Id
	@GeneratedValue
	@Column(name="IdWiad")
	private Integer idWiad;
	
	@Column(name="Tytul")
	private String tytul;
	
	@Column(name="Tresc", columnDefinition="TEXT")
	private String tresc;
	
	@Column(name="Data")
	private Date data;
	
	@Column(name="Odczytana")
	private boolean odczytana;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IdNadaw")
	private User nadawca12;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IdOdbior")
	private User odbiorca12;
	
	public Integer getIdWiad() {
		return idWiad;
	}

	public void setIdWiad(Integer idWiad) {
		this.idWiad = idWiad;
	}


	public String getTytul() {
		return tytul;
	}

	public void setTytul(String tytul) {
		this.tytul = tytul;
	}

	public String getTresc() {
		return tresc;
	}

	public void setTresc(String tresc) {
		this.tresc = tresc;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isOdczytana() {
		return odczytana;
	}

	public void setOdczytana(boolean odczytana) {
		this.odczytana = odczytana;
	}
	
	
	
}
