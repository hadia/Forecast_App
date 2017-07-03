package com.eu.parent.hadia.weatherapp.GetWeatherResponse;

import com.eu.parent.hadia.weatherapp.common.ResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class City extends ResponseModel {

	@JsonProperty("country")
	private String country;

	@JsonProperty("coord")
	private Coord coord;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	@JsonProperty("population")
	private int population;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setCoord(Coord coord){
		this.coord = coord;
	}

	public Coord getCoord(){
		return coord;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPopulation(int population){
		this.population = population;
	}

	public int getPopulation(){
		return population;
	}

	@Override
 	public String toString(){
		return 
			"City{" + 
			"country = '" + country + '\'' + 
			",coord = '" + coord + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",population = '" + population + '\'' + 
			"}";
		}
}