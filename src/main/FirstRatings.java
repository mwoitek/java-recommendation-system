package main;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.csv.CSVRecord;
import edu.duke.FileResource;

public class FirstRatings {

  public ArrayList<Movie> loadMovies(String filename) {
    ArrayList<Movie> movies = new ArrayList<Movie>();
    FileResource fr = new FileResource("data/" + filename);

    String id;
    String title;
    String year;
    String genres;
    String director;
    String country;
    String poster;
    int minutes;
    Movie movie;

    for (CSVRecord record : fr.getCSVParser()) {
      id = record.get("id");
      title = record.get("title");
      year = record.get("year");
      genres = record.get("genre");
      director = record.get("director");
      country = record.get("country");
      poster = record.get("poster");
      minutes = Integer.parseInt(record.get("minutes"));
      movie = new Movie(id, title, year, genres, director, country, poster, minutes);
      movies.add(movie);
    }

    return movies;
  }

  private int countComedyMovies(ArrayList<Movie> movies) {
    int count = 0;
    for (Movie movie : movies) {
      if (movie.getGenres().toLowerCase().contains("comedy")) {
        count++;
      }
    }
    return count;
  }

  private int countMoviesLongerThan150Min(ArrayList<Movie> movies) {
    int count = 0;
    for (Movie movie : movies) {
      if (movie.getMinutes() > 150) {
        count++;
      }
    }
    return count;
  }

  private HashMap<String, Integer> buildDirectorCountMap(ArrayList<Movie> movies) {
    HashMap<String, Integer> directorCountMap = new HashMap<String, Integer>();
    for (Movie movie : movies) {
      for (String director : movie.getDirector().split(", ")) {
        if (directorCountMap.containsKey(director)) {
          directorCountMap.put(director, directorCountMap.get(director) + 1);
        } else {
          directorCountMap.put(director, 1);
        }
      }
    }
    return directorCountMap;
  }

  private Integer getMaximumValueInMap(HashMap<String, Integer> myMap) {
    Integer maxValue = -1;
    Integer value;
    for (String key : myMap.keySet()) {
      if ((value = myMap.get(key)) > maxValue) {
        maxValue = value;
      }
    }
    return maxValue;
  }

  private ArrayList<String> getKeysWithMaxValue(HashMap<String, Integer> myMap,
      Integer maxValue) {
    ArrayList<String> keys = new ArrayList<String>();
    for (String key : myMap.keySet()) {
      if (myMap.get(key) == maxValue) {
        keys.add(key);
      }
    }
    return keys;
  }

  public void testLoadMovies() {
    String filename = "ratedmoviesfull.csv";
    ArrayList<Movie> movies = loadMovies(filename);
    HashMap<String, Integer> directorCountMap = buildDirectorCountMap(movies);
    Integer maxNumMovies = getMaximumValueInMap(directorCountMap);
    ArrayList<String> directors = getKeysWithMaxValue(directorCountMap, maxNumMovies);

    System.out.println("filename = " + filename);
    System.out.println("Number of movies: " + movies.size());

    // System.out.println("Movies:");
    // for (Movie movie : movies) {
    // System.out.println(movie);
    // }

    System.out
        .println("Movies that have 'Comedy' as a genre: " + countComedyMovies(movies));
    System.out.println("Movies that are longer than 150 minutes: "
        + countMoviesLongerThan150Min(movies));

    System.out.println("Maximum number of movies by any director: " + maxNumMovies);
    System.out.println("Directors that directed that many movies:");
    for (String director : directors) {
      System.out.println(director);
    }
    System.out.println("Number of such directors: " + directors.size());
  }

  public ArrayList<Rater> loadRaters(String filename) {
    ArrayList<Rater> raters = new ArrayList<Rater>();
    FileResource fr = new FileResource("data/" + filename);

    String prevRaterId = "";
    String currRaterId;
    String movieId;
    double rating;
    int idx = -1;
    Rater rater;

    for (CSVRecord record : fr.getCSVParser()) {
      currRaterId = record.get("rater_id");
      if (!currRaterId.equals(prevRaterId)) {
        raters.add(new Rater(currRaterId));
        idx++;
      }
      movieId = record.get("movie_id");
      rating = Double.parseDouble(record.get("rating"));
      rater = raters.get(idx);
      rater.addRating(movieId, rating);
      raters.set(idx, rater);
      prevRaterId = currRaterId;
    }

    return raters;
  }

  private HashMap<String, Integer> buildRaterNumRatingsMap(ArrayList<Rater> raters) {
    HashMap<String, Integer> raterNumRatingsMap = new HashMap<String, Integer>();
    for (Rater rater : raters) {
      raterNumRatingsMap.put(rater.getID(), rater.numRatings());
    }
    return raterNumRatingsMap;
  }

  public void testLoadRaters() {
    String filename = "ratings_short.csv";
    ArrayList<Rater> raters = loadRaters(filename);
    HashMap<String, Integer> raterNumRatingsMap = buildRaterNumRatingsMap(raters);
    Integer maxNumRatings = getMaximumValueInMap(raterNumRatingsMap);
    ArrayList<String> raterIds = getKeysWithMaxValue(raterNumRatingsMap, maxNumRatings);

    System.out.println("filename = " + filename);
    System.out.println("Number of raters: " + raters.size());

    // System.out.println("Raters:");
    // for (String id : raterNumRatingsMap.keySet()) {
    // System.out.println("ID: " + id + ", # ratings: " + raterNumRatingsMap.get(id));
    // }

    String raterId = "2";
    System.out.println("# ratings for rater with ID " + raterId + ": "
        + raterNumRatingsMap.get(raterId));

    System.out.println("Maximum number of ratings by any rater: " + maxNumRatings);
    System.out.println("Raters that have the maximum number of ratings:");
    for (String id : raterIds) {
      System.out.println(id);
    }
    System.out.println("Number of such raters: " + raterIds.size());
  }

  public static void main(String[] args) {
    FirstRatings fr = new FirstRatings();
    fr.testLoadMovies();
    System.out.println();
    fr.testLoadRaters();
  }

}
