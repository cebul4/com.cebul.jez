package com.cebul.jez.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cebul.jez.entity.Kategoria;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.ProduktyKupTeraz;
import com.cebul.jez.entity.ProduktyLicytuj;
import com.cebul.jez.entity.User;
import com.cebul.jez.entity.Zdjecie;
import com.cebul.jez.service.KategorieService;
import com.cebul.jez.service.ProduktyService;
import com.cebul.jez.useful.CheckboxZdj;
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
			if(diffInDays < 0)
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
	public @ResponseBody JsonLicytacja sprawdzAukcje(Model model, @RequestParam Integer idProd) 
	{
		ProduktyLicytuj r = (ProduktyLicytuj) produktyService.getProdukt(idProd);
		//JsonLicytacja json = new JsonLicytacja();
		//json.generateLicytacja()
		//System.out.println(r.getUser().getId());
		return new JsonLicytacja(r);
		//return r;
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
	/**
	 * mapuje /produkty/usun/{produktId}/
	 * usuwa produkt o podanym id z koszyka
	 * @param produktId identyfikator produktu
	 * @param model obiekt modelu
	 * @param session obiekt sesji
	 * @return zwraca logiczna nazwe widoku
	 */
	@RequestMapping(value = {"/produkty/usun/{produktId}/"}, method = RequestMethod.GET)
	public String usunProduktZBazy(@PathVariable Integer produktId, Model model, HttpSession session){
		produktyService.deleteProdukt(produktId);
		return "redirect:/mojekonto/wystawioneProdukty/";
	}
	/**
	 * mapuje /produkty/edytuj/{produktId}/
	 * wyswietla dane dotyczące produkty który chcemy edytować. Sprawdza czy mamy prawo do edycji danego produktu 
	 * (musimy być właścicielami)
	 * 
	 * @param produktId identyfikator produktu
	 * @param model obiekt modelu
	 * @param session obiekt sesji
	 * @return zwraca logiczna nazwę widoku
	 */
	@RequestMapping(value = {"/produkty/edytuj/{produktId}/"}, method = RequestMethod.GET)
	public String edytujProduktWBazie(@PathVariable Integer produktId, Model model, HttpSession session){
		
		User me = (User) session.getAttribute("sessionUser");
		Produkty produkt = produktyService.getProdukt(produktId);
		if(me != null && produkt.getUser().getId().equals(me.getId()))
		{
			//Collection<Zdjecie> zdjecia = produkt.getZdjecia();
			List<Integer> zdjecia = produktyService.getZdjeciaId(produkt); 
			
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
					//User me =  (User) session.getAttribute("sessionUser");
					if(me != null && u.getId().equals(me.getId()) )
					{
						czyJaWygrywam = true;
					}
				}
				Date now = new Date();
				diffInDays = (int)( (((ProduktyLicytuj) produkt).getDataZakonczenia().getTime() - now.getTime()) / (1000 * 60 * 60 * 24) );
				diffInDays++;
				if(diffInDays < 0)
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
			model.addAttribute("katGlow", katGlow.getId());
			model.addAttribute("podKat", produkt.getKategorie().getId());
			model.addAttribute("check", new CheckboxZdj());
			//model.addAttribute("prod",  new Produkty());
			return "edytujProdukt";
		}
		return "nieJestesSprzedajacym";
	}
	/**
	 * aktualizuje dane w bazie dotyczące produktu
	 * 
	 * @param produkt obiekt produktu który ma zostać zaktualizowany
	 * @param bindingResult
	 * @param model obiekt modelu
	 * @param session obiekt sesji
	 * @return przekierowuje na odpowiednią stronę
	 */
	@RequestMapping(value = "/produkty/updateProdukt/",  method=RequestMethod.POST)
	public String updateKontoUsera(@Valid Produkty produkt, BindingResult bindingResult, Model model, HttpSession session)
	{
		
		if(bindingResult.hasErrors())
		{
			System.out.println("nie przechodziiiii");
			System.out.println(bindingResult.getErrorCount());
			System.out.println(bindingResult.toString());
			return "redirect:/produkty/edytuj/"+produkt.getId()+"/";
		}
		//System.out.println(produkt.getId());
		//produkt.setNazwa("");
		
		
		//boolean itsDone = userService.updateUser(user);
		boolean itsDone = produktyService.updateProduktInfo(produkt);
		if(!itsDone)
		{
			return "redirect:/produkty/edytuj/"+produkt.getId()+"/";
		}
		//session.setAttribute("sessionUser", arg1);
		return "redirect:/mojekonto/wystawioneProdukty/";
	}
	/**
	 * usówa zdjęcia z bazy danych nalezące do danego produktu
	 * 
	 * @param check zawiera identyfiaktory zdjec ktore majac zostać usuniete
	 * @param idProd indentyfiaktor produktu
	 * @param model obiekt model
	 * @param session obiekt sesji
	 * @return przekierowuje na odpowiedni strone edycji produktu
	 */
	@RequestMapping(value = "/produkty/updateProdukt/usunZdjecie/",  method=RequestMethod.POST)
	public String usunZdjecieZProduktu(CheckboxZdj check, @RequestParam(value="idProd", required=false) Integer idProd, Model model, HttpSession session)
	{
		//System.out.println(idProd);
		Integer ch[] = check.getCheckboxs();
		if(ch.length > 0)
		{
			Produkty p = produktyService.getProdukt(idProd);
			p.removeZdj(ch);
			produktyService.updateProdukt(p);
		}
		return "redirect:/produkty/edytuj/"+idProd+"/";
	}
	/**
	 * dodaje zdjecie wysłane przez użytkownika do danego produktu
	 * @param produktId identyfiaktor produktu
	 * @param image wysłane przez użytkownika zdjecie
	 * @param model obiekt modelu
	 * @param session obiekt sesji
	 * @param request obiekt request
	 * @return przekierowuje na odpowiedni strone edycji produktu
	 * @throws Exception zgłaszany podczas błędu w dodawnaiu do bazy
	 */
	@RequestMapping(value = "/produkty/edytuj/dodajZdjecie/", method=RequestMethod.POST)
	public String dodajZdjecieDoProduktu(@RequestParam(value="produktId", required=false) Integer produktId, @RequestParam(value="image", required=false) MultipartFile image, 
			 Model model,  HttpSession session, HttpServletRequest request) throws Exception
	{
		//System.out.println(produktId);
		Produkty p = produktyService.getProdukt(produktId);
		//p.addZdjecie(zdj);
		try{
			if(!image.isEmpty())
			{
				validateImage(image);
				//saveImage(image);
				//saveImageOnHardDrive(image, "H:\\");
				Zdjecie z = returnImage(image);
				p.addZdjecie(z);
				p.setZdjecie(z);
				
				produktyService.updateProdukt(p);
				
			}
		}catch(Exception e){
				System.out.println("przed dodaniem sie wykladam");
				//System.out.println(e.getMessage());
				return "redirect:/produkty/edytuj/"+produktId+"/";
		}
			
		return "redirect:/produkty/edytuj/"+produktId+"/";
	}
	/**
	 * umożliwia sprawdzenie czy uploadowany obraz ma wymagany format, 
	 * w tym przypadku dopuszczalne są obrazu jpg oraz img
	 * 
	 * @param image analizowany obraz
	 * @throws Exception 	zglaszany w przypadku gdy obraz nie jest w określonym formacie
	 */
	public void validateImage(MultipartFile image) throws Exception 
	{
		if(!image.getContentType().equals("image/jpeg") && !image.getContentType().equals("image/png") && !image.getContentType().equals("image/x-png"))
		{
			throw new Exception("Plik nie ejst plikiem JPG. mam nontent= "+image.getContentType());
		}
	}
	/**
	 * przetwarza uploadownay obraz (MultipartFile) do postaci akceptowalnej 
	 * przez bazę danych (postać tablicy)
	 * 
	 * @param image
	 * @return zwraca instancję obiektu Zdjecie
	 */
	public Zdjecie returnImage(MultipartFile image)
	{
		Zdjecie zdj = new Zdjecie();
		try{
			byte[] bFile  = image.getBytes();
			zdj.setZdjecie(bFile);
			//zdjecieService.saveZdjecie(zdj);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return zdj;
	}
	/**
	 * umozliwia przetworzenie dany z formatu string do formatu Data("yyyy-MM-dd")
	 * metoda wykonuje się zanim binding result sprawdzi porpawnosć dancyh podpietego produktu
	 * @param binder
	 */
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
    }
	
}
