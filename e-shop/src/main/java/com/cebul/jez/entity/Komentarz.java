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

@Entity
@Table(name="Komentarze")
public class Komentarz implements Serializable
{

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
		
	@Column(name="Komentarz", columnDefinition="TEXT")
	@NotNull
	private String komentarz;
	
	@Column(name="Ocena")
	@NotNull
	private Integer ocena;
	
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="IdNadaw")
	private User nadawca;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="IdOdb")
	private User odbiorca;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="IdProd")
	private Produkty produkt;
	
	public Komentarz()
	{
		
	}
	
	public Komentarz(String komentarz, Integer ocena, User nadawca, User odbiorca, Produkty produkt)
	{
		this.komentarz = komentarz;
		this.ocena = ocena;
		this.nadawca = nadawca;
		this.odbiorca = odbiorca;
		this.produkt = produkt;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKomentarz() {
		return komentarz;
	}
	public void setKomentarz(String komentarz) {
		this.komentarz = komentarz;
	}
	public Integer getOcena() {
		return ocena;
	}
	public void setOcena(Integer ocena) {
		this.ocena = ocena;
	}
	public User getNadawca() {
		return nadawca;
	}
	public void setNadawca(User nadawca) {
		this.nadawca = nadawca;
	}
	public User getOdbiorca() {
		return odbiorca;
	}
	public void setOdbiorca(User odbiorca) {
		this.odbiorca = odbiorca;
	}
	public Produkty getProdukt() {
		return produkt;
	}
	public void setProdukt(Produkty produkt) {
		this.produkt = produkt;
	}
	
	
}
