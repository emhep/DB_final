package anime;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class AnimeList {
	private static ArrayList<String> colNames;
	private static ArrayList<Anime> animes = new ArrayList<Anime>();
	
	public static void main(String[] args) throws FileNotFoundException{
		loadData();
	}
	
	public static ArrayList<Anime> getAnimeList() {
		return animes;
	}
	
	public static void loadData() throws FileNotFoundException {
		FileReader file = new FileReader("AnimeList.csv");
		Scanner sc = new Scanner(file);
		
		// Get the names of all the columns
		String[] line = sc.nextLine().split(",");
		colNames = new ArrayList();
		for(String col : line) {
			colNames.add(col);
		}
		
		System.out.println(colNames.size() + " cols of anime data");
		
		int numBadLen = 0;
		int numGoodLen = 0;
		Anime.setColNames(colNames);
		
		boolean doPrint = false; // Should it print every copy as its read, mainly for testing
		int numToIgnore = 0; // Should it ignore a certain number of entries, mainly for testing
		for(int i = 0; i < numToIgnore; i++) {
			sc.nextLine();
		}
		
		try {
		while(sc.hasNextLine()) {
			int difInc = 0;
			line = sc.nextLine().split(",");
			//System.out.print(line.length + " ");
			
			
			// data format
			int dif = 0;
			Anime anime = new Anime();
			anime.setAnime_id(line[0]);
			
			// Title
			difInc = 0;
			String title = "";
			if(!line[dif + 1].equals("") && line[dif + 1].charAt(0) == '"') {
				for(int i = 1; i + dif < line.length; i++) {
					if(i != 1) {
						difInc += 1;
					}

					title += line[dif + i] + ",";

					if(line[dif + i].charAt(line[dif + i].length()-1) == '"') {
						break;
					}
				}
				dif += difInc;
				title = title.substring(1, title.length()-3);
			} else {
				title = line[dif + 1];
			}
			anime.setTitle(title);
			
			// The english title
			difInc = 0;
			String title_english = "";
			if(!line[dif + 2].equals("") && line[dif + 2].charAt(0) == '"') {
				for(int i = 2; i + dif < line.length; i++) {
					if(i != 2) {
						difInc += 1;
					}

					title_english += line[dif + i] + ",";

					if(line[dif + i].charAt(line[dif + i].length()-1) == '"') {
						break;
					}
				}
				dif += difInc;
				title_english = title_english.substring(1, title_english.length()-3);
			} else {
				title_english = line[dif + 2];
			}
			anime.setTitle_english(title_english);
			
			// The japanese title
			difInc = 0;
			String title_japanese = "";
			if(!line[dif + 3].equals("") && line[dif + 3].charAt(0) == '"') {
				for(int i = 3; i + dif < line.length; i++) {
					if(i != 3) {
						difInc += 1;
					}

					title_japanese += line[dif + i] + ",";

					if(line[dif + i].charAt(line[dif + i].length()-1) == '"') {
						break;
					}
				}
				dif += difInc;
				title_japanese = title_japanese.substring(1, title_japanese.length()-3);
			} else {
				title_japanese = line[dif + 3];
			}
			anime.setTitle_japanese(title_japanese);
			
			// All the alternate names
			int endOfAltNames = -1;
			String altNames;
			boolean usedAltEndBound = false;
			if(!line[dif + 4].equals("")) {
				for(int i = 5; i + dif < line.length; i++) {
					if(line[dif + i].length() >= 4 && line[dif + i].substring(0,4).toLowerCase().equals("http")){
						endOfAltNames = i; // the image url which is the next col
						break;
					}
				}
				if(endOfAltNames == -1) {
					for(int i = 6; i + dif < line.length; i++) {
						if(line[dif + i].toLowerCase().equals("tv") || line[dif + i].toLowerCase().equals("movie") || line[dif + i].toLowerCase().equals("ona") || line[dif + i].toLowerCase().equals("ova") || line[dif + i].toLowerCase().equals("special") || line[dif + i].toLowerCase().equals("music")){
							endOfAltNames = i; // the image url which is the next col
							usedAltEndBound = true;
							break;
						}
					}
				}
				if(endOfAltNames == -1) {
					throw new Exception("Didn't find the image_url col");
				}
				altNames = line[4];
				for(int i = 5; i + dif < endOfAltNames; i++) {
					altNames += ", " + line[dif + i];
				}
			} else {
				altNames = line[dif + 4];
				endOfAltNames = 5;
			}
			anime.setTitle_synonyms(altNames);
			
			dif += endOfAltNames - 5;
			if(usedAltEndBound) {
				dif -= 1;
			}
			
			anime.setImage_url(line[dif + 5]);
			anime.setType(line[dif + 6]); // The type (movie, tv, etc)
			anime.setSource(line[dif + 7]); // source material (original, manga, novel, etc)
			anime.setEpisodes(line[dif + 8]);
			anime.setStatus(line[dif + 9]); // Airing/not-airing
			anime.setAiring(line[dif + 10]); // above as a boolean
			
			// The date that it began airing as YYYY or Mon DD, YYYY
			if(line[dif + 11].substring(0,2).equals("19") || line[dif + 11].substring(0,2).equals("20")) { // "1980, 1990, 2000, or 2010  and  2000 to 2008
				if(line[dif + 11].length() == 4) {
					anime.setAired_string(line[dif + 11]);
					anime.setAired_stringYearOnly(true);
				} else {
					anime.setAired_string(line[dif + 11].substring(0,4));
					anime.setAired_stringYearOnly(true);
				}
			} else { // Oct 19, 2000 to Feb 20, 2002  or  Oct 19, 2000
				String date1 = line[dif + 11];
				String date2 = line[dif + 12];
				
				if(date1.equals("Not available")) {
					anime.setAired_string(date1);
				} else {
					if(date2.charAt(0) != ' ') {
						throw new Exception("aired_string didn't have a space on the next line");
					}

					if(date2.length() == 5) {
						anime.setAired_string(date1.substring(1, date1.length()) + "," + date2.substring(0, date2.length()-1));
						dif += 1;
					} else {
						anime.setAired_string(date1.substring(1, date1.length()) + "," + date2.substring(0,5));
						if((line[dif + 13].substring(0,3).equals(" 19") || line[dif + 13].substring(0,3).equals(" 20")) &&
								(line[dif + 13]).charAt(line[dif + 13].length()-1) == '"') {
							dif += 2;
						} else {
							dif += 1;
						}
					}
				}
			}
			
			// diff format of date aired
			String aired = line[dif + 12] + line[dif + 13];
			aired = aired.substring(1, aired.length()-1);
			dif += 1;
			anime.setAired(aired);
			
			anime.setDuration(line[dif + 13]);
			anime.setRating(line[dif + 14]); // Maturity rating (PG-13, etc)
			anime.setScore(line[dif + 15]); // Avg user score
			anime.setScored_by(line[dif + 16]); // num users scored by
			anime.setRank(line[dif + 17]); // the rank in mal
			anime.setPopularity(line[dif + 18]); // popularity of the show
			anime.setMembers(line[dif + 19]); // num users who have watched
			anime.setFavorites(line[dif + 20]); // num users who favorited it
			
			// Background info on the show
			difInc = 0;
			String background = "";
			if(!line[dif + 21].equals("") && line[dif + 21].charAt(0) == '"') {
				for(int i = 21; i + dif < line.length; i++) {
					if(i != 21) {
						difInc += 1;
					}

					background += line[dif + i] + ", ";

					int numQuotes = 0;
					for(int j = 0; j < background.length(); j++) {
						if(background.charAt(j) == '"') {
							numQuotes++;
						}
					}
					
					if(line[dif + i].charAt(line[dif + i].length()-1) == '"' && numQuotes % 2 == 0) {
						break;
					}
				}
				dif += difInc;
				background = background.substring(1, background.length()-3);
			} else {
				background = line[dif + 21];
			}
			anime.setBackground(background);
			
			anime.setPremiered(line[dif + 22]); // Season premiered in
			anime.setBroadcast(line[dif + 23]); // When it was broadcast

			// Related media
			difInc = 0;
			String relatedIDs = "";
			if(!line[dif + 24].equals("") && !line[dif + 24].equals("[]")) {
				for(int i = 24; i + dif < line.length; i++) {
					if(i != 24) {
						difInc += 1;
					}

					if(line[dif + i].contains("mal_id")) {
						relatedIDs += line[dif + i].substring(line[dif + i].lastIndexOf(' ') + 1, line[dif + i].length()-1) + ", ";
					}

					if(line[dif + i].contains("}]}\"")) {
						break;
					}
				}
				relatedIDs = relatedIDs.substring(0, relatedIDs.length()-2);
			} else {
				relatedIDs = line[dif + 24];
			}
			dif += difInc;
			anime.setRelated(relatedIDs);
			
			// Producer(s) who worked on it
			difInc = 0;
			String producers = "";
			if(!line[dif + 25].equals("") && line[dif + 25].charAt(0) == '"') {
				for(int i = 25; i + dif < line.length; i++) {
					if(i != 25) {
						difInc += 1;
					}

					producers += line[dif + i] + ", ";

					if(line[dif + i].charAt(line[dif + i].length()-1) == '"') {
						break;
					}
				}
				dif += difInc;
				producers = producers.substring(1, producers.length()-3);
			} else {
				producers = line[dif + 25];
			}
			anime.setProducer(producers);
			
			// Licensors of it
			difInc = 0;
			String licensors = "";
			if(!line[dif + 26].equals("") && line[dif + 26].charAt(0) == '"') {
				for(int i = 26; i + dif < line.length; i++) {
					if(i != 26) {
						difInc += 1;
					}

					licensors += line[dif + i] + ", ";

					if(line[dif + i].charAt(line[dif + i].length()-1) == '"') {
						break;
					}
				}
				dif += difInc;
				licensors = licensors.substring(1, licensors.length()-3);
			} else {
				licensors = line[dif + 26];
			}
			anime.setLicensor(licensors);
			
			// Studio(s) responsible for it
			difInc = 0;
			String studios = "";
			if(!line[dif + 27].equals("") && line[dif + 27].charAt(0) == '"') {
				for(int i = 27; i + dif < line.length; i++) {
					if(i != 27) {
						difInc += 1;
					}

					studios += line[dif + i] + ", ";

					if(line[dif + i].charAt(line[dif + i].length()-1) == '"') {
						break;
					}
				}
				dif += difInc;
				studios = studios.substring(1, studios.length()-3);
			} else {
				studios = line[dif + 27];
			}
			anime.setStudio(studios);
			
			// Genres it fall into
			difInc = 0;
			String genres = "";
			if(!line[dif + 28].equals("") && line[dif + 28].charAt(0) == '"') {
				for(int i = 28; i + dif < line.length; i++) {
					if(i != 28) {
						difInc += 1;
					}

					genres += line[dif + i] + ", ";

					if(line[dif + i].charAt(line[dif + i].length()-1) == '"') {
						break;
					}
				}
				dif += difInc;
				genres = genres.substring(1, genres.length()-3);
			} else {
				genres = line[dif + 28];
			}
			anime.setGenre(genres);
			
			// The opening song(s)
			difInc = 0;
			String opSongs = "";
			if(!line[dif + 29].equals("") && line[dif + 29].charAt(0) == '"') {
				for(int i = 29; i + dif < line.length; i++) {
					if(i != 29) {
						difInc += 1;
					}

					opSongs += line[dif + i] + ", ";

					int numQuotes = 0;
					for(int j = 0; j < opSongs.length(); j++) {
						if(opSongs.charAt(j) == '"') {
							numQuotes++;
						}
					}
					
					if(line[dif + i].charAt(line[dif + i].length()-1) == '"' && numQuotes % 2 == 0) {
						break;
					}
				}
				dif += difInc;
				opSongs = opSongs.substring(1, opSongs.length()-3);
			} else {
				opSongs = line[dif + 29];
			}
			anime.setOpening_theme(opSongs);
			
			// The ending song(s)
			difInc = 0;
			String edSongs = "";
			if(!line[dif + 30].equals("") && line[dif + 30].charAt(0) == '"') {
				for(int i = 30; i + dif < line.length; i++) {
					if(i != 30) {
						difInc += 1;
					}

					edSongs += line[dif + i] + ", ";

					int numQuotes = 0;
					for(int j = 0; j < edSongs.length(); j++) {
						if(edSongs.charAt(j) == '"') {
							numQuotes++;
						}
					}
					
					if(line[dif + i].charAt(line[dif + i].length()-1) == '"' && numQuotes % 2 == 0) {
						break;
					}
				}
				dif += difInc;
				edSongs = edSongs.substring(1, edSongs.length()-3);
			} else {
				edSongs = line[dif + 30];
			}
			anime.setEnding_theme(edSongs);
			
			// If there is any additional data, this is mainly for printing data if something goes wrong
			for(int i = 31; dif+i < line.length; i++) {
				anime.addMiscCols(line[dif+i]);
			}
			
			// Print out each item
			if(doPrint) {
			System.out.println(anime);
			}
			
			// Get a sum of good and bad data (doesn't look like it filled an anime object correctly)
			if(!anime.isGood()) {
				numBadLen++;
				System.out.println(anime);
			} else {
				numGoodLen++;
			}
			
			animes.add(anime);
		}
		} catch (Exception e) {
			System.out.println("Fail happened after " + (numBadLen+numGoodLen));
			e.printStackTrace();
		}
		
		System.out.println("There were " + numGoodLen + " good animes");
		System.out.println("There were " + numBadLen + " bad animes");
	}
	
}
