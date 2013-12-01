package com.cebul.jez.useful;

import java.io.Serializable;

import com.cebul.jez.entity.ProduktyLicytuj;

public class JsonLicytacja implements Serializable
{
	ProduktyLicytuj prod;

	public ProduktyLicytuj getProd() {
		return prod;
	}

	public void setProd(ProduktyLicytuj prod) {
		this.prod = prod;
	}
	
	public ProduktyLicytuj generateLicytacja()
	{
		return prod;
	}
}
