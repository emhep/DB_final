package db;

import javax.swing.table.DefaultTableModel;

import anime.Anime;
import anime.AnimeList;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import db.Login;
import economics.Unemployment;
import economics.UnemploymentList;

public class Database {
	static Connection db;
	
	public static void login(String username, String password) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        String connectString = "jdbc:postgresql://bartik.mines.edu/csci403";

        db = DriverManager.getConnection(connectString, username, password);
    }
	
	public static void main(String[] args) throws FileNotFoundException {
		Login.readLoginCredentials();
		try {
			login(Login.getUsername(), Login.getPassword());
			
			createAnimeTable();
			createAnimeRelationTable();
			fillAnimeTable();
			fillAnimeRelationTable();
			createCountryTable();
			createUnemploymentTable();
			fillCountryTable();
			fillUnemploymentTable();
			
			
			/*String query = "select * from album";
			
			PreparedStatement ps = db.prepareStatement(query);
			ResultSet rs = ps.executeQuery();*/
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createAnimeTable() throws SQLException {
		String query = "drop table if exists anime cascade";
		PreparedStatement dropPs = db.prepareStatement(query);
		dropPs.execute();
		
		query = "create table anime " + 
				"(anime_id numeric primary key, " + 
				"title text, " +
				"title_english text, " +
				"title_japanese text, " +
				"title_synonyms text, " +
				"type text, " +
				"source text, " +
				"episodes numeric, " +
				"aired_year numeric, " +
				"duration text, " +
				"rating text, " +
				"score numeric(4, 2), " +
				"scored_by numeric, " +
				"rank numeric, " +
				"popularity numeric, " +
				"members numeric, " +
				"favorites numeric)";
		PreparedStatement createPs = db.prepareStatement(query);
		createPs.execute();
	}
	
	public static void createAnimeRelationTable() throws SQLException {
		String query = "drop table if exists anime_relations";
		PreparedStatement dropPs = db.prepareStatement(query);
		dropPs.execute();
		
		query = "create table anime_relations " + 
				"(anime_id numeric references anime (anime_id), " + 
				"related_id numeric, " +
				"primary key (anime_id, related_id))";
		PreparedStatement createPs = db.prepareStatement(query);
		createPs.execute();
	}
	
	public static void createUnemploymentTable() throws SQLException {
		String query = "drop table if exists unemployment";
		PreparedStatement dropPs = db.prepareStatement(query);
		dropPs.execute();
		
		query = "create table unemployment " + 
				"(year numeric, " + 
				"country_code text references country (country_code), " +
				"percent_unemployment real, " +
				"primary key (year, country_code))";
		PreparedStatement createPs = db.prepareStatement(query);
		createPs.execute();
	}
	
	public static void createCountryTable() throws SQLException {
		String query = "drop table if exists country cascade";
		PreparedStatement dropPs = db.prepareStatement(query);
		dropPs.execute();
		
		query = "create table country " + 
				"(country_code text primary key, " +
				"country_name text)";
		PreparedStatement createPs = db.prepareStatement(query);
		createPs.execute();
	}
	
	public static void fillAnimeTable() throws SQLException, FileNotFoundException {
		String query = "insert into anime " +
				"(anime_id, " + 
				"title, " + 
				"title_english, " + 
				"title_japanese, " + 
				"title_synonyms, " + 
				"type, " +
				"source, " + 
				"episodes, " + 
				"aired_year, " +
				"duration, " + 
				"rating, " +
				"score, " +
				"scored_by, " +
				"rank, " +
				"popularity, " +
				"members, " +
				"favorites) " + 
				"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement ps = db.prepareStatement(query);
		db.setAutoCommit(false);
		AnimeList.loadData();
		ArrayList<Anime> animeList = AnimeList.getAnimeList();
		for(Anime anime : animeList) {
			ps.setInt(1, anime.getAnime_id());
			ps.setString(2, anime.getTitle());
			ps.setString(3, anime.getTitle_english());
			ps.setString(4, anime.getTitle_japanese());
			ps.setString(5, anime.getTitle_synonyms());
			ps.setString(6, anime.getType());
			ps.setString(7, anime.getSource());
			ps.setInt(8, anime.getEpisodes());
			ps.setInt(9, anime.getYear());
			ps.setString(10, anime.getDuration());
			ps.setString(11, anime.getRating());
			ps.setDouble(12, anime.getScore());
			ps.setInt(13, anime.getScored_by());
			ps.setInt(14, anime.getRank());
			ps.setInt(15, anime.getPopularity());
			ps.setInt(16, anime.getMembers());
			ps.setInt(17, anime.getFavorites());
			ps.addBatch();
		}
		int[] updates = ps.executeBatch();
        System.out.println(Arrays.toString(updates));
        
        db.commit();
        
        db.setAutoCommit(true);
        
        /*query = "update anime set aired_year = null where aired_year = -1";
        
        PreparedStatement upPs = db.prepareStatement(query);
        ps.execute();*/
	}
	
	public static void fillAnimeRelationTable() throws SQLException, FileNotFoundException {
		String query = "insert into anime_relations " +
				"(anime_id, " + 
				"related_id) " + 
				"values (?, ?);";
		
		PreparedStatement ps = db.prepareStatement(query);
		db.setAutoCommit(false);
		AnimeList.loadData();
		ArrayList<Anime> animeList = AnimeList.getAnimeList();
		for(Anime anime : animeList) {
			Set<Integer> seenRelateds = new HashSet<Integer>();
			for(String related : anime.getRelated().split(", ")) {
				if(related.equals("[]") || related.equals("")) {
					continue;
				}
				int related_id = Integer.parseInt(related);
				if(seenRelateds.contains(related_id)) {
					continue;
				}
				seenRelateds.add(related_id);
				ps.setInt(1, anime.getAnime_id());
				ps.setInt(2, related_id);
				ps.addBatch();
			}
		}
		int[] updates = ps.executeBatch();
        System.out.println(Arrays.toString(updates));
        
        db.commit();
        
        db.setAutoCommit(true);
	}
	
	public static void fillCountryTable() throws SQLException, FileNotFoundException {
		String query = "insert into country " +
				"(country_code, " + 
				"country_name) " + 
				"values (?, ?);";
		
		PreparedStatement ps = db.prepareStatement(query);
		db.setAutoCommit(false);
		UnemploymentList.loadData();
		ArrayList<Unemployment> unemploymentList = UnemploymentList.getUnemploymentList();
		for(Unemployment unemployment : unemploymentList) {
			ps.setString(1, unemployment.getCountry_code());
			ps.setString(2, unemployment.getCountry_name());
			ps.addBatch();
		}
		int[] updates = ps.executeBatch();
        System.out.println(Arrays.toString(updates));
        
        db.commit();
        
        db.setAutoCommit(true);
	}
	
	public static void fillUnemploymentTable() throws SQLException, FileNotFoundException {
		String query = "insert into unemployment " +
				"(year, " + 
				"country_code, " +
				"percent_unemployment) " + 
				"values (?, ?, ?);";
		
		PreparedStatement ps = db.prepareStatement(query);
		db.setAutoCommit(false);
		UnemploymentList.loadData();
		ArrayList<Unemployment> unemploymentList = UnemploymentList.getUnemploymentList();
		ArrayList<Integer> years = Unemployment.getYears();
		for(Unemployment unemployment : unemploymentList) {
			String country_code = unemployment.getCountry_code();
			ArrayList<Double> yearlyUnemployment = unemployment.getYearlyUnemployment();
			for(int i = 0; i < yearlyUnemployment.size(); i++) {
				if(yearlyUnemployment.get(i) == -1) {
					continue;
				}
				ps.setInt(1, years.get(i));
				ps.setString(2, country_code);
				ps.setDouble(3, yearlyUnemployment.get(i));
				ps.addBatch();
			}
		}
		int[] updates = ps.executeBatch();
        System.out.println(Arrays.toString(updates));
        
        db.commit();
        
        db.setAutoCommit(true);
	}
}
