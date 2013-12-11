package com.cebul.jez.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cebul.jez.entity.Kategoria;
import com.cebul.jez.service.KategorieService;
import com.cebul.jez.useful.JsonKat;
import com.cebul.jez.useful.JsonObject;

/**
 *  Klasa pelniaca zadanie kontrolera w modelu MVC
 * Jej zadaniem jest obsługa rządań wysylanych z poziomu Javascriptu w formacie JSon
 * Wykorzystuje mechanizm DI do wstrzykiwania zależnosći
 * Wstrzyknięte zależnosci: KategorieService
 * 
 * @author Mateusz
 *
 */
@Controller
@RequestMapping("/dodajProdukt/podkategorie.json")
public class DodajProduktyController 
{
	@Autowired
	private KategorieService kategorieService;
	
	/**
	 * metoda zwraca podkategorie danej kategori 
	 * 
	 * @param model referencja do modelu
	 * @param kategory identyfikator kategorii dla której szukane są podkategorie
	 * @return zwraca obiekt JSon zawierajacy podkategorie
	 */
	@RequestMapping(method = RequestMethod.GET, params="kategory")
	public @ResponseBody JsonKat znajdzWyszukiwarka(Model model, @RequestParam Integer kategory) 
	{
		List<Kategoria> r = kategorieService.getPodKategory(kategory);
		JsonKat jso = new JsonKat();
		jso.generateKategorie(r);
		//System.out.println("jestemmm tutaj");
		
		return jso;
	}
}
