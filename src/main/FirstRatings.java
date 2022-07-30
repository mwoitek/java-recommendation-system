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

  public int countComedyMovies(ArrayList<Movie> movies) {
    int count = 0;
    for (Movie movie : movies) {
      if (movie.getGenres().toLowerCase().contains("comedy")) {
        count++;
      }
    }
    return count;
  }

  public int countMoviesLongerThan150Min(ArrayList<Movie> movies) {
    int count = 0;
    for (Movie movie : movies) {
      if (movie.getMinutes() > 150) {
        count++;
      }
    }
    return count;
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
    System.out.println();

    filename = "ratedmoviesfull.csv";
    movies = loadMovies(filename);
    System.out.println("filename = " + filename);
    System.out.println("Number of movies: " + movies.size());
    System.out
        .println("Movies that have 'Comedy' as a genre: " + countComedyMovies(movies));
    System.out.println("Movies that are longer than 150 minutes: "
        + countMoviesLongerThan150Min(movies));
  }

  public static void main(String[] args) {
    FirstRatings fr = new FirstRatings();
    fr.testLoadMovies();
  }

}
