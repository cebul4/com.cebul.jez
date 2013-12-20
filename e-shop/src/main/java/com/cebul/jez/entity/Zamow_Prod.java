package com.cebul.jez.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *  obiekt reprezentujacy tabelę Zamow_Prod w bazie danych
 *  łączy tabele zamówienia i produkty
 * @author Mateusz
 *
 */
@Entity
@Table(name="Zamow_Prod")
public class Zamow_Prod 
{
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="Zamowienie")
	private Zamowienie zamowienie;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="Produkt")
	private Produkty produkt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Zamowienie getZamowienie() {
		return zamowienie;
	}

	public void setZamowienie(Zamowienie zamowienie) {
		this.zamowienie = zamowienie;
	}

	public Produkty getProdukt() {
		return produkt;
	}

	public void setProdukt(Produkty produkt) {
		this.produkt = produkt;
	}
	
	
}
