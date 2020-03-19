package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class SalesReport {
	private String salesFileName = "Sales-Report.txt";
	private File salesReport = new File(salesFileName);
	
	public void createSalesReportFile() {

		try {
			int versionNumber = 1;

			while (salesReport.exists() == true) {
				String[] splitName = salesFileName.split("\\.");
				String newFileName = splitName[0] + "-" + versionNumber + "." + splitName[1];
				salesReport = new File(newFileName);
				versionNumber++;

			}

			if (salesReport.exists() == false) {
				salesReport.createNewFile();
			}

		} catch (IOException e) {
			System.out.println("Unable to create the file");
		}
	}
	
	public void writeToSalesReport(String report) {

		try {

			try (PrintWriter salesReportWriter = new PrintWriter(new FileOutputStream(salesReport, true))) {
				salesReportWriter.println(report);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Sales Report File Not Found");
		}
	}


	
	
}
