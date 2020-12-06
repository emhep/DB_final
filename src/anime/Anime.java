package anime;
import java.util.ArrayList;

public class Anime {
	private String anime_id, title, title_english, title_japanese, title_synonyms, image_url, type, source, status, aired_string, aired, duration, rating, background, premiered, broadcast, related, producer, licensor, studio, genre, opening_theme, ending_theme;
	private int episodes, duration_per_episode, scored_by, rank, popularity, members, favorites;
	private boolean airing, aired_stringYearOnly = false;
	private double score;
	private ArrayList<String> miscCols = new ArrayList<String>();
	
	private static ArrayList<String> colNames;
	
	public Anime() {
		setAll("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", -1, -1, -1, -1, -1, -1, -1, false, false, -1);
	}
	
	private void setAll(String anime_id, String title, String title_english, String title_japanese, String title_synonyms,
			String image_url, String type, String source, String status, String aired_string, String aired,
			String duration, String rating, String background, String premiered, String broadcast, String related,
			String producer, String licensor, String studio, String genre, String opening_theme, String ending_theme,
			int episodes, int duration_per_episode, int scored_by, int rank, int popularity, int members, int favorites,
			boolean airing, boolean aired_stringYearOnly, double score) {
		this.anime_id = anime_id;
		this.title = title;
		this.title_english = title_english;
		this.title_japanese = title_japanese;
		this.title_synonyms = title_synonyms;
		this.image_url = image_url;
		this.type = type;
		this.source = source;
		this.status = status;
		this.aired_string = aired_string;
		this.aired = aired;
		this.duration = duration;
		this.rating = rating;
		this.background = background;
		this.premiered = premiered;
		this.broadcast = broadcast;
		this.related = related;
		this.producer = producer;
		this.licensor = licensor;
		this.studio = studio;
		this.genre = genre;
		this.opening_theme = opening_theme;
		this.ending_theme = ending_theme;
		this.episodes = episodes;
		this.duration_per_episode = duration_per_episode;
		this.scored_by = scored_by;
		this.rank = rank;
		this.popularity = popularity;
		this.members = members;
		this.favorites = favorites;
		this.airing = airing;
		this.aired_stringYearOnly = aired_stringYearOnly;
		this.score = score;
	}

	public static void setColNames(ArrayList<String> _colNames) {
		colNames = _colNames;
	}
	
	public boolean isGood() {
		boolean good = true;
		if(ending_theme.equals("")) {
			good = false;
		}
		if(miscCols.size() != 0) {
			good = false;
		}
		return good;
	}
	
	public String toString() {
		String output = "";
		output += colNames.get(0) + " : " + anime_id + "\n";
		output += colNames.get(1) + " : " + title + "\n";
		output += colNames.get(2) + " : " + title_english + "\n";
		output += colNames.get(3) + " : " + title_japanese + "\n";
		output += colNames.get(4) + " : " + title_synonyms + "\n";
		output += colNames.get(5) + " : " + image_url + "\n";
		output += colNames.get(6) + " : " + type + "\n";
		output += colNames.get(7) + " : " + source + "\n";
		output += colNames.get(8) + " : " + episodes + "\n";
		output += colNames.get(9) + " : " + status + "\n";
		output += colNames.get(10) + " : " + airing + "\n";
		output += colNames.get(11) + " : " + aired_string + "\n";
		output += colNames.get(12) + " : " + aired + "\n";
		output += colNames.get(13) + " : " + duration + "\n";
		output += colNames.get(14) + " : " + rating + "\n";
		output += colNames.get(15) + " : " + score + "\n";
		output += colNames.get(16) + " : " + scored_by + "\n";
		output += colNames.get(17) + " : " + rank + "\n";
		output += colNames.get(18) + " : " + popularity + "\n";
		output += colNames.get(19) + " : " + members + "\n";
		output += colNames.get(20) + " : " + favorites + "\n";
		output += colNames.get(21) + " : " + background + "\n";
		output += colNames.get(22) + " : " + premiered + "\n";
		output += colNames.get(23) + " : " + broadcast + "\n";
		output += colNames.get(24) + " : " + related + "\n";
		output += colNames.get(25) + " : " + producer + "\n";
		output += colNames.get(26) + " : " + licensor + "\n";
		output += colNames.get(27) + " : " + studio + "\n";
		output += colNames.get(28) + " : " + genre + "\n";
		output += colNames.get(29) + " : " + opening_theme + "\n";
		output += colNames.get(30) + " : " + ending_theme + "\n";
		
		for(int i = 0; i < miscCols.size(); i++) {
			output += "? : " + miscCols.get(i) + "\n";
		}

		return output;
	}
	
	public String getAnime_id() {
		return anime_id;
	}
	public void setAnime_id(String anime_id) {
		this.anime_id = anime_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle_english() {
		return title_english;
	}
	public void setTitle_english(String title_english) {
		this.title_english = title_english;
	}
	public String getTitle_japanese() {
		return title_japanese;
	}
	public void setTitle_japanese(String title_japanese) {
		this.title_japanese = title_japanese;
	}
	public String getTitle_synonyms() {
		return title_synonyms;
	}
	public void setTitle_synonyms(String title_synonyms) {
		this.title_synonyms = title_synonyms;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAired_string() {
		return aired_string;
	}
	public void setAired_string(String aired_string) {
		this.aired_string = aired_string;
	}
	public String getAired() {
		return aired;
	}
	public void setAired(String aired) {
		this.aired = aired;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getPremiered() {
		return premiered;
	}
	public void setPremiered(String premiered) {
		this.premiered = premiered;
	}
	public String getBroadcast() {
		return broadcast;
	}
	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}
	public String getRelated() {
		return related;
	}
	public void setRelated(String related) {
		this.related = related;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getLicensor() {
		return licensor;
	}
	public void setLicensor(String licensor) {
		this.licensor = licensor;
	}
	public String getStudio() {
		return studio;
	}
	public void setStudio(String studio) {
		this.studio = studio;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getOpening_theme() {
		return opening_theme;
	}
	public void setOpening_theme(String opening_theme) {
		this.opening_theme = opening_theme;
	}
	public String getEnding_theme() {
		return ending_theme;
	}
	public void setEnding_theme(String ending_theme) {
		this.ending_theme = ending_theme;
	}
	public int getEpisodes() {
		return episodes;
	}
	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}
	public void setEpisodes(String episodes) {
		setEpisodes(Integer.parseInt(episodes));
	}
	public int getDuration_per_episode() {
		return duration_per_episode;
	}
	public void setDuration_per_episode(int duration_per_episode) {
		this.duration_per_episode = duration_per_episode;
	}
	public int getScored_by() {
		return scored_by;
	}
	public void setScored_by(int scored_by) {
		this.scored_by = scored_by;
	}
	public void setScored_by(String scored_by) {
		setScored_by(Integer.parseInt(scored_by));
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public void setRank(String rank) {
		if(rank.equals("")) {
			setRank(0);
		} else {
			setRank(Integer.parseInt(rank));
		}
	}
	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public void setPopularity(String popularity) {
		setPopularity(Integer.parseInt(popularity));
	}
	public int getMembers() {
		return members;
	}
	public void setMembers(int members) {
		this.members = members;
	}
	public void setMembers(String members) {
		setMembers(Integer.parseInt(members));
	}
	public int getFavorites() {
		return favorites;
	}
	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}
	public void setFavorites(String favorites) {
		setFavorites(Integer.parseInt(favorites));
	}
	public boolean isAiring() {
		return airing;
	}
	public void setAiring(boolean airing) {
		this.airing = airing;
	}
	public void setAiring(String airing) {
		setAiring(Boolean.parseBoolean(airing));
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public void setScore(String score) {
		setScore(Double.parseDouble(score));
	}
	public ArrayList<String> getMiscCols() {
		return miscCols;
	}
	public void clearMiscCols() {
		miscCols.clear();
	}
	public void addMiscCols(String misc) {
		miscCols.add(misc);
	}
	public boolean getAired_stringYearOnly() {
		return aired_stringYearOnly;
	}
	public void setAired_stringYearOnly(boolean aired_stringYearOnly) {
		this.aired_stringYearOnly = aired_stringYearOnly;
	}
}
