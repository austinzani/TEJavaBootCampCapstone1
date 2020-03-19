package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Audit {

	private static String fileName = "Audit-Log.txt";
	private static File auditLog = new File(fileName);

	public void createAuditFile() {

		try {
			int versionNumber = 1;
			String[] splitName = fileName.split("\\.");
			
			while (auditLog.exists() == true) {
				String newFileName = splitName[0] + "-" + versionNumber + "." + splitName[1];
				auditLog = new File(newFileName);
				versionNumber++;

			}

			if (auditLog.exists() == false) {
				auditLog.createNewFile();
			}

		} catch (IOException e) {
			System.out.println("Unable to create the file");
		}
	}

	public void writeToAudit(String auditLine) {

		try (PrintWriter auditWriter = new PrintWriter(new FileOutputStream(auditLog, true))) {
			auditWriter.println(auditLine);
		}

		catch (FileNotFoundException e) {
			System.out.println("Audit File Not Found");
		}

	}

}
