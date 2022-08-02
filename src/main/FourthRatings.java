package main;

import java.util.ArrayList;

public class FourthRatings {

  private double getAverageByID(String id, int minimalRaters) {
    double sum = 0.0;
    int count = 0;
    double rating;
    for (Rater rater : RaterDatabase.getRaters()) {
      if ((rating = rater.getRating(id)) < 0.0) {
        continue;
      }
      sum += rating;
      count++;
    }
    if (count >= minimalRaters) {
      return sum / count;
    }
    return 0.0;
  }

  public ArrayList<Rating> getAverageRatings(int minimalRaters) {
    ArrayList<Rating> averageRatings = new ArrayList<Rating>();
    ArrayList<String> movieIds = MovieDatabase.filterBy(new TrueFilter());
    double averageRating;
    for (String movieId : movieIds) {
      if ((averageRating = getAverageByID(movieId, minimalRaters)) > 0.0) {
        averageRatings.add(new Rating(movieId, averageRating));
      }
    }
    return averageRatings;
  }

  public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters,
      Filter filterCriteria) {
    ArrayList<Rating> averageRatings = new ArrayList<Rating>();
    ArrayList<String> movieIds = MovieDatabase.filterBy(filterCriteria);
    double averageRating;
    for (String movieId : movieIds) {
      if ((averageRating = getAverageByID(movieId, minimalRaters)) > 0.0) {
        averageRatings.add(new Rating(movieId, averageRating));
      }
    }
    return averageRatings;
  }

}
