package com.cebul.jez.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cebul.jez.entity.Komentarz;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.ProduktyKupTeraz;
import com.cebul.jez.entity.User;
import com.cebul.jez.service.KomentarzeService;
import com.cebul.jez.service.ProduktyService;
import com.cebul.jez.service.ZamowienieService;

/**
 * 
 * @author Mateusz Cebularz
 * Klasa pelniaca zadanie kontrolera w modelu MVC
 * Jej zadaniem jest obsługa rządań z ścieżki "/komentarze/*"
 * Wykorzystuje mechanizm DI do wstrzykiwania zależnosći
 * Wstrzykiwane Beany: ProduktyService, ZamowienieService, KomentarzeService
 *
 */
@Controller
public class KomentarzController 
{
	@Autowired
	private ProduktyService produktyService;
	
	@Autowired
	private ZamowienieService zamowienieService;
	
	@Autowired
	private KomentarzeService komentarzeService;

	/**
	 * umożliwia wyświetlenie formularza za pomocą którego możemy dodać komentarz.
	 * sprawdza czy mamy możliwość dodania komentarza
	 * @param produktId identyfikator produktu
	 * @param model obiekt model
	 * @param session obiekt sesji
	 * @return zwraca logiczną nazwe widoku, w przypadku gdy nie mamy uprawnień do wystawienia komentarza
	 * przekierowuje na odpowiednia stronę
	 */
	@RequestMapping(value = "/komentarze/dodajKomentarz/{produktId}/", method = RequestMethod.GET)
	public String showFormDodajKomentarz(@PathVariable Integer produktId, Model model,  HttpSession session) 
	{
		User me = (User) session.getAttribute("sessionUser");
		boolean ok = zamowienieService.getProduktZZamowienia(me, produktId);
		if(ok)
		{
			Produkty p = produktyService.getProdukt(produktId);
			Boolean czyKupTeraz = false;
			
			if(p instanceof ProduktyKupTeraz)
				czyKupTeraz = true;
			
			User wlasciciel = p.getUser();
	
			model.addAttribute("element", p);
			model.addAttribute("czyKupTeraz", czyKupTeraz);
			model.addAttribute("wlas", wlasciciel);
			
			return "dodajKomentarz";
		}
		return "redirect:/mojekonto/wystawKomentarz/";
	}
	/**
	 * zapisuje komentarz w bazie
	 * 
	 * @param idProduktu identyfikator produjtu którego dotyczy komentarz
	 * @param komentarz właściwa treść komentarza
	 * @param ocena ocena sprzedaży w skali (1-5)
	 * @param model obiekt model
	 * @param session obiekt sesji
	 * @return przekierowuje na odobowiednią stronę
	 */
	@RequestMapping(value = "/komentarze/zapiszKomentarz/", method = RequestMethod.POST)
	public String zapiszKomentarz(@RequestParam(value="idProduktu", required=true) Integer idProduktu, 
			@RequestParam(value="komentarz", required=true) String komentarz,  
			@RequestParam(value="ocena", required=true) Integer ocena, Model model,  HttpSession session) 
	{
		User me = (User) session.getAttribute("sessionUser");
		zamowienieService.dodajKomentarz(idProduktu, komentarz, ocena, me);
		return "redirect:/mojekonto/wystawKomentarz/";
	}
	/**
	 * wyświetla komentarze które otrzymał użytkownik
	 * 
	 * @param model obiekt model
	 * @param session obiet sesji
	 * @return zwraca logiczną nazwę widoku
	 */
	@RequestMapping(value = "/mojekonto/otrzymaneKomentarze/")
	public String otrzymaneKomentarze(Model model,  HttpSession session) 
	{
		User me = (User) session.getAttribute("sessionUser");
		List<Komentarz> kom = komentarzeService.pobierzOtrzymaneKomentarze(me);
		model.addAttribute("komentarze", kom);
		
		return "otrzymaneKomentarze";
		
	}
	/**
	 * wyświetla komentarze które wystawił użytkownik
	 * 
	 * @param model obiekt model
	 * @param session obiet sesji
	 * @return zwraca logiczną nazwę widoku
	 */
	@RequestMapping(value = "/mojekonto/wystawioneKomentarze/")
	public String wystawioneKomentarze(Model model,  HttpSession session) 
	{
		User me = (User) session.getAttribute("sessionUser");
		List<Komentarz> kom = komentarzeService.pobierzWystawioneKomentarze(me);
		model.addAttribute("komentarze", kom);
		
		return "wystawioneKomentarze";
	}
}
