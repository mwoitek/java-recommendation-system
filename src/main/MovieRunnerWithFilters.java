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

  public static void main(String[] args) {
    MovieRunnerWithFilters runner = new MovieRunnerWithFilters();
    runner.printAverageRatings();
  }

}
