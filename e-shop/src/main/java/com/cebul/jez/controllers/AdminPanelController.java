package com.cebul.jez.controllers;

import java.awt.image.BufferedImage;
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
import com.cebul.jez.model.UserDao;
import com.cebul.jez.service.KategorieService;
import com.cebul.jez.service.ProduktyService;
import com.cebul.jez.service.UserService;
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
	
	
	/**
	 * Funkcja zwaraca główny widok panelu
	 * @return panel - logiczna nazwa widoku
	 */
	@RequestMapping(value = "/panel/")
	public String getGlownyWidok()
	{
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
	public String addAdmin(Model model)
	{
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
		
	  userService.setAdmin(addLogin);
				
		return "redirect:/admin_home/";
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
	@RequestMapping(value= "/panel/dodajKategorie", method= RequestMethod.POST)
	public String addKatFromForm(@Valid Kategoria kategoria, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			return "/dodajKategorie";
			
		}
		
		boolean add = kategorieService.addKategoria(kategoria);
		
		if (!add)
		{
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
	
	@RequestMapping(value = "/panel/statystyki")
	public String stats(Model model)
	{
		return "statystyki";
	}
	
	@RequestMapping(value= "/panel/edytujKategorie/")
	public String edytujKategorieForm(Model model, HttpSession session)
	{
		//Kategoria kat = (Kategoria) session.getAttribute("kategoryList");
		// test
		// System.out.println("aaa=" + kat.getId());
		 
		//model.addAttribute("kategoria", kat);
		
		//model.addAttribute(new Kategoria());
		
		//List<Kategoria> kat = kategorieService.getMainKategory();
		//model.addAttribute("kategoryListModel", kat);
		
//komentaraz dla jezyka
		
		return "edytujKategorie";
	}
	
	@RequestMapping(value = "/panel/edytujKategorie/zapisz/",  method=RequestMethod.POST)
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
		
		return "/admin_home/panel/";
		
	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
    }
}

