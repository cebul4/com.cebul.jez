package com.cebul.jez.useful;

import java.io.Serializable;

import com.cebul.jez.entity.ProduktyLicytuj;

/**
 * obiekt który jest zwracany podczas asynchronicznego wyowlania od klienta
 * zgodny ze strukturą JSon
 * przechowuje informacje o produkcie przeznaczonym do licytacji
 * @author Mateusz
 *
 */
public class JsonLicytacja implements Serializable
{
	private Integer userId;
	private Double cena;

	public JsonLicytacja(ProduktyLicytuj pl)
	{
		userId = pl.getAktualnyWlasciciel().getId();
		cena = pl.getCena();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}
	
	
}
