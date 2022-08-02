package main;

import java.util.ArrayList;

public class ThirdRatings {

  private ArrayList<EfficientRater> myRaters;

  public ThirdRatings(String ratingsfile) {
    FirstRatings fr = new FirstRatings();
    myRaters = fr.loadRaters(ratingsfile);
  }

  public ThirdRatings() {
    this("data/ratings.csv");
  }

  public int getRaterSize() {
    return myRaters.size();
  }

  private double getAverageByID(String id, int minimalRaters) {
    double sum = 0.0;
    int count = 0;
    double rating;
    for (EfficientRater rater : myRaters) {
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

}
