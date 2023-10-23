import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class RandomDepthFirstPaths {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public RandomDepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
    }
	    
	public void randomDFS(Graph G) {
		randomDFS(G,s);
	}
	
    // Helper method to update pre-order, post-order, and distance to start node
    private void update(int v, int w, List<Integer> preorder, List<Integer> postorder, int[] distTo) {
        preorder.add(w); // add to preorder list
        edgeTo[w] = v; // set edge to parent node
        distTo[w] = distTo[v] + 1; // set distance to start node
    }

    // depth first search from v
    private void randomDFS(Graph G, int v) {
        marked[v] = true;
        List<Integer> preorder = new ArrayList<>();
        List<Integer> postorder = new ArrayList<>();
        int[] distTo = new int[G.V()];
        List<Integer> adjNodes = new ArrayList<>();
        for (int w : G.adj(v)) {
            adjNodes.add(w);
        }
        Collections.shuffle(adjNodes); // randomize the order of adjacent nodes
        for (int w : adjNodes) {
            if (!marked[w]) {
                update(v, w, preorder, postorder, distTo);
                randomDFS(G, w);
            }
        }
        postorder.add(v); // add to postorder list
    }
    
    public void randomNonrecursiveDFS(Graph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        List<Iterator<Integer>> adj = new ArrayList<>();
        for (int v = 0; v < G.V(); v++) {
            List<Integer> adjList = new ArrayList<>();
            for (int w : G.adj(v)) {
                adjList.add(w);
            }
            Collections.shuffle(adjList);
            adj.add(adjList.iterator());
        }

        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                edgeTo[s] = s;
                Stack<Integer> stack = new Stack<Integer>();
                marked[s] = true;
                stack.push(s);
                while (!stack.isEmpty()) {
                    int v = stack.peek();
                    if (adj.get(v).hasNext()) {
                        int w = adj.get(v).next();
                        if (!marked[w]) {
                            marked[w] = true;
                            edgeTo[w] = v;
                            stack.push(w);
                        }
                    } else {
                        stack.pop();
                    }
                }
            }
        }
    }

    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a path between the source vertex {@code s} and vertex {@code v}, or
     * {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a path between the source vertex
     *         {@code s} and vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * 
     * This method is different compared to the original one.
     */
    public List<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        List<Integer> pathList = new ArrayList<Integer>();
        while (!path.empty())
            pathList.add(path.pop());
        return pathList;
    }
    
    public int [] edge() {
    	return edgeTo;
    }  

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

}

