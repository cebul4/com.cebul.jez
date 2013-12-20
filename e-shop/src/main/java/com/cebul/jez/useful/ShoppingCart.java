package com.cebul.jez.useful;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.cebul.jez.entity.Produkty;

/**
 * 
 * Klasa reprezentuje koszyk na zakupy
 * przechowuje informacje o dodanych do koszyka produkatach
 * umozliwia dodawanie oraz usówanie produktów do koszyka
 * @author Mateusz
 *
 */
@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart implements Serializable
{
	private List<Produkty> items;
	private Double suma;
	
	public ShoppingCart()
	{
		this.items = new ArrayList<Produkty>();
		this.suma = 0.0;
	}
	/**
	 * dodaje produkt do koszyka, jednocześnie zwiększa wartość (sumę cen) 
	 * produktów znajdujacych sie w koszyku
	 * @param i produkt który ma zostać dodany do ksoszyka
	 */
	public void addItem(Produkty i)
	{
		items.add(i);
		addToSum(i.getCena());
	}
	/**
	 * 
	 * @param p przedmiot który ma zostać usunięty z koszyka
	 * @return zwraca true jeśli opercja się powiedzie lub false w przypadku błedu
	 */
	public boolean removeItem(Produkty p)
	{
		subFromSum(p.getCena());
		return items.remove(p);
	}
	/**
	 * pobiera wszytskie produkty znajdujaće sie w kosyzku
	 * @return zwraca liste produktów znajdujacych sie w kosyzku
	 */
	public List<Produkty> getItems() {
		return items;
	}
	/**
	 * ustawia zawrtosc koszyka
	 * @param items produkty które mają sie znajdowac w koszyku
	 */
	public void setItems(List<Produkty> items) {
		this.items = items;
	}
	/**
	 * dodaje cenę produktu do ogólnej sumy cen produktów znajdujacych sie  w kosyzku
	 * @param cena cena produktu
	 */
	public void addToSum(Double cena)
	{
		this.suma += cena;
	}
	/**
	 * pomniejsza wartosc koszyka o cenę produktu
	 * @param cena cena produktu
	 */
	public void subFromSum(Double cena)
	{
		this.suma -= cena;
	}

	public Double getSuma() {
		return suma;
	}

	public void setSuma(Double suma) {
		this.suma = suma;
	}
	/**
	 * czyści zawartosc koszyka
	 */
	public void clear()
	{
		this.items = new ArrayList<Produkty>();
		this.suma = 0.0;
	}
	
	
}
