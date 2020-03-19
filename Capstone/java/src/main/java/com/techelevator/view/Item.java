package com.techelevator.view;

import java.math.BigDecimal;

public class Item {
	public static final int STARTING_INVENTORY_LEVEL = 5;
	
	
	private String name;
	private String coordinate;
	private String type;
	private BigDecimal price;
	private int stockLevel;
	
	public Item(String coordniate, String name, BigDecimal price, String type) {
		this.coordinate = coordniate;
		this.name = name;
		this.price = price;
		this.type = type;
		this.stockLevel = STARTING_INVENTORY_LEVEL;
	}
	
	
	public int getStockLevel() {
		return stockLevel;
	}
	public void itemPurchased() {
		stockLevel--;
	}
	public String getName() {
		return name;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public String getType() {
		return type;
	}
	public BigDecimal getPrice() {
		return price;
	}
	
	public String makeSound() {
		String sound = "";
		if (getType().equals("Chip")) {
			sound = "\nCrunch Crunch, Yum!";
		} else if (getType().equals("Candy")) {
			sound = "\nMunch Munch, Yum!";
		} else if (getType().equals("Drink")) {
			sound = "\nGlug Glug, Yum!";
		} else if (getType().equals("Gum")) {
			sound = "\nChew Chew, Yum!";
		} else {
			sound = "\nNo item to make sound";
		}
		
		return sound;
	}
	

	

}
