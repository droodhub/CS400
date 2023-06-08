import java.util.List;

public interface MovieInterface extends Comparable<MovieInterface>{
    public String getTitle();
    public List<String> getGenres();
    public Integer getYear();
    public String getDirector();
    public String getDescription();
    public Float getAvgVote();
    public int compareTo(MovieInterface otherMovie);
}
