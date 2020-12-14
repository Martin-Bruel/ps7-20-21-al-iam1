package com.example.polyville2.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
	Map<String,Float> menuItems;
	List<String> items;
	
	public Menu() {
		items=new ArrayList<String>();
		menuItems = new HashMap<String,Float>();
	}
}
