package com.cebul.jez.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Platnosci")
public class Platnosc implements Serializable
{

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@Column(name="RodzajPlatnosci")
	@NotNull
	private String rodzajPlatnosci;

	public Platnosc()
	{
		
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRodzajPlatnosci() {
		return rodzajPlatnosci;
	}

	public void setRodzajPlatnosci(String rodzajPlatnosci) {
		this.rodzajPlatnosci = rodzajPlatnosci;
	}
	
	
	
}
