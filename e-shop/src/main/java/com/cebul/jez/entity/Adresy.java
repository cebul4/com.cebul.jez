package com.cebul.jez.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 
 * @author Mateusz
 * Obiekt reprezentujacy tabelę Adresy w bazie danych
 * zawiera informacje dotyczące adresu korespondencyjnego uzytkownika
 * 
 */
@Entity
@Table(name="Adresy")
public class Adresy implements Serializable
{
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@Column(name="Miejscowosc")
	@Size(min=3, max=20, message="Nie ma takiego miejsca.")
	private String miejscowosc;
	
	@Column(name="Kod_Pocztowy")
	@Size(min=6, max=6, message="Niepoprawny kod pocztowy.")
	private String kod_pocztowy;

	/**
	 * Pobiera wartość id
	 * @return zwraca identyfikator adresu
	 */
	public Integer getId() {
		return id;
	}
/**
 * Ustawia identyfikator adresu według paramteru id
 * @param id identyfikator adresu
 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getMiejscowosc() {
		return miejscowosc;
	}

	public void setMiejscowosc(String miejscowosc) {
		this.miejscowosc = miejscowosc;
	}

	public String getKod_pocztowy() {
		return kod_pocztowy;
	}

	public void setKod_pocztowy(String kod_pocztowy) {
		this.kod_pocztowy = kod_pocztowy;
	}
	
	
}
