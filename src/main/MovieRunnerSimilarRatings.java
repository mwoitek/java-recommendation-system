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

  public void printSimilarRatings() {
    FourthRatings fr = new FourthRatings();
    MovieDatabase.initialize("ratedmoviesfull.csv");
    RaterDatabase.initialize("ratings.csv");

    String id = "65";
    int numSimilarRaters = 20;
    int minimalRaters = 5;

    ArrayList<Rating> similarRatings =
        fr.getSimilarRatings(id, numSimilarRaters, minimalRaters);

    int numRows = 15;
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

  public void printSimilarRatingsByGenre() {
    FourthRatings fr = new FourthRatings();
    MovieDatabase.initialize("ratedmoviesfull.csv");
    RaterDatabase.initialize("ratings.csv");

    String id = "65";
    int numSimilarRaters = 20;
    int minimalRaters = 5;

    String genre = "Action";
    GenreFilter filter = new GenreFilter(genre);

    ArrayList<Rating> similarRatings =
        fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, filter);

    int numRows = 15;
    int titleColLength = 40;
    String titleColFormat = "%-" + titleColLength + "s";
    Rating similarRating;
    String movieId;
    String col1;
    String col2;
    String col3;
    String col4;

    if (numRows > similarRatings.size()) {
      numRows = similarRatings.size();
    }

    System.out.println("Rank\tWeighted Average\t" + String.format(titleColFormat, "Title")
        + "\tGenres\n");
    for (int i = 0; i < numRows; i++) {
      similarRating = similarRatings.get(i);
      movieId = similarRating.getItem();
      col1 = String.format("%-4s", i + 1);
      col2 = String.format("%.4f", similarRating.getValue());
      col2 = String.format("%-16s", col2);
      col3 = MovieDatabase.getTitle(movieId);
      if (col3.length() > titleColLength) {
        col3 = col3.substring(0, titleColLength - 3) + "...";
      }
      col3 = String.format(titleColFormat, col3);
      col4 = MovieDatabase.getGenres(movieId);
      System.out.println(col1 + "\t" + col2 + "\t" + col3 + "\t" + col4);
    }
  }

  public void printSimilarRatingsByDirector() {
    FourthRatings fr = new FourthRatings();
    MovieDatabase.initialize("ratedmoviesfull.csv");
    RaterDatabase.initialize("ratings.csv");

    String id = "1034";
    int numSimilarRaters = 10;
    int minimalRaters = 3;

    String directors = "Clint Eastwood,Sydney Pollack,David Cronenberg,Oliver Stone";
    DirectorsFilter filter = new DirectorsFilter(directors);

    ArrayList<Rating> similarRatings =
        fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, filter);

    int numRows = 15;
    int titleColLength = 40;
    String titleColFormat = "%-" + titleColLength + "s";
    Rating similarRating;
    String movieId;
    String col1;
    String col2;
    String col3;
    String col4;

    if (numRows > similarRatings.size()) {
      numRows = similarRatings.size();
    }

    System.out.println("Rank\tWeighted Average\t" + String.format(titleColFormat, "Title")
        + "\tDirectors\n");
    for (int i = 0; i < numRows; i++) {
      similarRating = similarRatings.get(i);
      movieId = similarRating.getItem();
      col1 = String.format("%-4s", i + 1);
      col2 = String.format("%.4f", similarRating.getValue());
      col2 = String.format("%-16s", col2);
      col3 = MovieDatabase.getTitle(movieId);
      if (col3.length() > titleColLength) {
        col3 = col3.substring(0, titleColLength - 3) + "...";
      }
      col3 = String.format(titleColFormat, col3);
      col4 = MovieDatabase.getDirector(movieId);
      System.out.println(col1 + "\t" + col2 + "\t" + col3 + "\t" + col4);
    }
  }

  public void printSimilarRatingsByGenreAndMinutes() {
    FourthRatings fr = new FourthRatings();
    MovieDatabase.initialize("ratedmoviesfull.csv");
    RaterDatabase.initialize("ratings.csv");

    String id = "65";
    int numSimilarRaters = 10;
    int minimalRaters = 5;

    int minMinutes = 100;
    int maxMinutes = 200;
    String genre = "Adventure";
    AllFilters filter = new AllFilters();
    filter.addFilter(new MinutesFilter(minMinutes, maxMinutes));
    filter.addFilter(new GenreFilter(genre));

    ArrayList<Rating> similarRatings =
        fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, filter);

    int numRows = 15;
    int titleColLength = 40;
    String titleColFormat = "%-" + titleColLength + "s";
    Rating similarRating;
    String movieId;
    String col1;
    String col2;
    String col3;
    String col4;
    String col5;

    if (numRows > similarRatings.size()) {
      numRows = similarRatings.size();
    }

    System.out.println("Rank\tWeighted Average\t" + String.format(titleColFormat, "Title")
        + "\tMinutes\t\tGenres\n");
    for (int i = 0; i < numRows; i++) {
      similarRating = similarRatings.get(i);
      movieId = similarRating.getItem();
      col1 = String.format("%-4s", i + 1);
      col2 = String.format("%.4f", similarRating.getValue());
      col2 = String.format("%-16s", col2);
      col3 = MovieDatabase.getTitle(movieId);
      if (col3.length() > titleColLength) {
        col3 = col3.substring(0, titleColLength - 3) + "...";
      }
      col3 = String.format(titleColFormat, col3);
      col4 = String.format("%-7s", MovieDatabase.getMinutes(movieId));
      col5 = MovieDatabase.getGenres(movieId);
      System.out.println(col1 + "\t" + col2 + "\t" + col3 + "\t" + col4 + "\t\t" + col5);
    }
  }

  public void printSimilarRatingsByYearAfterAndMinutes() {
    FourthRatings fr = new FourthRatings();
    MovieDatabase.initialize("ratedmoviesfull.csv");
    RaterDatabase.initialize("ratings.csv");

    String id = "65";
    int numSimilarRaters = 10;
    int minimalRaters = 5;

    int minMinutes = 80;
    int maxMinutes = 100;
    int year = 2000;
    AllFilters filter = new AllFilters();
    filter.addFilter(new MinutesFilter(minMinutes, maxMinutes));
    filter.addFilter(new YearAfterFilter(year));

    ArrayList<Rating> similarRatings =
        fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, filter);

    int numRows = 15;
    int titleColLength = 40;
    String titleColFormat = "%-" + titleColLength + "s";
    Rating similarRating;
    String movieId;
    String col1;
    String col2;
    String col3;
    String col4;
    String col5;

    if (numRows > similarRatings.size()) {
      numRows = similarRatings.size();
    }

    System.out.println("Rank\tWeighted Average\t" + String.format(titleColFormat, "Title")
        + "\tMinutes\t\tYear\n");
    for (int i = 0; i < numRows; i++) {
      similarRating = similarRatings.get(i);
      movieId = similarRating.getItem();
      col1 = String.format("%-4s", i + 1);
      col2 = String.format("%.4f", similarRating.getValue());
      col2 = String.format("%-16s", col2);
      col3 = MovieDatabase.getTitle(movieId);
      if (col3.length() > titleColLength) {
        col3 = col3.substring(0, titleColLength - 3) + "...";
      }
      col3 = String.format(titleColFormat, col3);
      col4 = String.format("%-7s", MovieDatabase.getMinutes(movieId));
      col5 = String.format("%-4s", MovieDatabase.getYear(movieId));
      System.out.println(col1 + "\t" + col2 + "\t" + col3 + "\t" + col4 + "\t\t" + col5);
    }
  }

  public static void main(String[] args) {
    MovieRunnerSimilarRatings runner = new MovieRunnerSimilarRatings();

    // runner.printAverageRatings();
    // System.out.println();

    // runner.printAverageRatingsByYearAfterAndGenre();
    // System.out.println();

    runner.printSimilarRatings();
    System.out.println();

    runner.printSimilarRatingsByGenre();
    System.out.println();

    runner.printSimilarRatingsByDirector();
    System.out.println();

    runner.printSimilarRatingsByGenreAndMinutes();
    System.out.println();

    runner.printSimilarRatingsByYearAfterAndMinutes();
  }

}
