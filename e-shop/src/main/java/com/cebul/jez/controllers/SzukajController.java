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
import com.cebul.jez.entity.ProduktyLicytuj;
import com.cebul.jez.service.KategorieService;
import com.cebul.jez.service.ProduktyService;
import com.cebul.jez.useful.CheckBoxLicKup;
import com.cebul.jez.useful.JsonKat;
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
	@RequestMapping(value="/szukajProd/", method = RequestMethod.GET)
	public String znajdzWyszukiwarka(Model model, @RequestParam(value="szukanaKat", required=false) Integer szukanaKat, @RequestParam(value="szukanaFraza", required=false) String szukanaFraza,
			@RequestParam(value="cenaOd", required=false) Double cenaOd, @RequestParam(value="cenaDo", required=false) Double cenaDo,
			CheckBoxLicKup chLicKup) 
	{
		List<Produkty> produkty;
		List<Kategoria> podkategorie = new ArrayList<Kategoria>();
		Boolean hasPodkategory = false;
		
		String kupLic[] = chLicKup.getKupLicyt();
		Integer podkat[] = chLicKup.getPodkat();
		produkty =produktyService.getFullProduktyLike(szukanaFraza, szukanaKat, cenaOd, cenaDo, kupLic, podkat);
		
		if(szukanaKat != null && !szukanaKat.equals(0))
		{
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
				//System.out.println("kup teraz");
			}
			else
				czyKupTeraz.add(false);
		}
		if(szukanaKat != null && !szukanaKat.equals(0))
		{
			Kategoria k = kategorieService.getKategory(szukanaKat);
			model.addAttribute("szukanaKat", k.getNazwa());
		}else{
			model.addAttribute("szukanaKat", "Wszystkie");
		}
		
		if(cenaOd != null)
		{
			model.addAttribute("cenaOd", cenaOd);
		}
		if(cenaDo != null)
		{
			model.addAttribute("cenaDo", cenaDo);
		}
		if(kupLic != null)
		{
			for(int i=0;i<kupLic.length; i++)
			{
				if(kupLic[i].equals("kupTeraz"))
				{
					model.addAttribute("kupCheck", true);
				}else{
					model.addAttribute("licCheck", true);
				}
			}
		}
		if(podkat != null)
		{
			model.addAttribute("checkPod", podkat);
		}
		
		model.addAttribute("szukaneProdukty", produkty);
		model.addAttribute("czyKupTeraz", czyKupTeraz);
		model.addAttribute("podkategorie", podkategorie);
		model.addAttribute("hasPodkategory", hasPodkategory);
		model.addAttribute("szukKat", szukanaKat);
		model.addAttribute("szukFraz", szukanaFraza);
		model.addAttribute("check", new CheckBoxLicKup());
		
		//System.out.println(podkategorie.size());
		
		
		return "szukaneProdukty";
	}
	@RequestMapping(value="/podkat.json", method = RequestMethod.GET, params="katId")
	public @ResponseBody JsonKat znajdzPodkat(Model model, @RequestParam Integer katId) 
	{
		List<Kategoria> podKat = kategorieService.getPodKategory(katId);
		JsonKat jso = new JsonKat();
		jso.generateKategorie(podKat);
		
		return jso;
	}
	
}
