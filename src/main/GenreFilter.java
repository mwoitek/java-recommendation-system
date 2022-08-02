package main;

public class GenreFilter implements Filter {

  private String myGenre;

  public GenreFilter(String genre) {
    myGenre = genre.toLowerCase();
  }

  public boolean satisfies(String id) {
    return MovieDatabase.getGenres(id).toLowerCase().contains(myGenre);
  }

}
