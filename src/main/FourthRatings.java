package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

  private double dotProduct(Rater me, Rater r) {
    double sum = 0.0;
    double rating1;
    double rating2;
    for (String movieId : me.getItemsRated()) {
      if (r.hasRating(movieId)) {
        rating1 = me.getRating(movieId);
        rating2 = r.getRating(movieId);
        sum += (rating1 - 5.0) * (rating2 - 5.0);
      }
    }
    return sum;
  }

  private ArrayList<Rating> getSimilarities(String id) {
    ArrayList<Rating> list = new ArrayList<Rating>();
    Rater me = RaterDatabase.getRater(id);
    String raterId;
    for (Rater r : RaterDatabase.getRaters()) {
      raterId = r.getID();
      if (!raterId.equals(id)) {
        list.add(new Rating(raterId, dotProduct(me, r)));
      }
    }
    Collections.sort(list, Collections.reverseOrder());
    return list;
  }

  private double getWeightedAverage(String movieId, List<Rating> similarities,
      int minimalRaters) {
    double sum = 0.0;
    int count = 0;
    double rating;
    for (Rating r : similarities) {
      if ((rating = RaterDatabase.getRater(r.getItem()).getRating(movieId)) < 0.0) {
        continue;
      }
      sum += r.getValue() * rating;
      count++;
    }
    if (count >= minimalRaters) {
      return sum / count;
    }
    return 0.0;
  }

  public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters,
      int minimalRaters) {
    ArrayList<Rating> list = new ArrayList<Rating>();

    List<Rating> similarities = getSimilarities(id);
    if (numSimilarRaters < similarities.size()) {
      similarities = similarities.subList(0, numSimilarRaters);
    }

    ArrayList<String> movieIds = MovieDatabase.filterBy(new TrueFilter());
    double weightedAverage;
    for (String movieId : movieIds) {
      weightedAverage = getWeightedAverage(movieId, similarities, minimalRaters);
      if (weightedAverage > 0.0) {
        list.add(new Rating(movieId, weightedAverage));
      }
    }

    Collections.sort(list, Collections.reverseOrder());
    return list;
  }

}
