package com.cebul.jez.flows;

import java.io.Serializable;

public class Tmp implements Serializable
{
	private String platnosc;
	private String dokument;
	
	public Tmp()
	{
		
	}

	public String getPlatnosc() {
		return platnosc;
	}

	public void setPlatnosc(String platnosc) {
		this.platnosc = platnosc;
	}

	public String getDokument() {
		return dokument;
	}
	public void setDokument(String dokument) {
		this.dokument = dokument;
	}
	
	

}
