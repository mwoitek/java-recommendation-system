package main;

import java.util.ArrayList;

public class SecondRatings {

  private ArrayList<Movie> myMovies;
  private ArrayList<PlainRater> myRaters;

  public SecondRatings(String moviefile, String ratingsfile) {
    FirstRatings fr = new FirstRatings();
    myMovies = fr.loadMovies(moviefile);
    myRaters = fr.loadRaters(ratingsfile);
  }

  public SecondRatings() {
    this("ratedmoviesfull.csv", "ratings.csv");
  }

  public int getMovieSize() {
    return myMovies.size();
  }

  public int getRaterSize() {
    return myRaters.size();
  }

  private double getAverageByID(String id, int minimalRaters) {
    double sum = 0.0;
    int count = 0;
    double rating;
    for (PlainRater rater : myRaters) {
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
    String movieId;
    double averageRating;
    for (Movie movie : myMovies) {
      movieId = movie.getID();
      averageRating = getAverageByID(movieId, minimalRaters);
      if (averageRating > 0.0) {
        averageRatings.add(new Rating(movieId, averageRating));
      }
    }
    return averageRatings;
  }

  public String getTitle(String id) {
    int i = 0;
    while (i < myMovies.size() && !myMovies.get(i).getID().equals(id)) {
      i++;
    }
    if (i == myMovies.size()) {
      return "MOVIE ID NOT FOUND";
    }
    return myMovies.get(i).getTitle();
  }

  public String getID(String title) {
    int i = 0;
    while (i < myMovies.size() && !myMovies.get(i).getTitle().equals(title)) {
      i++;
    }
    if (i == myMovies.size()) {
      return "NO SUCH TITLE";
    }
    return myMovies.get(i).getID();
  }

}
