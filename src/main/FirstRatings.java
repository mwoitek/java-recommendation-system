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

  private Integer getMaximumNumberMovies(HashMap<String, Integer> directorCountMap) {
    Integer maxNumMovies = -1;
    Integer numMovies;
    for (String director : directorCountMap.keySet()) {
      numMovies = directorCountMap.get(director);
      if (numMovies > maxNumMovies) {
        maxNumMovies = numMovies;
      }
    }
    return maxNumMovies;
  }

  private ArrayList<String> getDirectorsMaximumNumberMovies(
      HashMap<String, Integer> directorCountMap, Integer maxNumMovies) {
    ArrayList<String> directors = new ArrayList<String>();
    for (String director : directorCountMap.keySet()) {
      if (directorCountMap.get(director) == maxNumMovies) {
        directors.add(director);
      }
    }
    return directors;
  }

  private void printDirectorsInfo(ArrayList<Movie> movies) {
    HashMap<String, Integer> directorCountMap = buildDirectorCountMap(movies);
    Integer maxNumMovies = getMaximumNumberMovies(directorCountMap);
    System.out.println("Maximum number of movies by any director: " + maxNumMovies);
    ArrayList<String> directors =
        getDirectorsMaximumNumberMovies(directorCountMap, maxNumMovies);
    System.out.println("Directors that directed that many movies:");
    for (String director : directors) {
      System.out.println(director);
    }
  }

  public void testLoadMovies() {
    String filename = "ratedmovies_short.csv";
    ArrayList<Movie> movies = loadMovies(filename);
    System.out.println("filename = " + filename);
    System.out.println("Number of movies: " + movies.size());
    System.out.println("Movies:");
    for (Movie movie : movies) {
      System.out.println(movie);
    }
    System.out
        .println("Movies that have 'Comedy' as a genre: " + countComedyMovies(movies));
    System.out.println("Movies that are longer than 150 minutes: "
        + countMoviesLongerThan150Min(movies));
    printDirectorsInfo(movies);
    System.out.println();

    filename = "ratedmoviesfull.csv";
    movies = loadMovies(filename);
    System.out.println("filename = " + filename);
    System.out.println("Number of movies: " + movies.size());
    System.out
        .println("Movies that have 'Comedy' as a genre: " + countComedyMovies(movies));
    System.out.println("Movies that are longer than 150 minutes: "
        + countMoviesLongerThan150Min(movies));
    printDirectorsInfo(movies);
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

  public void testLoadRaters() {
    String filename = "ratings_short.csv";
    ArrayList<Rater> raters = loadRaters(filename);
    System.out.println("filename = " + filename);
    System.out.println("Number of raters: " + raters.size());
    System.out.println("Raters:");
    for (Rater rater : raters) {
      System.out.println("ID: " + rater.getID() + ", # ratings: " + rater.numRatings());
    }
    System.out.println();

    filename = "ratings.csv";
    raters = loadRaters(filename);
    System.out.println("filename = " + filename);
    System.out.println("Number of raters: " + raters.size());
  }

  public static void main(String[] args) {
    FirstRatings fr = new FirstRatings();
    // fr.testLoadMovies();
    fr.testLoadRaters();
  }

}
