package nl.tudelft.ir.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadMail {

	public String printMail(String loc) {
		String mail = "";
		try {
			File file = new File(loc);
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis,
					"UTF-8"));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				mail = mail + sCurrentLine + "\n";
			}

		} catch (Exception e) {
			System.out.println("Exception in Read Mail");
		}

		return mail;

	}

}
