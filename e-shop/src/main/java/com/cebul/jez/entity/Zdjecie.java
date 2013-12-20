package com.cebul.jez.entity;

import java.io.File;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  obiekt reprezentujacy tabelę Zdjecia w bazie danych
 *  przechowuje zdjęcia produktów, które wysłał na serwer użytkownik
 * @author Mateusz
 *
 */
@Entity
@Table(name="Zdjecia")
public class Zdjecie 
{
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@Column(name="Zdjecie", columnDefinition="LONGBLOB")
	private byte[] zdjecie;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getZdjecie() {
		return zdjecie;
	}

	public void setZdjecie(byte[] zdjecie) {
		this.zdjecie = zdjecie;
	}

	
	/*
	public Blob getZdjecie() {
		return zdjecie;
	}

	public void setZdjecie(Blob zdjecie) {
		this.zdjecie = zdjecie;
	}
	*/
	
	
}
