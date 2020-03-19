package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {

	private static final String SOLD_OUT_LABEL = "**SOLD OUT**";
	public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");
	public static BigDecimal totalSales;

	private Map<String, Item> vendingMachineItems;
	private SalesReport salesReport;
	private Audit vendingAudit;
	private Transaction transactions;

	public VendingMachine(String fileName) {
		this.vendingMachineItems = new LinkedHashMap<>();
		this.salesReport = new SalesReport();
		this.vendingAudit = new Audit();
		vendingAudit.createAuditFile();
		this.transactions = new Transaction();
		File vendingMachineFile = new File(fileName);
		try (Scanner itemScanner = new Scanner(vendingMachineFile)) {
			while (itemScanner.hasNextLine()) {
				String[] splitString = itemScanner.nextLine().split("\\|");
				Item newItem = new Item(splitString[0], splitString[1], new BigDecimal(splitString[2]), splitString[3]);
				vendingMachineItems.put(splitString[0], newItem);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	public String displayItemsToMenu() {
		String result = "";
		String item = "ITEM";
		String vendNumber = "VEND NUMBER";
		String price = "Price";

		result += String.format("\n%-15s %-20s %-20s", vendNumber, item, price);
		result += "\n************************************************";
		for (String s : vendingMachineItems.keySet()) {
			Item i = vendingMachineItems.get(s);
			if (i.getStockLevel() == 0) {
				result += String.format("\n%-15s %-20s %-20s", i.getCoordinate(), i.getName(), SOLD_OUT_LABEL);
			} else {
				result += String.format("\n%-15s %-20s $%-20s", i.getCoordinate(), i.getName(),
						i.getPrice().toString());
			}
		}
		return result;
	}

	public String purchaseItem(String coordinate) {
		String result = "";
		Item item = vendingMachineItems.get(coordinate);
		if (item == null) {
			result += "\nItem doesn't exist";
		} else if (transactions.getBalance().compareTo(item.getPrice()) >= 0 && item.getStockLevel() > 0) {
			transactions.subtractFunds(item.getPrice());
			item.itemPurchased();
			result += item.makeSound();
			String auditLine = DTF.format(LocalDateTime.now()) + " " + item.getName() + " " + item.getCoordinate()
					+ " $" + item.getPrice() + " $" + transactions.getBalance();
			//result += "\nYour remaining balance is $" + transactions.getBalance();
			vendingAudit.writeToAudit(auditLine);
		} else if (item.getStockLevel() == 0) {
			result += "\nItem is Sold Out";
		} else if (transactions.getBalance().compareTo(item.getPrice()) < 0) {
			result += "\nNot enough funds for purchase.";
		}
		return result;

	}

	public String createSalesReport() {
		salesReport.createSalesReportFile();
		totalSales = new BigDecimal("0.00");
		String report = String.format("%-20s%-15s\n", "Items", "Sold");
		report += "*************************\n";
		for (String s : vendingMachineItems.keySet()) {
			Item i = vendingMachineItems.get(s);
			int itemsSold = Item.STARTING_INVENTORY_LEVEL - i.getStockLevel();
			BigDecimal bigItemsSold = BigDecimal.valueOf(itemsSold);
			report += String.format("%-20s%-15d\n", i.getName(), itemsSold);
			// report += i.getName() + " | " + itemsSold + "\n";
			BigDecimal profitOnItem = i.getPrice().multiply(bigItemsSold);
			totalSales = totalSales.add(profitOnItem);
		}

		report += "\n**TOTAL SALES** $" + totalSales;
		
		salesReport.writeToSalesReport(report);

		return report;
	}
	
	public String makeDeposit(String input) {
		String result = "";
		try {
		if(input.equals("")) {
			result +="No input found.";
			input = "0";
		}
		if(Integer.parseInt(input) < 0) {
			result +="\nCannot deposit that amount";
			input = "0";
		}
		BigDecimal inputDeposit = new BigDecimal(input);
		transactions.addFunds(input);
		result += String.format("\nCurrent Balance is $%s\n" , transactions.getBalance());
		String deposit = VendingMachine.DTF.format(LocalDateTime.now()) + " FEED MONEY: $" + inputDeposit + " $" + transactions.getBalance();
		vendingAudit.writeToAudit(deposit);
		} catch (NumberFormatException e) {
			result = "\nInvalid deposit amount";
		}
		return result;	
	}
	
	public String makeChange() {
		return "\n" + transactions.makeChange();
	}

}// no printing, needs to return strings