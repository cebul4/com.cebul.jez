package com.cebul.jez.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User implements Serializable
{
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	
	@Column(name="Login")
	@Size(min=3, max=20, message="Nazwa użytkownika musi skladąc się od 3 do 20 znaków.")
	@Pattern(regexp="^[a-zA-Z0-9]+$" , message="Nie poprawna anzwa użytkownika.")
	private String login;
	
	@Column(name="Pass")
	@Size(min=6, max=20, message="Hasło nie moż ebyć krótsze niż 6 znaków")
	private String pass;
	
	@Column(name="Imie")
	@Size(min=3, max=20, message="Imię musi skladąc się od 3 do 20 znaków.")
	@Pattern(regexp="^[a-zA-Z]+$" , message="Nie poprawne imie.")
	private String imie;
	
	@Column(name="Nazwisko")
	@Size(min=3, max=20, message="Nazwisko musi skladąc się od 3 do 20 znaków.")
	@Pattern(regexp="^[a-zA-Z0-9]+$" , message="Nie poprawne nazwisko.")
	private String nazwisko;
	
	@Column(name="Email")
	@Pattern(regexp="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}" , message="Nie poprawny adres email.")
	private String email;
	
	@Column(name="Plec")
	@Size(min=1, max=2, message="Wybierz płeć.")
	private String plec;
	
	@Column(name="Rok")
	@NotNull
	private Integer rok;
	
	@Column(name="Miesiac")
	@NotNull
	private Integer miesiac;
	
	@Column(name="Dzien")
	@NotNull(message="Wybierz poprawna date.")
	private Integer dzien;
	
	@Column(name="Ranga")
	private String ranga;
	
	@Column(name="Enabled")
	private Integer enabled;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="Adres")
	private Adresy adres;
	
	public Adresy getAdres() {
		return adres;
	}

	public void setAdres(Adresy adres) {
		this.adres = adres;
	}

	public User()
	{
		/*
		setLogin("test1");
		setPass("test1");
		setImie("lolek");
		setNazwisko("lolek");
		setEmail("lsdlkla@wp.pl");
		setMiejscowosc("rusocie");
		setKod_pocztowy("32-071");
		setPlec("m");
		setDzien(20);
		setMiesiac(10);
		setRok(1991);
		*/
		setRanga("user");
		setEnabled(0);
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPlec() {
		return plec;
	}

	public void setPlec(String plec) {
		this.plec = plec;
	}

	public Integer getRok() {
		return rok;
	}

	public void setRok(Integer rok) {
		this.rok = rok;
	}

	public Integer getMiesiac() {
		return miesiac;
	}

	public void setMiesiac(Integer miesiac) {
		this.miesiac = miesiac;
	}

	public Integer getDzien() {
		return dzien;
	}

	public void setDzien(Integer dzien) {
		this.dzien = dzien;
	}

	public String getRanga() {
		return ranga;
	}

	public void setRanga(String ranga) {
		this.ranga = ranga;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
		
	}
	
	
	
}
