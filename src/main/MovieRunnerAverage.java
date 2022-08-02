package main;

import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {

  public void printAverageRatings() {
    SecondRatings sr = new SecondRatings();
    System.out.println("Number of movies: " + sr.getMovieSize());
    System.out.println("Number of raters: " + sr.getRaterSize());

    int minimalRaters = 12;
    ArrayList<Rating> averageRatings = sr.getAverageRatings(minimalRaters);
    Collections.sort(averageRatings);

    String movieTitle;
    for (Rating averageRating : averageRatings) {
      movieTitle = sr.getTitle(averageRating.getItem());
      System.out.println(averageRating.getValue() + " " + movieTitle);
    }
  }

  public void getAverageRatingOneMovie() {
    SecondRatings sr = new SecondRatings();

    String movieTitle = "Vacation";
    String movieId = sr.getID(movieTitle);

    ArrayList<Rating> averageRatings = sr.getAverageRatings(1);
    int i = 0;
    while (i < averageRatings.size()
        && !averageRatings.get(i).getItem().equals(movieId)) {
      i++;
    }

    if (i == averageRatings.size()) {
      System.out.println("NO SUCH TITLE");
    } else {
      System.out.println(
          "Average rating for " + movieTitle + ": " + averageRatings.get(i).getValue());
    }
  }

  public static void main(String[] args) {
    MovieRunnerAverage runner = new MovieRunnerAverage();
    runner.printAverageRatings();
    System.out.println();
    runner.getAverageRatingOneMovie();
  }

}
