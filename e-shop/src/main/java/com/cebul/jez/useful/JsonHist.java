package com.cebul.jez.useful;

import java.util.List;

import com.cebul.jez.entity.Hist_Wyszuk;

public class JsonHist {
	
	private Hist_Wyszuk historia[];

	public Hist_Wyszuk[] getHistoria() {
		return historia;
	}

	public void setHistoria(Hist_Wyszuk[] historia) {
		this.historia = historia;
	}
	
	public void generateHistoria(List<Hist_Wyszuk> h)
	{
		int size = h.size();
		historia = h.toArray(new Hist_Wyszuk[size]);
	}

}
