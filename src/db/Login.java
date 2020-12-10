package db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Login {
	private static String usr;
	private static String psw;
	private static FileReader file;
	private static Scanner s;
	
	private static void setLoginFile() throws FileNotFoundException {
		file = new FileReader("login.txt");
	}
	
	private static void setScanner() {
		try {
			setLoginFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s = new Scanner(file);
	}
	
	public static void readLoginCredentials() {
		setScanner();
		usr = s.nextLine();
		psw = s.nextLine();
	}
	
	public static String getUsername() {
		return usr;
	}
	
	public static String getPassword() {
		return psw;
	}
	
}
