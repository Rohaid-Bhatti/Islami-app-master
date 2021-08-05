package com.example.islamiapp.API.Model;

import com.google.gson.annotations.SerializedName;

public class RadiosItem{

	@SerializedName("URL")
	private String uRL;

	@SerializedName("Name")
	private String name;

	public void setURL(String uRL){
		this.uRL = uRL;
	}

	public String getURL(){
		return uRL;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"RadiosItem{" + 
			"uRL = '" + uRL + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}