package main;

public class MinutesFilter implements Filter {

  private int minMinutes;
  private int maxMinutes;

  public MinutesFilter(int min, int max) {
    minMinutes = min;
    maxMinutes = max;
  }

  public boolean satisfies(String id) {
    int minutes = MovieDatabase.getMinutes(id);
    return minutes >= minMinutes && minutes <= maxMinutes;
  }

}
