package com.cebul.jez.controllers;

import java.awt.image.BufferedImage;
import java.beans.PropertyEditorSupport;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.util.FileUtils;
import org.aspectj.util.FileUtil;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
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
import com.cebul.jez.entity.Zamowienie;
import com.cebul.jez.entity.Zdjecie;
import com.cebul.jez.model.UserDao;
import com.cebul.jez.service.KategorieService;
import com.cebul.jez.service.ProduktyService;
import com.cebul.jez.service.UserService;
import com.cebul.jez.service.ZamowienieService;
import com.cebul.jez.service.ZdjecieService;


/**
 * 
 * @author Robert
 *Klasa pelni rolę kontrolera w modelu MVC
 *Kontroler panelu administratora 
 *
 */
@Controller("AdminPanelController")
public class AdminPanelController 
{

	@Autowired
	ServletContext context;
	
	@Autowired
	private KategorieService kategorieService;
	
	@Autowired
	private ProduktyService produktyService;
	
	@Autowired
	private ZdjecieService zdjecieService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ZamowienieService zamowienieService;
	
	
	/**
	 * Funkcja zwaraca główny widok panelu
	 * @return panel - logiczna nazwa widoku
	 */
	@RequestMapping(value = "/panel/")
	public String getGlownyWidok(Model model)
	{
		List<String> user = userService.getAllUsers();
		System.out.println("list user size: " + user.size());
		model.addAttribute("blockUser", user);
		
		return "panel";
	}
	
	
	/**
	 * @author Robert
	 * Funkcja dodawania admina
	 * Obsługuje żądania inne niż POST
	 * @param model - dostep do modelu
	 * @return zwraca logiczną nazwę widoku
	 */
	@RequestMapping(value = "/panel/dodajAdmina")
	public String addAdmin(Model model, HttpSession session)
	{
		List<String> str = userService.getUsers();
		System.out.println("Rozmiar loginow: " + str.size());
		model.addAttribute("loginy", str);
		return "/dodajAdmina";
	}
	
	/**
	 * @author Robert
	 * Funckja obsługuje dodoawanie admina poprzez formularz
	 * Dodawanie admina poprzez podanie loginu
	 * @param model - dostęp do modelu
	 * @param addLogin - zmienna przechowująca login podany z formularza
	 * @param session - atrybut sesji
	 * @return - po poprawnym dodaniu admina przekierowuje do strony głównej admina
	 */
	@RequestMapping(value = "/panel/dodajAdmina", method=RequestMethod.POST)
	public String addAdminFromLogin(Model model, @RequestParam(value="login") String addLogin, HttpSession session)
	{
		
	  
		  
		  if(addLogin.isEmpty())
		  {
		  return "redirect:/panel/dodajAdmina";
		  }
	 
		  
	  userService.setAdmin(addLogin);
	  
				
		return "redirect:/panel/";
	}
	
	/**
	 * @author Robert
	 * Funkcja dodawanie Kategorii
	 * Obsługa metod innych niż POST
	 * @param model - dodaje do modelu nowy Obiekt Kategoria
	 * @return zwraca logiczną nazwę widoku
	 * 
	 */
	@RequestMapping(value= "/panel/dodajKategorie")
	public String addKatForm(Model model)
	{
		model.addAttribute(new Kategoria());
		model.addAttribute("parent", kategorieService.getAll());
		
		return "dodajKategorie";
	}
	
	/**
	 * @author Robert
	 * Dodawanie kategorii poprzez formularz
	 * obsługa żądań typu POST
	 * 
	 * @param kategoria - obiekt typu Kategoria do przetworzenia z formularza
	 * @param bindingResult - zwraca błędy przesyłu
	 * @param model - dostęp do modelu
	 
	 * @return zwraca logiczna nazwę widoku
	 */
	@RequestMapping(value= "/panel/dodajKategorie/", method= RequestMethod.POST)
	public String addKatFromForm(@Valid Kategoria kategoria, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			
			System.out.println(bindingResult.toString());
			System.out.println("bindingResult");
			
			return "/dodajKategorie";
			
		}
	
		boolean add = kategorieService.addKategoria(kategoria);
		
		if (!add)
		{
			System.out.println(add);
			System.out.println("!add");
			return "/dodajKategorie";
		}
		
		
		return "redirect:/panel/";
	}
	
	@RequestMapping(value = "/panel/dodajProdukt/zakoncz/")
	public String zakonczaDodawanieZdj(HttpSession session)
	{
		session.removeAttribute("prod");
		return "redirect:/panel/";
	}
	
	/**
	 * @author Robert
	 * 
	 * Funkcja odpowiada za edycje kategorii
	 * 
	 * @param model - dostęp do obiektu modelu
	 * @param session - dośtęp do obiektu sesji
	 * @return - zwraca logiczną nazwę widoku
	 */
	@RequestMapping(value= "/panel/edytujKategorie")
	public String edytujKategorie(Model model,HttpSession session)
	{
		
		model.addAttribute("katList", kategorieService.getAll());
		
		
		return "edytujKategorie";
	}
	@RequestMapping(value= "/panel/edytujKategorieForm")
	public String edytujKategorieForm(@RequestParam(value="katId") Integer katId, Model model,HttpSession session)
	{
	
		model.addAttribute("kategoria", kategorieService.getKategory(katId));
		model.addAttribute("parent", kategorieService.getAll());
		
		return "edytujKategorieForm";
	}
	
	
	
	/**
	 * @author Robert
	 * 
	 * Funkcja wyświetla formularz edycji kategorii
	 * 
	 * @param kat - obiekt klasy Kategoria. Uzyskujemy dostęp do danych w tabeli Kategorie bazy danych
	 * @param bindingResult - obiekt zwraca błędy w razie nie powdzenia operacji
	 * @param model - dostęp do obiektu modelu
	 * @param session - dostęp do obiektu sesji
	 * @return w razie powodzenia edycji przekierowuje do panelu admina, w przeciwnym wypadku wraca do formularza edycji
	 */
	@RequestMapping(value = "/panel/edytujKategorieForm/zapisz",  method=RequestMethod.POST)
	public String updateKategoria(@Valid Kategoria kat, BindingResult bindingResult, Model model, HttpSession session)
	{
		if(bindingResult.hasErrors())
		{
			
			return "redirect:/panel/edytujKategorie/";
		}
		
		boolean updateOk = kategorieService.updateCategory(kat);
		
		if (!updateOk)
		{
			return "redirect: /panel/edytujKategorie/";
		}
		
		return "redirect:/panel/";
		
	}
	
	/**
	 * @author Robert
	 * Funkcja blokowania użytkownika
	 * Obsługuje żądania GET
	 * @param model - dostep do modelu
	 * @return zwraca logiczną nazwę widoku
	 */
	@RequestMapping(value = "/panel/blockUser")
	public String block(Model model, HttpSession session)
	{
		List<String> user = userService.getAllUsers();
		System.out.println("block user size: " + user.size());
		model.addAttribute("blockUser", user);
		//session.setAttribute("blockUser", user);
		
		return "/blockUser";
	}
	
	
	/**
	 * @author Robert
	 * 
	 * Funkcja blokuje użytkownika o ID podanym z formularza
	 * Obsługuje żądania POST
	 * @param model - dostęp do atrybutów modelu
	 * @param id - identyfikator użytkownika, który ma zostać zablokowany
	 * @param session - dostęp do obiektów sesji
	 * @return przekierowuje do panelu admina
	 */
	@RequestMapping(value = "/panel/blockUser", method=RequestMethod.POST)
	public String blockUser(Model model, @RequestParam(value="id") Integer id, HttpSession session, HttpServletRequest request)
	{
	  if (request.getParameter("block") != null)
	  {
	  userService.blockUser(id);
	  			
		
	  }
	  else if (request.getParameter("unblock") != null)
	  {
		  userService.unblockUser(id);
	  }
	  return "redirect:/panel/";
	}
	
	@RequestMapping(value = "/panel/usunProdukt")
	public String usun(Model model)
	{
		List<String> prod = produktyService.getAllProducts();
		System.out.println("usun produkt size: " + prod.size());
		model.addAttribute("produkty", prod);
		
		return "/usunProdukt";
	}
	
	@RequestMapping(value = "/panel/usunProdukt", method = RequestMethod.POST)
	public String usunProdukt(@RequestParam(value="id") Integer produktId, Model model, HttpSession session){
		produktyService.usunProdukt(produktId);
		
		return "redirect:/panel/";
	}
	
	@RequestMapping(value= "/panel/zamowienia")
	public String getOrders(Model model)
	{
		List<Zamowienie> z = zamowienieService.getZamowienie();
		System.out.println("list zamowienia size: " + z.size());
		model.addAttribute("zamowienia", z);
		
		return "zamowienia";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
    }
	
	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	{
		binder.registerCustomEditor(Kategoria.class,"parentKategory" ,new PropertyEditorSupport()
		{
			public void setAsText(String text) {
				Kategoria k = kategorieService.getKategory(Integer.parseInt(text));
				
				setValue(k);
			}
			
			
		});
	}

}