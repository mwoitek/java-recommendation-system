package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.commons.csv.CSVRecord;
import edu.duke.FileResource;

public class FirstRatings {

  public ArrayList<Movie> loadMovies(String filename) {
    ArrayList<Movie> movies = new ArrayList<Movie>();
    FileResource fr = new FileResource(filename);

    String id;
    String title;
    String year;
    String genres;
    String director;
    String country;
    String poster;
    int minutes;

    for (CSVRecord record : fr.getCSVParser()) {
      id = record.get("id");
      title = record.get("title");
      year = record.get("year");
      genres = record.get("genre");
      director = record.get("director");
      country = record.get("country");
      poster = record.get("poster");
      minutes = Integer.parseInt(record.get("minutes"));
      movies.add(new Movie(id, title, year, genres, director, country, poster, minutes));
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
    String filename = "data/ratedmoviesfull.csv";
    System.out.println("filename = " + filename);

    ArrayList<Movie> movies = loadMovies(filename);
    System.out.println("Number of movies: " + movies.size());
    System.out
        .println("# movies that have 'Comedy' as a genre: " + countComedyMovies(movies));
    System.out.println("# movies that are longer than 150 minutes: "
        + countMoviesLongerThan150Min(movies));

    HashMap<String, Integer> directorCountMap = buildDirectorCountMap(movies);
    Integer maxNumMovies = getMaximumValueInMap(directorCountMap);
    ArrayList<String> directors = getKeysWithMaxValue(directorCountMap, maxNumMovies);
    System.out.println("Maximum number of movies by any director: " + maxNumMovies);
    System.out.println("Directors that directed that many movies:");
    for (String director : directors) {
      System.out.println(director);
    }
    System.out.println("Number of such directors: " + directors.size());
  }

  public ArrayList<EfficientRater> loadRaters(String filename) {
    ArrayList<EfficientRater> raters = new ArrayList<EfficientRater>();
    FileResource fr = new FileResource(filename);

    String prevRaterId = "";
    String currRaterId;
    String movieId;
    double rating;
    int idx = -1;
    EfficientRater rater;

    for (CSVRecord record : fr.getCSVParser()) {
      currRaterId = record.get("rater_id");
      if (!currRaterId.equals(prevRaterId)) {
        raters.add(new EfficientRater(currRaterId));
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

  private HashMap<String, Integer> buildRaterNumRatingsMap(
      ArrayList<EfficientRater> raters) {
    HashMap<String, Integer> raterNumRatingsMap = new HashMap<String, Integer>();
    for (EfficientRater rater : raters) {
      raterNumRatingsMap.put(rater.getID(), rater.numRatings());
    }
    return raterNumRatingsMap;
  }

  private int countNumRatingsOfMovie(ArrayList<EfficientRater> raters, String movieId) {
    int count = 0;
    for (EfficientRater rater : raters) {
      if (rater.getItemsRated().contains(movieId)) {
        count++;
      }
    }
    return count;
  }

  private int countUniqueMovies(ArrayList<EfficientRater> raters) {
    HashSet<String> uniqueMovies = new HashSet<String>();
    for (EfficientRater rater : raters) {
      uniqueMovies.addAll(rater.getItemsRated());
    }
    return uniqueMovies.size();
  }

  public void testLoadRaters() {
    String filename = "data/ratings.csv";
    System.out.println("filename = " + filename);

    ArrayList<EfficientRater> raters = loadRaters(filename);
    System.out.println("Number of raters: " + raters.size());

    HashMap<String, Integer> raterNumRatingsMap = buildRaterNumRatingsMap(raters);
    String raterId = "193";
    System.out.println("# ratings for rater with ID " + raterId + ": "
        + raterNumRatingsMap.get(raterId));

    Integer maxNumRatings = getMaximumValueInMap(raterNumRatingsMap);
    ArrayList<String> raterIds = getKeysWithMaxValue(raterNumRatingsMap, maxNumRatings);
    System.out.println("Maximum number of ratings by any rater: " + maxNumRatings);
    System.out.println("Raters that have the maximum number of ratings:");
    for (String id : raterIds) {
      System.out.println(id);
    }
    System.out.println("Number of such raters: " + raterIds.size());

    String movieId = "1798709";
    System.out.println("Movie ID: " + movieId + ", # ratings: "
        + countNumRatingsOfMovie(raters, movieId));
    System.out
        .println("# different movies that have been rated: " + countUniqueMovies(raters));
  }

  public static void main(String[] args) {
    FirstRatings fr = new FirstRatings();
    fr.testLoadMovies();
    System.out.println();
    fr.testLoadRaters();
  }

}
