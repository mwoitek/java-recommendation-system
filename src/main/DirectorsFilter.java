package main;

import java.util.ArrayList;
import java.util.Arrays;

public class DirectorsFilter implements Filter {

  private ArrayList<String> directorsList;

  public DirectorsFilter(String directors) {
    directorsList = new ArrayList<String>(Arrays.asList(directors.split(",")));
  }

  public boolean satisfies(String id) {
    String directors = MovieDatabase.getDirector(id);
    for (String director : directorsList) {
      if (directors.contains(director)) {
        return true;
      }
    }
    return false;
  }

}
