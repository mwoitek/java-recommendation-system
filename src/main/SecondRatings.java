package main;

import java.util.ArrayList;

public class SecondRatings {

  private ArrayList<Movie> myMovies;
  private ArrayList<Rater> myRaters;

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

}
