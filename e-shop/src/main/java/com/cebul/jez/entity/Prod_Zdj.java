package com.cebul.jez.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *  obiekt reprezentujacy tabelę Prod_Zdj w bazie danych
 *  łączy tabele produkty i zdjecia
 * @author Mateusz
 *
 */
@Entity
@Table(name="Prod_Zdj")
public class Prod_Zdj 
{
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="IdProdukt")
	private Produkty produkt;
	
	@OneToOne
	@JoinColumn(name="IdZdjecia")
	private Zdjecie zdjecie;

	public Produkty getProdukt() {
		return produkt;
	}

	public void setProdukt(Produkty produkt) {
		this.produkt = produkt;
	}

	public Zdjecie getZdjecie() {
		return zdjecie;
	}

	public void setZdjecie(Zdjecie zdjecie) {
		this.zdjecie = zdjecie;
	}
	
	
}
