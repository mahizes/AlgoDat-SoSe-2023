import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
/**
 * Class that represents a maze with N*N junctions.
 * 
 * @author Vera Röhr
 */
public class Maze{
    private final int N;
    private Graph M;    //Maze
    public int startnode;
        
	public Maze(int N, int startnode) {
		
        if (N < 0) throw new IllegalArgumentException("Number of vertices in a row must be nonnegative");
        this.N = N;
        this.M= new Graph(N*N);
        this.startnode= startnode;
        buildMaze();
	}
	
    public Maze (In in) {
    	this.M = new Graph(in);
    	this.N= (int) Math.sqrt(M.V());
    	this.startnode=0;
    }

	
    /**
     * Adds the undirected edge v-w to the graph M.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        M.addEdge(v, w);
    }
    
    /**
     * Returns true if there is an edge between 'v' and 'w'
     * @param v one vertex
     * @param w another vertex
     * @return true or false
     */
    public boolean hasEdge( int v, int w){
        if (v < 0 || v >= M.V() || w < 0 || w >= M.V()) {
            return false;  // Return false if either vertex is out of range
        }
        if (v == w) {
            return true;  // A vertex always has a reflexive edge
        }
        for (int adj : M.adj(v)) {
            if (adj == w) {
                return true;
            }
        }
        return false;
    }	
    
    /**
     * Builds a grid as a graph.
     * @return Graph G -- Basic grid on which the Maze is built
     */
    public Graph mazegrid() {
        Graph G = new Graph(N * N);
        for (int v = 0; v < N * N; v++) {
            if (v % N != N - 1) G.addEdge(v, v + 1); // add edge to right neighbor
            if (v < N * (N - 1)) G.addEdge(v, v + N); // add edge to bottom neighbor
        }
        return G;
    }
    
    /**
     * Builds a random maze as a graph.
     * The maze is build with a randomized DFS as the Graph M.
     */
    private void buildMaze() {
        Graph grid = mazegrid();
        M = new Graph(grid.V());  // Initialize M as a new graph with the same number of vertices as grid
        RandomDepthFirstPaths randomDFS = new RandomDepthFirstPaths(grid, startnode);
        randomDFS.randomDFS(grid);
        int[] edges = randomDFS.edge();
        for (int vertex = 1; vertex < edges.length; vertex++) {
            M.addEdge(vertex, edges[vertex]);
        }
    }

    /**
     * Find a path from node v to w
     * @param v start node
     * @param w end node
     * @return List<Integer> -- a list of nodes on the path from v to w (both included) in the right order.
     */
    public List<Integer> findWay(int v, int w){
        DepthFirstPaths dfs = new DepthFirstPaths(M, v);
        dfs.nonrecursiveDFS(M);
        return dfs.pathTo(w);
    }
    
    /**
     * @return Graph M
     */
    public Graph M() {
    	return M;
    }

    public static void main(String[] args) {
		// FOR TESTING
    }


}

