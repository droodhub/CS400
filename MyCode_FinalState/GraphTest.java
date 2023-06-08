import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;
// --== CS400 File Header Information ==--
// Name: Andrew McAvoy
// Email: apmcavoy@wisc.edu
// Team: HA
// TA: Unknown
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
/**
 * Tests the implementation of CS400Graph for the individual component of
 * Project Three: the implementation of Dijsktra's Shortest Path algorithm.
 */
public class GraphTest {

    private CS400Graph<String> graph;

    /**
     * Instantiate graph from last week's shortest path activity.
     */
    @BeforeEach
    public void createGraph() {
        graph = new CS400Graph<>();
        // insert vertices A-E
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        // insert edges from Week 09. Dijkstra's Activity
        graph.insertEdge("A","B",2);
        graph.insertEdge("A","D",4);
        graph.insertEdge("A","E",1);
        graph.insertEdge("B","C",5);
        graph.insertEdge("C","A",3);
        graph.insertEdge("D","B",3);
        graph.insertEdge("D","C",7);
        graph.insertEdge("D","E",1);
        graph.insertEdge("E","C",8);
    }

    /**
     * Checks the distance/total weight cost from the vertex labelled C to E
     * (should be 4), and from the vertex labelled A to C (should be 7).
     */
    @Test
    public void providedTestToCheckPathCosts() {
        assertTrue(graph.getPathCost("C", "E") == 4);
        assertTrue(graph.getPathCost("A", "C") == 7);
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex 
     * labelled C to E, and from the vertex labelled A to C.
     */
    @Test
    public void providedTestToCheckPathContents() {
        assertTrue(graph.shortestPath("C", "E").toString().equals(
                "[C, A, E]"
        ));
        assertTrue(graph.shortestPath("A", "C").toString().equals(
                "[A, B, C]"
        ));
    }
    /**
     * Checks the ordered sequence of data within vertices from the B to
     * all other vertices in the graph
     */
    @Test
    public void TestDijkstraHandPaths(){
        assertTrue(graph.shortestPath("B", "C").toString().equals("[B, C]"));
        assertTrue(graph.shortestPath("B", "A").toString().equals("[B, C, A]"));
        assertTrue(graph.shortestPath("B", "E").toString().equals("[B, C, A, E]"));
        assertTrue(graph.shortestPath("B", "D").toString().equals("[B, C, A, D]"));
    }
    /**
     * Checks the distance/total weight cost from the vertex labelled B to
     * all other vertices in the graph
     */
    @Test
    public void TestDijkstraHandWeights(){
        assertTrue(graph.getPathCost("B", "C") == 5);
        assertTrue(graph.getPathCost("B", "A") == 8);
        assertTrue(graph.getPathCost("B", "E") == 9);
        assertTrue(graph.getPathCost("B", "D") == 12);
    }
    /**
     * Checks the order of data from A to B
     */
    @Test
    public void TestDijkstraAlternatePath(){
        assertTrue(graph.shortestPath("A", "C").toString().equals("[A, B, C]"));
    }
    /**
     * Makes sure the method throws a noSuchElementException
     * if the node is unreachable
     */
    @Test
    public void TestImpossibleGraph(){
            graph.removeEdge("A", "D");
            assertThrows(NoSuchElementException.class , () -> {graph.shortestPath("A", "D");});
    }
}