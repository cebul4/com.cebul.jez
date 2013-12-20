package com.cebul.jez.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * obiekt reprezentujacy tabelę Hist_wyszuk w bazie danych
 * zawiera informacje o tym które kategorie są przeszukiwane podczas wyszukiwania
 * na podstawie tych informacji powstają wykresy przedstawiajace popularność wyszukiwanych kategorii
 * @author Mateusz
 *
 */
@Entity
@Table(name="Hist_Wyszuk")
public class Hist_Wyszuk 
{
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@Column(name="Data")
	@NotNull
	private Date data;
	
	@OneToOne
	@JoinColumn(name="IdKat")
	private Kategoria kategoria;

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

	public Kategoria getKategoria() {
		return kategoria;
	}

	public void setKategoria(Kategoria kategoria) {
		this.kategoria = kategoria;
	}
	
	
}
