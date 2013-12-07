package com.cebul.jez.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cebul.jez.entity.Kategoria;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.ProduktyKupTeraz;
import com.cebul.jez.entity.ProduktyLicytuj;
import com.cebul.jez.entity.User;
import com.cebul.jez.entity.Zdjecie;
import com.cebul.jez.service.KategorieService;
import com.cebul.jez.service.ProduktyService;
import com.cebul.jez.useful.JsonLicytacja;
import com.cebul.jez.useful.JsonObject;
import com.cebul.jez.useful.ShoppingCart;

/**
 * 
 * @author Mateusz
  *	Klasa działa jako kontroler w modelu MVC
 *	używa mechanizmu DI do wstrzykiwania zależnosći
 *	obsługuje żądania z ścieżki "/produkty/*"
 *	ponadto zawiera metody użożliwijace wyswietlenie multimediów (obrazów)
 *	przechowywanych w bazie danych
 */
@Controller
public class ProduktyController 
{
	@Autowired
	private ProduktyService produktyService;
	
	@Autowired
	private KategorieService kategorieService;
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	/**
	 * powoduje wyświetlenie szczegółowych informacji o produkcie 
	 * 
	 * @param produktId identyfiaktor jednoznaczenie identyfikujacy dany produkt
	 * @param model referencja do modelu
	 * @param session	 referencja do obiektu sesji
	 * @return zwraca logiczną nazwę widoku
	 */
	@RequestMapping(value = {"/produkty/{produktId}/"}, method = RequestMethod.GET)
	public String infoProdukt(@PathVariable Integer produktId, Model model, HttpSession session){
		
		Produkty produkt = produktyService.getProdukt(produktId);
		//Collection<Zdjecie> zdjecia = produkt.getZdjecia();
		Collection<Integer> zdjecia = produktyService.getZdjeciaId(produkt); 
		
		Kategoria katGlow = kategorieService.getMainKategory(produkt.getKategorie().getParentKategory());
		String path = katGlow.getNazwa()+" >>> "+produkt.getKategorie().getNazwa();
		List<Kategoria> podkategorie = kategorieService.getPodKategory(katGlow.getId());
		
		boolean czySprzedane = false;
		
		boolean czyKupTeraz = false;
		if(produkt instanceof ProduktyKupTeraz)
		{
			czyKupTeraz = true;
			czySprzedane = ((ProduktyKupTeraz) produkt).isKupiony();
		}
		
		boolean ktosLicytuje = false;
		boolean czyJaWygrywam = false;
		int diffInDays = 0;
		if(produkt instanceof ProduktyLicytuj)
		{		
			User u = ((ProduktyLicytuj) produkt ).getAktualnyWlasciciel();
			if( u != null)
			{
				ktosLicytuje = true;
				User me =  (User) session.getAttribute("sessionUser");
				if(me != null && u.getId().equals(me.getId()) )
				{
					czyJaWygrywam = true;
				}
			}
			Date now = new Date();
			diffInDays = (int)( (((ProduktyLicytuj) produkt).getDataZakonczenia().getTime() - now.getTime()) / (1000 * 60 * 60 * 24) );
			diffInDays++;
			if(diffInDays >= 0)
				czySprzedane = true;
			//System.out.println("roznica = "+diffInDays+1);
		}
		
		model.addAttribute("podkategorie", podkategorie);
		model.addAttribute("produkt", produkt);
		model.addAttribute("path", path);
		model.addAttribute("zdjecia", zdjecia);
		model.addAttribute("czyKupTeraz", czyKupTeraz);
		model.addAttribute("ktosLicytuje", ktosLicytuje);
		model.addAttribute("czyJaWygrywam", czyJaWygrywam);
		model.addAttribute("roznicaDat", diffInDays);
		model.addAttribute("czySprzedane", czySprzedane);
		//model.addAttribute("prod",  new Produkty());
		
		return "produkt";
	}
	/**
	 * 
	 * @param model refenencja do obiektu modelu
	 * @return zwraca logiczna nazwe widoku
	 */
	@RequestMapping(value = {"/produkty"})
	public String mainProdukty(Model model)
	{
		return "produkt";
	}
	
	/**
	 * dodaje obiekt do koszyka (koszyk to bean o zasiegu sessji)
	 * @param produkt produkt który ma zostać dodany do koszyka
	 * @param model refernecja do obiektu model
	 * @return przekierowuje na stronę koszyka
	 */
	@RequestMapping(value = {"/produkty/addToCart/"}, method=RequestMethod.POST)
	public String addToCart(Produkty produkt, Model model)
	{
		boolean prodWKoszyku = false;
		for(Produkty p : shoppingCart.getItems())
		{
			if(p.getId().equals(produkt.getId()))
			{
				prodWKoszyku = true;
			}
		}
		if(!prodWKoszyku)
		{
			Produkty p = produktyService.getProdukt(produkt.getId());
			shoppingCart.addItem(p);
		}
		
		return "redirect:/koszyk";
	}
	/**
	 * umożliwia podbicie ceny licytacji przez uzytkownika
	 * 
	 * @param produkt produkt który jest obiektem licytacji
	 * @param model	referencja do modelu
	 * @param session	referencja do obiektu sesji
	 * @return przekierowuje na strone produktu
	 */
	@RequestMapping(value = {"/produkty/licytuj/"}, method=RequestMethod.POST)
	public String podbijCeneLicytuj(Produkty produkt, Model model, HttpSession session)
	{
		//System.out.println(produkt.getId());
		ProduktyLicytuj pl = new ProduktyLicytuj();
		pl.setId(produkt.getId());
		pl.setCena(produkt.getCena());
		User me =  (User) session.getAttribute("sessionUser");
		pl.setAktualnyWlasciciel(me);
		produktyService.updateLicytacja(pl);
		
		return "redirect:/produkty/"+produkt.getId()+"/";
	}
	/**
	 * sprawdza dostepnosc produktu w bazie dnaych
	 * @param model refernecja do obiektu modelu
	 * @param idProd	identyfikator produkt
	 * @return zwraca obiekt produktu
	 */
	@RequestMapping(value="/produkty/sprawdzDostepnosc.json", method = RequestMethod.GET, params="idProd")
	public @ResponseBody ProduktyLicytuj sprawdzAukcje(Model model, @RequestParam Integer idProd) 
	{
		ProduktyLicytuj r = (ProduktyLicytuj) produktyService.getProdukt(idProd);
		return r;
	}
	/**
	 * umożliwia wyświetlenie obrazka na stronie klienta
	 * aby metoda obsługiwała wyswietlanie obrazka, atrybut src znacznika img 
	 * musi mieć postać src="/prodimag/{prodimageId}"
	 *  
	 * @param prodimageId identyfikator obrazu
	 * @param response	referencja do obiektu response
	 * @param request referencja do obiektu requet
	 * @param session	referencja do obiektu sessji
	 * @param model	referencja do obiektu modelu
	 * @throws IOException zgłasza wyjatek podczas nieudanej próby wyświetlenia obrazka
	 */
	@ResponseBody
	@RequestMapping(value = "/prodimag/{prodimageId}", method = RequestMethod.GET, produces="prodimag/*")
	public void getProdImage(@PathVariable Integer prodimageId, HttpServletResponse response, HttpServletRequest request, 
			HttpSession session, Model model) throws IOException {
	    response.setContentType("image/jpeg");
	    
	    Zdjecie requestedImage = produktyService.getZdjecie(prodimageId);

		    InputStream in= new ByteArrayInputStream(requestedImage.getZdjecie()); 
		    //System.out.println("input: "+in);
		    if (in != null) {
		        IOUtils.copy(in, response.getOutputStream());  
		    }
	   
	}
}
