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
    int numMovies = 15;
    int numSimilarRaters = 20;
    int minimalRaters = 5;

    MovieDatabase.initialize("ratedmoviesfull.csv");
    RaterDatabase.initialize("ratings.csv");

    FourthRatings fr = new FourthRatings();
    ArrayList<Rating> similarRatings =
        fr.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters);

    System.out.println("<!DOCTYPE html>");
    System.out.println("<html>");

    if (similarRatings.isEmpty()) {
      System.out.println("<body>");
      System.out.println("<p>Unfortunately, there are no recommendations.</p>");
      System.out.println("</body>");
      System.out.println("</html>");
      return;
    }

    System.out.println("<head>");
    System.out.println("<style>");
    System.out
        .println("table, th, td {border: 1px solid black; border-collapse: collapse;}");
    System.out.println("th, td {padding: 1px 20px; text-align: center;}");
    System.out.println("</style>");
    System.out.println("</head>");

    System.out.println("<body>");
    System.out.println("<table>");
    System.out.println("<tr>");
    System.out.println("<th></th>");
    System.out.println("<th>Title</th>");
    System.out.println("<th>Genres</th>");
    System.out.println("<th>Minutes</th>");
    System.out.println("<th>Directed by</th>");
    System.out.println("<th>Year</th>");
    System.out.println("</tr>");

    if (numMovies > similarRatings.size()) {
      numMovies = similarRatings.size();
    }

    String movieId;
    for (int i = 0; i < numMovies; i++) {
      movieId = similarRatings.get(i).getItem();
      System.out.println("<tr>");
      System.out.println("<td><b>" + (i + 1) + "</b></td>");
      System.out.println("<td>" + MovieDatabase.getTitle(movieId) + "</td>");
      System.out.println("<td>" + MovieDatabase.getGenres(movieId) + "</td>");
      System.out.println("<td>" + MovieDatabase.getMinutes(movieId) + "</td>");
      System.out.println("<td>" + MovieDatabase.getDirector(movieId) + "</td>");
      System.out.println("<td>" + MovieDatabase.getYear(movieId) + "</td>");
      System.out.println("</tr>");
    }

    System.out.println("</table>");
    System.out.println("</body>");
    System.out.println("</html>");
  }

  public static void main(String[] args) {
    RecommendationRunner runner = new RecommendationRunner();

    // ArrayList<String> itemsToRate = runner.getItemsToRate();
    // for (String movieId : itemsToRate) {
    // System.out.println(movieId);
    // }

    runner.printRecommendationsFor("2");
  }

}
