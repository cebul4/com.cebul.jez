package com.cebul.jez.useful;

import java.util.List;

/**
 * obiekt który jest zwracany podczas asynchronicznego wyowlania od klienta
 * zgodny ze strukturą JSon
 * przechowuje informacje o nazwach produktów
 * @author Mateusz
 *
 */
public class JsonObject 
{
	private String produkty[];

	public String[] getProdukty() {
		return produkty;
	}

	public void setProdukty(String[] produkty) {
		this.produkty = produkty;
	}
	public void generateProdukty(List<String> p)
	{
		int size = p.size();
		produkty = p.toArray(new String[size]);
	}
	
	
}
