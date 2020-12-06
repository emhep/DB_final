package economics;

import java.util.ArrayList;

public class Unemployment {
	private ArrayList<Double> yearlyUnemployment = new ArrayList<Double>();
	private String country_name, country_code;
	
	private static ArrayList<String> colNames;
	
	public String toString() {
		String output = "";
		output += country_name + "(" + country_code + ")";
		for(Double data : yearlyUnemployment) {
			output += " " + data;
		}
		
		return output;
	}

	public static void setColNames(ArrayList<String> _colNames) {
		colNames = _colNames;
	}
	
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		if(country_name.charAt(0) == '"') {
			country_name = country_name.substring(1, country_name.length());
		}
		if(country_name.charAt(country_name.length()-1) == '"') {
			country_name = country_name.substring(0, country_name.length()-1);
		}
		this.country_name = country_name;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		if(country_code.charAt(0) == '"') {
			country_code = country_code.substring(1, country_code.length());
		}
		if(country_code.charAt(country_code.length()-1) == '"') {
			country_code = country_code.substring(0, country_code.length()-1);
		}
		this.country_code = country_code;
	}
	
	public void addToYearlyUnemployment(String yearlyUnemploymentString) {
		Double yearlyUnemployment;
		if(yearlyUnemploymentString.equals("\"\"")) {
			yearlyUnemployment = null;
		} else {
			yearlyUnemploymentString = yearlyUnemploymentString.substring(1, yearlyUnemploymentString.length()-1);
			yearlyUnemployment = Double.parseDouble(yearlyUnemploymentString);
		}
		this.yearlyUnemployment.add(yearlyUnemployment);
	}
	
	public boolean isGood() {
		boolean good = true;
		if(yearlyUnemployment.size() + 3 != colNames.size()) {
			good = false;
		}
		return good;
	}
}
