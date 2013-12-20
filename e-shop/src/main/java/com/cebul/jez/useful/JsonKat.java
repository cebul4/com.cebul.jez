package com.cebul.jez.useful;

import java.util.List;

import com.cebul.jez.entity.Kategoria;

/**
 * obiekt który jest zwracany podczas asynchronicznego wyowlania od klienta
 * zgodny ze strukturą JSon
 * przechowuje informacje o kategoriach
 * @author Mateusz
 *
 */
public class JsonKat 
{
	private Kategoria kategorie[];

	public Kategoria[] getKategorie() {
		return kategorie;
	}

	public void setKategorie(Kategoria[] kategorie) {
		this.kategorie = kategorie;
	}
	
	public void generateKategorie(List<Kategoria> k)
	{
		int size = k.size();
		kategorie = k.toArray(new Kategoria[size]);
	}
}
