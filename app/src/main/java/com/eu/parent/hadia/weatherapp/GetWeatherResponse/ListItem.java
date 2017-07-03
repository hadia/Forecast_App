package com.eu.parent.hadia.weatherapp.GetWeatherResponse;

import java.util.List;

import com.eu.parent.hadia.weatherapp.common.ResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ListItem extends ResponseModel {

	@JsonProperty("dt")
	private long dt;

	@JsonProperty("temp")
	private Temp temp;

	@JsonProperty("deg")
	private int deg;

	@JsonProperty("weather")
	private List<WeatherItem> weather;

	@JsonProperty("humidity")
	private int humidity;

	@JsonProperty("pressure")
	private double pressure;

	@JsonProperty("clouds")
	private int clouds;

	@JsonProperty("speed")
	private double speed;

	public void setDt(long dt){
		this.dt = dt;
	}

	public long getDt(){
		return dt;
	}

	public void setTemp(Temp temp){
		this.temp = temp;
	}

	public Temp getTemp(){
		return temp;
	}

	public void setDeg(int deg){
		this.deg = deg;
	}

	public int getDeg(){
		return deg;
	}

	public void setWeather(List<WeatherItem> weather){
		this.weather = weather;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public void setHumidity(int humidity){
		this.humidity = humidity;
	}

	public int getHumidity(){
		return humidity;
	}

	public void setPressure(double pressure){
		this.pressure = pressure;
	}

	public double getPressure(){
		return pressure;
	}

	public void setClouds(int clouds){
		this.clouds = clouds;
	}

	public int getClouds(){
		return clouds;
	}

	public void setSpeed(double speed){
		this.speed = speed;
	}

	public double getSpeed(){
		return speed;
	}

	@Override
 	public String toString(){
		return 
			"ListItem{" + 
			"dt = '" + dt + '\'' + 
			",temp = '" + temp + '\'' + 
			",deg = '" + deg + '\'' + 
			",weather = '" + weather + '\'' + 
			",humidity = '" + humidity + '\'' + 
			",pressure = '" + pressure + '\'' + 
			",clouds = '" + clouds + '\'' + 
			",speed = '" + speed + '\'' + 
			"}";
		}
}