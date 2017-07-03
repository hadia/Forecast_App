package com.eu.parent.hadia.weatherapp.GetWeatherResponse;

import com.eu.parent.hadia.weatherapp.common.ResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Temp extends ResponseModel {

	@JsonProperty("min")
	private double min;

	@JsonProperty("max")
	private double max;

	@JsonProperty("eve")
	private double eve;

	@JsonProperty("night")
	private double night;

	@JsonProperty("day")
	private double day;

	@JsonProperty("morn")
	private double morn;

	public void setMin(double min){
		this.min = min;
	}

	public double getMin(){
		return min;
	}

	public void setMax(double max){
		this.max = max;
	}

	public double getMax(){
		return max;
	}

	public void setEve(double eve){
		this.eve = eve;
	}

	public double getEve(){
		return eve;
	}

	public void setNight(double night){
		this.night = night;
	}

	public double getNight(){
		return night;
	}

	public void setDay(double day){
		this.day = day;
	}

	public double getDay(){
		return day;
	}

	public void setMorn(double morn){
		this.morn = morn;
	}

	public double getMorn(){
		return morn;
	}

	@Override
 	public String toString(){
		return 
			"Temp{" + 
			"min = '" + min + '\'' + 
			",max = '" + max + '\'' + 
			",eve = '" + eve + '\'' + 
			",night = '" + night + '\'' + 
			",day = '" + day + '\'' + 
			",morn = '" + morn + '\'' + 
			"}";
		}
}