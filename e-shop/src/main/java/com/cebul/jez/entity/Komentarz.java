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
@Table(name="Komentarze")
public class Komentarz 
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
	
	
}
