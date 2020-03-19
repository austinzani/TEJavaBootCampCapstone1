package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.view.VendingMachine;


public class VendingMachineTest {
	VendingMachine testVending;
	File testFile;
	private static final String LINE_1 = "A1|Potato Crisps|3.05|Chip";
	private static final String LINE_2 = "B3|Wonka Bar|1.50|Candy";
	private static final String LINE_3 = "C3|Mountain Melter|1.50|Drink";
	private static final String LINE_4 = "D1|U-Chews|0.85|Gum";
	private static final String[] LINES_FOR_FILE = new String[] { LINE_1, LINE_2, LINE_3, LINE_4 };

	@Before
	public void setup() {
		testFile = new File("test-vending.txt");
		try {
			testFile.createNewFile();
			try (PrintWriter testWriter = new PrintWriter(testFile)) {
				for (String s : LINES_FOR_FILE) {
					testWriter.println(s);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Vending source file not found");
		} catch (IOException e) {
			System.out.println("Test file couldn't be created");
		}

		testVending = new VendingMachine("test-vending.txt");
	}

	@Test
	public void check_if_map_is_in_correct_order() {
		String vendingList = testVending.displayItemsToMenu();
		String expecctedList = "\nVEND NUMBER     ITEM                 Price               \n************************************************\n"
				+ "A1              Potato Crisps        $3.05                \nB3              Wonka Bar            $1.50                \n"
				+"C3              Mountain Melter      $1.50                \nD1              U-Chews              $0.85                ";

		Assert.assertEquals(expecctedList, vendingList);
	}

	@Test
	public void check_to_see_if_make_change_works_as_expected() {
		testVending.makeDeposit("10");
		String testChange = testVending.makeChange();
		String expectedOutcome = "\nYou were returned 10 dollar(s), 0 quarter(s), 0 dime(s), and 0 nickel(s).";
		Assert.assertEquals(expectedOutcome, testChange);
		testVending.makeDeposit("10");
		testVending.purchaseItem("A1");
		testChange = testVending.makeChange();
		expectedOutcome = "\nYou were returned 6 dollar(s), 3 quarter(s), 2 dime(s), and 0 nickel(s).";
		Assert.assertEquals(expectedOutcome, testChange);
		testVending.makeDeposit("10");
		testVending.purchaseItem("A1");
		testVending.purchaseItem("A1");
		testVending.purchaseItem("A1");
		testChange = testVending.makeChange();
		expectedOutcome = "\nYou were returned 0 dollar(s), 3 quarter(s), 1 dime(s), and 0 nickel(s).";
		Assert.assertEquals(expectedOutcome, testChange);

	}

	@Test
	public void items_make_sound_properly() {
		testVending.makeDeposit("100");

		String drink = testVending.purchaseItem("C3");
		String gum = testVending.purchaseItem("D1");
		String candy = testVending.purchaseItem("B3");
		String chips = testVending.purchaseItem("A1");

		Assert.assertEquals("\nCrunch Crunch, Yum!", chips);
		Assert.assertEquals("\nChew Chew, Yum!", gum);
		Assert.assertEquals("\nMunch Munch, Yum!", candy);
		Assert.assertEquals("\nGlug Glug, Yum!", drink);

	}// should be checking string from purchasing an item

	@After
	public void cleanup() {
		testFile.delete();
		testVending.makeChange();
	}

}
