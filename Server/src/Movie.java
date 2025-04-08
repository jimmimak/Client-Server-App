import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String director;
    private String genre;
    private String duration;
    private String summary;

    Movie(String title, String director, String genre, String duration, String summary) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.duration = duration;
        this.summary = summary;
    }

    String getTitle() {
        return title;
    }
    
    boolean directorEq(String director){
        return this.director.equals(director);
    }

    @Override
    public String toString() {
        return "\n\nTitle: " + title + 
                "\nDirector: " + director + 
                "\nGenre: " + genre + 
                "\nDuration: " + duration +
                "\nSummary:" + summary;
    }
}
