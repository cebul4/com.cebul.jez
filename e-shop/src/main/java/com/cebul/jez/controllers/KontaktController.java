package com.cebul.jez.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cebul.jez.entity.User;

@Controller
public class KontaktController 
{
	@RequestMapping(value = "/kontakt")
	public String regForm(Model model,  HttpSession session) 
	{
		return "kontakt";
	}
}
