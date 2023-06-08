import java.util.List;

public class Movie implements MovieInterface{
    private String title;
    private List<String> genres;
    private Integer year;
    private String director;
    private String description;
    private Float avgVote;
    public Movie(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvgVote(Float avgVote) {
        this.avgVote = avgVote;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Integer getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public String getDescription() {
        return description;
    }

    public Float getAvgVote() {
        return avgVote;
    }
    public int compareTo(MovieInterface otherMovie){
        return (int)Math.ceil((this.getAvgVote() - otherMovie.getAvgVote()));
    }
}
