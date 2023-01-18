package it.polito.tdp.nyc.model;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Adiacenza {
	
	private String c1;
	private String c2;
	private LatLng peso1;
	private LatLng peso2;
	private double pesoArco;
	
	public Adiacenza(String c1, String c2, LatLng peso1, LatLng peso2) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.peso1 = peso1;
		this.peso2 = peso2;
		this.pesoArco= calcolaDistanza();
	}
	
	private double calcolaDistanza() {
		double distanza=0;
		distanza=LatLngTool.distance( peso1,peso2, LengthUnit.KILOMETER);
		return distanza;
	}

	public String getC1() {
		return c1;
	}
	public String getC2() {
		return c2;
	}
	public LatLng getPeso1() {
		return peso1;
	}

	public LatLng getPeso2() {
		return peso2;
	}

	public double getPesoArco() {
		return pesoArco;
	}
	
	
	
	
}
