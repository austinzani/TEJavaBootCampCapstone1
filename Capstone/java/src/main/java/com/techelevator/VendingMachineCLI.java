package com.techelevator;

import java.util.Scanner;

import com.techelevator.view.Menu;
import com.techelevator.view.Transaction;
import com.techelevator.view.VendingMachine;

public class VendingMachineCLI {
	public static final String VENDING_MACHINE_INVENTORY_FILE = "VendingMachine.txt";
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase items";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String MAIN_MENU_SALES_REPORT = "";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_EXIT, MAIN_MENU_SALES_REPORT };
	private static final String PURCHASE_ITEMS_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_ITEMS_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_ITEMS_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_ITEMS_OPTIONS = { PURCHASE_ITEMS_FEED_MONEY, PURCHASE_ITEMS_SELECT_PRODUCT,
			PURCHASE_ITEMS_FINISH_TRANSACTION };
	private Menu menu;
	public static Scanner userInput = new Scanner(System.in);

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		VendingMachine vendingMachine = new VendingMachine(VENDING_MACHINE_INVENTORY_FILE);
		
		
		boolean keepGoing = true;
		while (keepGoing == true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				System.out.println(vendingMachine.displayItemsToMenu());
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				boolean stillPurchasing = true;
				while (stillPurchasing == true) {
					String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_ITEMS_OPTIONS);

					if (purchaseChoice.equals(PURCHASE_ITEMS_FEED_MONEY)) {
						System.out.print("\nHow much money would you like to deposit? ");
						String depositAmount = userInput.nextLine();
						System.out.println(vendingMachine.makeDeposit(depositAmount));
					} else if (purchaseChoice.equals(PURCHASE_ITEMS_SELECT_PRODUCT)) {
						System.out.print("\nWhat item would you like to purchase? (Example:A1) ");
						String itemWishToPurchase = userInput.nextLine();
						System.out.println(vendingMachine.purchaseItem(itemWishToPurchase.toUpperCase()));
					} else if (purchaseChoice.equals(PURCHASE_ITEMS_FINISH_TRANSACTION)) {
						System.out.println(vendingMachine.makeChange());
						stillPurchasing = false;
					}
				}

			} else if (choice.equals(MAIN_MENU_EXIT)) {
				keepGoing = false;
			} else if (choice.equals(MAIN_MENU_SALES_REPORT)) {
				vendingMachine.createSalesReport();

			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
