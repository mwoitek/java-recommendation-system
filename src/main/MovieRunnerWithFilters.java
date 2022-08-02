package main;

import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerWithFilters {

  public void printAverageRatings() {
    ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
    System.out.println("read data for " + tr.getRaterSize() + " raters");

    MovieDatabase.initialize("ratedmovies_short.csv");
    System.out.println("read data for " + MovieDatabase.size() + " movies");

    int minimalRaters = 1;
    ArrayList<Rating> averageRatings = tr.getAverageRatings(minimalRaters);
    Collections.sort(averageRatings);
    System.out.println("found " + averageRatings.size() + " movies");

    String movieTitle;
    for (Rating averageRating : averageRatings) {
      movieTitle = MovieDatabase.getTitle(averageRating.getItem());
      System.out.println(averageRating.getValue() + " " + movieTitle);
    }
  }

  public void printAverageRatingsByYear() {
    ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
    System.out.println("read data for " + tr.getRaterSize() + " raters");

    MovieDatabase.initialize("ratedmovies_short.csv");
    System.out.println("read data for " + MovieDatabase.size() + " movies");

    int minimalRaters = 1;
    int year = 2000;
    YearAfterFilter filter = new YearAfterFilter(year);
    ArrayList<Rating> averageRatings =
        tr.getAverageRatingsByFilter(minimalRaters, filter);
    Collections.sort(averageRatings);
    System.out.println("found " + averageRatings.size() + " movies");

    String movieId;
    int movieYear;
    String movieTitle;
    for (Rating averageRating : averageRatings) {
      movieId = averageRating.getItem();
      movieYear = MovieDatabase.getYear(movieId);
      movieTitle = MovieDatabase.getTitle(movieId);
      System.out.println(averageRating.getValue() + " " + movieYear + " " + movieTitle);
    }
  }

  public void printAverageRatingsByGenre() {
    ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
    System.out.println("read data for " + tr.getRaterSize() + " raters");

    MovieDatabase.initialize("ratedmovies_short.csv");
    System.out.println("read data for " + MovieDatabase.size() + " movies");

    int minimalRaters = 1;
    String genre = "Crime";
    GenreFilter filter = new GenreFilter(genre);
    ArrayList<Rating> averageRatings =
        tr.getAverageRatingsByFilter(minimalRaters, filter);
    Collections.sort(averageRatings);
    System.out.println("found " + averageRatings.size() + " movies");

    String movieId;
    for (Rating averageRating : averageRatings) {
      movieId = averageRating.getItem();
      System.out
          .println(averageRating.getValue() + " " + MovieDatabase.getTitle(movieId));
      System.out.println("    " + MovieDatabase.getGenres(movieId));
    }
  }

  public void printAverageRatingsByMinutes() {
    ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
    System.out.println("read data for " + tr.getRaterSize() + " raters");

    MovieDatabase.initialize("ratedmovies_short.csv");
    System.out.println("read data for " + MovieDatabase.size() + " movies");

    int minimalRaters = 1;
    int minMinutes = 110;
    int maxMinutes = 170;
    MinutesFilter filter = new MinutesFilter(minMinutes, maxMinutes);
    ArrayList<Rating> averageRatings =
        tr.getAverageRatingsByFilter(minimalRaters, filter);
    Collections.sort(averageRatings);
    System.out.println("found " + averageRatings.size() + " movies");

    String movieId;
    int movieMinutes;
    String movieTitle;
    for (Rating averageRating : averageRatings) {
      movieId = averageRating.getItem();
      movieMinutes = MovieDatabase.getMinutes(movieId);
      movieTitle = MovieDatabase.getTitle(movieId);
      System.out.println(
          averageRating.getValue() + " Time: " + movieMinutes + " " + movieTitle);
    }
  }

  public void printAverageRatingsByDirectors() {
    ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
    System.out.println("read data for " + tr.getRaterSize() + " raters");

    MovieDatabase.initialize("ratedmovies_short.csv");
    System.out.println("read data for " + MovieDatabase.size() + " movies");

    int minimalRaters = 1;
    String directors = "Charles Chaplin,Michael Mann,Spike Jonze";
    DirectorsFilter filter = new DirectorsFilter(directors);
    ArrayList<Rating> averageRatings =
        tr.getAverageRatingsByFilter(minimalRaters, filter);
    Collections.sort(averageRatings);
    System.out.println("found " + averageRatings.size() + " movies");

    String movieId;
    for (Rating averageRating : averageRatings) {
      movieId = averageRating.getItem();
      System.out
          .println(averageRating.getValue() + " " + MovieDatabase.getTitle(movieId));
      System.out.println("    " + MovieDatabase.getDirector(movieId));
    }
  }

  public void printAverageRatingsByYearAfterAndGenre() {
    ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
    System.out.println("read data for " + tr.getRaterSize() + " raters");

    MovieDatabase.initialize("ratedmovies_short.csv");
    System.out.println("read data for " + MovieDatabase.size() + " movies");

    int minimalRaters = 1;
    int year = 1980;
    String genre = "Romance";
    AllFilters filter = new AllFilters();
    filter.addFilter(new YearAfterFilter(year));
    filter.addFilter(new GenreFilter(genre));
    ArrayList<Rating> averageRatings =
        tr.getAverageRatingsByFilter(minimalRaters, filter);
    Collections.sort(averageRatings);
    System.out.println(averageRatings.size() + " movies matched");

    String movieId;
    int movieYear;
    String movieTitle;
    for (Rating averageRating : averageRatings) {
      movieId = averageRating.getItem();
      movieYear = MovieDatabase.getYear(movieId);
      movieTitle = MovieDatabase.getTitle(movieId);
      System.out.println(averageRating.getValue() + " " + movieYear + " " + movieTitle);
      System.out.println("    " + MovieDatabase.getGenres(movieId));
    }
  }

  public void printAverageRatingsByDirectorsAndMinutes() {
    ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
    System.out.println("read data for " + tr.getRaterSize() + " raters");

    MovieDatabase.initialize("ratedmovies_short.csv");
    System.out.println("read data for " + MovieDatabase.size() + " movies");

    int minimalRaters = 1;
    String directors = "Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola";
    int minMinutes = 30;
    int maxMinutes = 170;
    AllFilters filter = new AllFilters();
    filter.addFilter(new DirectorsFilter(directors));
    filter.addFilter(new MinutesFilter(minMinutes, maxMinutes));
    ArrayList<Rating> averageRatings =
        tr.getAverageRatingsByFilter(minimalRaters, filter);
    Collections.sort(averageRatings);
    System.out.println(averageRatings.size() + " movies matched");

    String movieId;
    int movieMinutes;
    String movieTitle;
    for (Rating averageRating : averageRatings) {
      movieId = averageRating.getItem();
      movieMinutes = MovieDatabase.getMinutes(movieId);
      movieTitle = MovieDatabase.getTitle(movieId);
      System.out.println(
          averageRating.getValue() + " Time: " + movieMinutes + " " + movieTitle);
      System.out.println("    " + MovieDatabase.getDirector(movieId));
    }
  }

  public static void main(String[] args) {
    MovieRunnerWithFilters runner = new MovieRunnerWithFilters();

    runner.printAverageRatings();
    System.out.println();

    runner.printAverageRatingsByYear();
    System.out.println();

    runner.printAverageRatingsByGenre();
    System.out.println();

    runner.printAverageRatingsByMinutes();
    System.out.println();

    runner.printAverageRatingsByDirectors();
    System.out.println();

    runner.printAverageRatingsByYearAfterAndGenre();
    System.out.println();

    runner.printAverageRatingsByDirectorsAndMinutes();
  }

}
