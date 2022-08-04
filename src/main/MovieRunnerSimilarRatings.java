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

  private void printListOfRatings(ArrayList<Rating> similarRatings, int numRows) {
    Rating similarRating;
    String col1;
    String col2;
    String col3;
    if (numRows > similarRatings.size()) {
      numRows = similarRatings.size();
    }
    System.out.println("Rank\tWeighted Average\tTitle\n");
    for (int i = 0; i < numRows; i++) {
      similarRating = similarRatings.get(i);
      col1 = String.format("%-4s", i + 1);
      col2 = String.format("%.4f", similarRating.getValue());
      col2 = String.format("%-16s", col2);
      col3 = MovieDatabase.getTitle(similarRating.getItem());
      System.out.println(col1 + "\t" + col2 + "\t" + col3);
    }
  }

  public void printSimilarRatings() {
    FourthRatings fr = new FourthRatings();
    MovieDatabase.initialize("ratedmoviesfull.csv");
    RaterDatabase.initialize("ratings.csv");

    String id = "65";
    int numSimilarRaters = 20;
    int minimalRaters = 5;

    ArrayList<Rating> similarRatings =
        fr.getSimilarRatings(id, numSimilarRaters, minimalRaters);
    printListOfRatings(similarRatings, 15);
  }

  public static void main(String[] args) {
    MovieRunnerSimilarRatings runner = new MovieRunnerSimilarRatings();

    // runner.printAverageRatings();
    // System.out.println();

    // runner.printAverageRatingsByYearAfterAndGenre();
    // System.out.println();

    runner.printSimilarRatings();
  }

}
