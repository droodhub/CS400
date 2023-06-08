import java.io.FileReader;
import java.util.List;
import java.util.LinkedList;

public class MovieDataReaderInterface {
    public static List<MovieInterface> readDataSet(FileReader reader){
        Movie m1 = new Movie();
        Movie m2 = new Movie();
        Movie m3 = new Movie();
        m1.setTitle("Pulp Fiction");
        m1.setYear(1979);
        m1.setDirector("Quentin Tarantino");
        m1.setAvgVote((float) 7.8);
        List<String> genres1 = new LinkedList<String>();
        genres1.add("Action");
        genres1.add("Violence");
        genres1.add("Thriller");
        m1.setGenres(genres1);
        String description1 = "A collection of individual stories come together culminating in a fantastic collision of different walks of life: a gangster bodyguard, a boxer trying to escape his life, and more.";
        m1.setDescription(description1);
        m2.setTitle("Real Steel");
        m2.setYear(2012);
        m2.setDirector("Guillermo Del Toro");
        m2.setAvgVote((float) 4.6);
        List<String> genres2 = new LinkedList<String>();
        genres2.add("Sci-Fi");
        genres2.add("Heartwarming");
        genres2.add("Action");
        m2.setGenres(genres2);
        String description2 = "A former boxer and his estranged son come together to fight together with a robot.";
        m2.setDescription(description2);
        m3.setTitle("Dumbo");
        m3.setYear(1463);
        m3.setDirector("Walt Disney");
        m3.setAvgVote((float) 6.1);
        List<String> genres3 = new LinkedList<String>();
        genres3.add("Family");
        m3.setGenres(genres3);
        String description3 = "Didney Movie";
        m3.setDescription(description3);
        List<MovieInterface> returning = new LinkedList<MovieInterface>();
        returning.add(m1);
        returning.add(m2);
        returning.add(m3);
        return returning;
    }
}
