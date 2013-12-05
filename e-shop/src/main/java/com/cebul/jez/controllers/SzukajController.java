package com.cebul.jez.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cebul.jez.entity.Kategoria;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.ProduktyKupTeraz;
import com.cebul.jez.service.KategorieService;
import com.cebul.jez.service.ProduktyService;
import com.cebul.jez.useful.JsonObject;


/**
 * 
 * @author Mateusz
 *	Klasa działa jako kontroler w modelu MVC
 *	używa mechanizmu DI do wstrzykiwania zależnosći
 *	obsługuje żądania z ścieżki "/szukaj/*"
 *
 */
@Controller
@RequestMapping("/szukaj")
public class SzukajController 
{
	@Autowired
	private ProduktyService produktyService;
	
	@Autowired
	private KategorieService kategorieService;
	
	/**
	 * mapuje rzadanie dostępu /szukaj.json
	 * metoda ta jets wyowływana z poziomu jezyka Javascript
	 * zwraca object Json ktory zawiera słowa pasujaće do padanego wzorca
	 * 
	 * @param model zawiera referencje do obiektu modelu
	 * @param slowo fraza która ma być użyta do przeszukania bazy danych pod kontem zgodnosći z nią
	 * @return zwraca objekt Ktry zawiera pasujące słowa (objekt JSon)
	 */
	@RequestMapping(value="/szukaj.json", method = RequestMethod.GET, params="slowo")
	public @ResponseBody JsonObject znajdzWyszukiwarka(Model model, @RequestParam String slowo) 
	{
		List<String> r = produktyService.getProduktyLike(slowo);
		JsonObject jso = new JsonObject();
		jso.generateProdukty(r);
		
		return jso;
	}
	/**
	 * mapuje rzadanie /szukajProd/
	 * metoda jets używana do wyswietlenia informacji o prduktach na stronie klienta
	 * 
	 * @param model zawiera referencje do obiektu modelu
	 * @param szukanaKat zawier ainformację o tym w jakiej kategorii maja być szukane produkty
	 * @param szukanaFraza zawiera informację o szukanej frazie
	 * @return
	 */
	@RequestMapping(value="/szukajProd/", method = RequestMethod.GET, params={"szukanaKat","szukanaFraza"})
	public String znajdzWyszukiwarka(Model model, @RequestParam Integer szukanaKat, @RequestParam String szukanaFraza) 
	{
		List<Produkty> produkty;
		List<Kategoria> podkategorie = new ArrayList<Kategoria>();
		Boolean hasPodkategory = false;
		
		if(szukanaKat.equals(0))
		{
			produkty = produktyService.getFullProduktyLike(szukanaFraza);
		}
		else
		{
			produkty = produktyService.getFullProduktyLike(szukanaFraza, szukanaKat);
			if(produkty.size() > 0)
			{
				podkategorie = kategorieService.getPodKategory(szukanaKat);
				hasPodkategory = true;
			}
		}
		//System.out.println(produkty.size());
		
		List<Boolean> czyKupTeraz = new ArrayList<Boolean>();
		
		for(Produkty p : produkty)
		{
			if(p instanceof ProduktyKupTeraz)
			{
				czyKupTeraz.add(true);
				System.out.println("kup teraz");
			}
			else
				czyKupTeraz.add(false);
		}
	
		
		model.addAttribute("szukaneProdukty", produkty);
		model.addAttribute("czyKupTeraz", czyKupTeraz);
		model.addAttribute("podkategorie", podkategorie);
		model.addAttribute("hasPodkategory", hasPodkategory);
		//System.out.println(podkategorie.size());
		
		
		return "szukaneProdukty";
	}
}
