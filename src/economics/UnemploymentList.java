package economics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import anime.Anime;

public class UnemploymentList {
	private static ArrayList<String> colNames;
	private static ArrayList<Unemployment> unemploymentData = new ArrayList<Unemployment>();

	public static void main(String[] args) throws FileNotFoundException {
		loadData();
	}
	
	public static ArrayList<Unemployment> getAnimeList() {
		return unemploymentData;
	}
	
	public static void loadData() throws FileNotFoundException {
		FileReader file = new FileReader("Unemployment.csv");
		Scanner sc = new Scanner(file);
		
		// Use up spare lines
		for(int i = 0; i < 4; i++) {
			sc.nextLine();
		}
		
		// Get the names of all the columns
		String[] line = sc.nextLine().split(",");
		colNames = new ArrayList();
		for(String col : line) {
			colNames.add(col);
		}
		
		System.out.println(colNames.size() + " cols of unemployment data");
		
		
		int numBadLen = 0;
		int numGoodLen = 0;
		Unemployment.setColNames(colNames);
		
		try {
			while(sc.hasNextLine()) {
				line = sc.nextLine().split(",");
				
				Unemployment unemployment = new Unemployment();
				
				unemployment.setCountry_name(line[0]);
				unemployment.setCountry_code(line[1]);
				
				for(int i = 5; i < colNames.size(); i++) {
					unemployment.addToYearlyUnemployment(line[i]);
				}
				
				// Get a sum of good and bad data (doesn't look like it filled an anime object correctly)
				if(!unemployment.isGood()) {
					numBadLen++;
					System.out.println(unemployment);
				} else {
					numGoodLen++;
				}
				
				unemploymentData.add(unemployment);
				System.out.println(unemployment);
			}
		} catch (Exception e) {
			System.out.println("Fail happened after " + (numBadLen+numGoodLen));
			e.printStackTrace();
		}

		System.out.println("There were " + numGoodLen + " good unemployments");
		System.out.println("There were " + numBadLen + " bad unemployments");
	}
}
