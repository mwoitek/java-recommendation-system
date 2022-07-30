package main;

import java.util.ArrayList;
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

  public void testLoadMovies() {
    ArrayList<Movie> movies = loadMovies("ratedmovies_short.csv");
    System.out.println("Number of movies: " + movies.size());
    System.out.println("Movies:");
    for (Movie movie : movies) {
      System.out.println(movie);
    }

    movies = loadMovies("ratedmoviesfull.csv");
    System.out.println("Number of movies: " + movies.size());
  }

  public static void main(String[] args) {
    FirstRatings fr = new FirstRatings();
    fr.testLoadMovies();
  }

}
