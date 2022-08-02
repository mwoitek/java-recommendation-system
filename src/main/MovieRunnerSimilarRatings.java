package main;

import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {

  public void printAverageRatings() {
    RaterDatabase.initialize("ratings.csv");
    System.out.println("read data for " + RaterDatabase.size() + " raters");

    MovieDatabase.initialize("ratedmoviesfull.csv");
    System.out.println("read data for " + MovieDatabase.size() + " movies");

    FourthRatings fr = new FourthRatings();
    int minimalRaters = 35;
    ArrayList<Rating> averageRatings = fr.getAverageRatings(minimalRaters);
    Collections.sort(averageRatings);
    System.out.println("found " + averageRatings.size() + " movies");

    String movieTitle;
    for (Rating averageRating : averageRatings) {
      movieTitle = MovieDatabase.getTitle(averageRating.getItem());
      System.out.println(averageRating.getValue() + " " + movieTitle);
    }
  }

  public void printAverageRatingsByYearAfterAndGenre() {
    RaterDatabase.initialize("ratings.csv");
    System.out.println("read data for " + RaterDatabase.size() + " raters");

    MovieDatabase.initialize("ratedmoviesfull.csv");
    System.out.println("read data for " + MovieDatabase.size() + " movies");

    int year = 1990;
    String genre = "Drama";
    AllFilters filter = new AllFilters();
    filter.addFilter(new YearAfterFilter(year));
    filter.addFilter(new GenreFilter(genre));

    FourthRatings fr = new FourthRatings();
    int minimalRaters = 8;
    ArrayList<Rating> averageRatings =
        fr.getAverageRatingsByFilter(minimalRaters, filter);
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

  public static void main(String[] args) {
    MovieRunnerSimilarRatings runner = new MovieRunnerSimilarRatings();

    runner.printAverageRatings();
    System.out.println();

    runner.printAverageRatingsByYearAfterAndGenre();
  }

}
