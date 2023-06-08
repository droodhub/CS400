import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class BackendOperator implements BackEndInterface{
private List<String> genreSearch = new LinkedList<String>();
private List<String> ratingSearch = new LinkedList<String>();
private List<MovieInterface> inRange = new LinkedList<MovieInterface>();

private List<String> allGenres = new ArrayList<String>();
private int totalMovies;

private List<MovieInterface> dataSet = new ArrayList<MovieInterface>();
private HashTableMap<String, LinkedList<MovieInterface>> genreTable;
private HashTableMap<String, LinkedList<MovieInterface>> ratingsTable;




    public BackendOperator(String fileName){
        totalMovies = 0;
        try {
            FileReader reader = new FileReader(fileName);
            dataSet = MovieDataReaderInterface.readDataSet(reader);
            initialize();
            genreTable = new HashTableMap<String, LinkedList<MovieInterface>>(dataSet.size());
            ratingsTable = new HashTableMap<String, LinkedList<MovieInterface>>(dataSet.size());
        } catch (FileNotFoundException e) {
            System.out.println("Movie File not found.");
            e.printStackTrace();
        }

    }

    @Override
    public void addGenre(String genre) {
    if(!genreSearch.contains(genre) && allGenres.contains(genre))
        genreSearch.add(genre);
    getInRange();
    }

    @Override
    public void addAvgRating(String rating) {
    if(!ratingSearch.contains(rating))
        ratingSearch.add(rating);
    getInRange();
    }

    @Override
    public void removeGenre(String genre) {
        if(genreSearch.contains(genre))
            genreSearch.remove(genre);
        getInRange();
    }

    @Override
    public void removeAvgRating(String rating) {
        if(ratingSearch.contains(rating))
            ratingSearch.remove(rating);
        getInRange();
    }

    @Override
    public List<String> getGenres() {
        return genreSearch;
    }

    @Override
    public List<String> getAvgRatings() {
        return ratingSearch;
    }

    @Override
    public int getNumberOfMovies() {
        return totalMovies;
    }

    @Override
    public List<MovieInterface> getThreeMovies(int startingIndex) {
        List<MovieInterface> threeMovies = new ArrayList<MovieInterface>(3);
        getInRange();
        if(startingIndex > inRange.size() -1)
            return threeMovies;
        for(int i = 0; i < 3; i++){
            if(i < inRange.size()-1)
                threeMovies.add(inRange.get(startingIndex+i));
        }
        Collections.sort(threeMovies);
        return threeMovies;
    }

    @Override
    public List<String> getAllGenres() {
        return allGenres;
    }
    private List<MovieInterface> getInRange(){
        if(!genreSearch.isEmpty() && !ratingSearch.isEmpty()){
            for(String s : genreSearch){
                List<MovieInterface> curr = genreTable.get(s);
                for(MovieInterface movie : curr){
                    if(!inRange.contains(movie)) {
                        inRange.add(movie);
                        totalMovies++;
                    }
                }
            }
            for(String s : ratingSearch){
                List<MovieInterface> curr = ratingsTable.get(s);
                for(MovieInterface movie : curr){
                    if(!inRange.contains(movie)) {
                        inRange.add(movie);
                        totalMovies++;
                    }
                }
            }
        }
        else if(!genreSearch.isEmpty()){
            for(String s : genreSearch){
                List<MovieInterface> curr = genreTable.get(s);
                for(MovieInterface movie : curr){
                    if(!inRange.contains(movie)) {
                        inRange.add(movie);
                        totalMovies++;
                    }
                }
            }
        }
        else if(!ratingSearch.isEmpty()){
            for(String s : ratingSearch){
                List<MovieInterface> curr = ratingsTable.get(s);
                for(MovieInterface movie : curr){
                    if(!inRange.contains(movie)) {
                        inRange.add(movie);
                        totalMovies++;
                    }
                }
            }
        }
        return inRange;
    }
    private void initialize(){

    for(MovieInterface movie : dataSet){
        for(String currGenre : movie.getGenres()){
            if(genreTable.containsKey(currGenre)) {
                genreTable.put(currGenre, new LinkedList<MovieInterface>());
                allGenres.add(currGenre);
            }
            genreTable.get(currGenre).add(movie);
        }
            if(!ratingsTable.containsKey((Double.toString(Math.floor(movie.getAvgVote()))))) {
                ratingsTable.put(Double.toString(Math.floor(movie.getAvgVote())), new LinkedList<MovieInterface>());
            }
            ratingsTable.get(Double.toString(Math.floor(movie.getAvgVote()))).add(movie);
    }
    }
}
