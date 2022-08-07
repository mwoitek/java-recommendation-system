package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RecommendationRunner implements Recommender {

  public ArrayList<String> getItemsToRate() {
    int numMovies = 15;
    int minimalRaters = 5;
    int numTopMovies = 45;

    MovieDatabase.initialize("ratedmoviesfull.csv");
    RaterDatabase.initialize("ratings.csv");

    FourthRatings fr = new FourthRatings();
    ArrayList<Rating> averageRatings = fr.getAverageRatings(minimalRaters);
    Collections.sort(averageRatings, Collections.reverseOrder());

    if (numTopMovies > averageRatings.size()) {
      numTopMovies = averageRatings.size();
    }

    ArrayList<String> itemsToRate = new ArrayList<>();
    Random rng = new Random();
    int randomIndex;
    String movieId;

    while (itemsToRate.size() < numMovies) {
      randomIndex = rng.nextInt(numTopMovies);
      movieId = averageRatings.get(randomIndex).getItem();
      if (itemsToRate.contains(movieId)) {
        continue;
      }
      itemsToRate.add(movieId);
    }

    return itemsToRate;
  }

  public void printRecommendationsFor(String webRaterID) {
    //
  }

  public static void main(String[] args) {
    RecommendationRunner runner = new RecommendationRunner();

    ArrayList<String> itemsToRate = runner.getItemsToRate();
    for (String movieId : itemsToRate) {
      System.out.println(movieId);
    }

    //
  }

}
