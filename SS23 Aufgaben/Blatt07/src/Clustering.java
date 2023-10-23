import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.awt.Color;

/**
 * This class solves a clustering problem with the Prim algorithm.
 */
public class Clustering {
	EdgeWeightedGraph G;
	List<List<Integer>> clusters;
	List<List<Integer>> labeled;
	private List<String> labels;

	/**
	 * Constructor for the Clustering class, for a given EdgeWeightedGraph and no
	 * labels.
	 * 
	 * @param G a given graph representing a clustering problem
	 */
	public Clustering(EdgeWeightedGraph G) {
		this.G = G;
		clusters = new LinkedList<List<Integer>>();
	}

	/**
	 * Constructor for the Clustering class, for a given data set with labels
	 * 
	 * @param in input file for a clustering data set with labels
	 */
	public Clustering(In in) {
		int V = in.readInt();
		int dim = in.readInt();
		G = new EdgeWeightedGraph(V);
		labeled = new LinkedList<List<Integer>>();
		LinkedList labels = new LinkedList();
		double[][] coord = new double[V][dim];
		for (int v = 0; v < V; v++) {
			for (int j = 0; j < dim; j++) {
				coord[v][j] = in.readDouble();
			}
			String label = in.readString();
			if (labels.contains(label)) {
				labeled.get(labels.indexOf(label)).add(v);
			} else {
				labels.add(label);
				List<Integer> l = new LinkedList<Integer>();
				labeled.add(l);
				labeled.get(labels.indexOf(label)).add(v);
				System.out.println(label);
			}
		}

		G.setCoordinates(coord);
		for (int w = 0; w < V; w++) {
			for (int v = 0; v < V; v++) {
				if (v != w) {
					double weight = 0;
					for (int j = 0; j < dim; j++) {
						weight = weight + Math.pow(G.getCoordinates()[v][j] - G.getCoordinates()[w][j], 2);
					}
					weight = Math.sqrt(weight);
					Edge e = new Edge(v, w, weight);
					G.addEdge(e);
				}
			}
		}
		clusters = new LinkedList<List<Integer>>();
	}

	/**
	 * This method finds a specified number of clusters based on a MST.
	 *
	 * It is based in the idea that removing edges from a MST will create a
	 * partition into several connected components, which are the clusters.
	 * 
	 * @param numberOfClusters number of expected clusters
	 */
	public void findClusters(int numberOfClusters) {
		// 1. Create a minimum spanning tree (MST) from the graph G using Prim's
		// algorithm.
		boolean[] marked = new boolean[G.V()]; // marked[v] = true if v on tree
		Queue<Edge> mst = new PriorityQueue<>(); // edges in the MST
		Queue<Edge> pq = new PriorityQueue<>(); // edges with one endpoint in tree

		visit(G, 0, marked, mst, pq);
		while (!pq.isEmpty()) {
			Edge e = pq.poll(); // Get lowest-weight edge from pq.
			int v = e.either(), w = e.other(v);
			if (marked[v] && marked[w])
				continue; // Skip if ineligible.
			mst.add(e); // Add edge to tree.
			if (!marked[v])
				visit(G, v, marked, mst, pq);
			if (!marked[w])
				visit(G, w, marked, mst, pq);
		}

		// 2. Remove the (numberOfClusters - 1) largest edges from the MST.
		List<Edge> mstEdges = new ArrayList<>(mst);
		mstEdges.sort(Comparator.comparingDouble(Edge::weight).reversed());
		for (int i = 0; i < numberOfClusters - 1; i++) {
			mstEdges.remove(0);
		}

		// 3. Each connected component of the resulting graph is a cluster.
		// We use a simple Union-Find structure to find these connected components.
		int[] id = new int[G.V()];
		for (int i = 0; i < id.length; i++) {
			id[i] = i;
		}
		for (Edge e : mstEdges) {
			int v = e.either(), w = e.other(v);
			union(v, w, id); // Use the union method here.
		}

		// 4. Store these clusters in the 'clusters' field.
		clusters = new ArrayList<>();
		Map<Integer, List<Integer>> clusterMap = new HashMap<>();
		for (int v = 0; v < G.V(); v++) {
			int clusterId = find(v, id);
			if (!clusterMap.containsKey(clusterId)) {
				clusterMap.put(clusterId, new ArrayList<>());
			}
			clusterMap.get(clusterId).add(v);
		}
		for (List<Integer> cluster : clusterMap.values()) {
			Collections.sort(cluster); // Sort the elements within each cluster
			clusters.add(cluster);
		}
		// Sort the clusters based on the first element of each cluster.
		clusters.sort(Comparator.comparingInt(list -> list.get(0)));
	}

	// Helper method for Prim's algorithm.
	private void visit(EdgeWeightedGraph G, int v, boolean[] marked, Queue<Edge> mst, Queue<Edge> pq) {
		marked[v] = true;
		for (Edge e : G.adj(v)) {
			if (!marked[e.other(v)]) {
				pq.add(e);
			}
		}
	}

	// Helper method for Union-Find.
	private int find(int v, int[] id) {
		while (v != id[v]) {
			id[v] = id[id[v]]; // Path compression.
			v = id[v];
		}
		return v;

	}

	// Helper method for Union-Find.
	private void union(int p, int q, int[] id) {
		int rootP = find(p, id);
		int rootQ = find(q, id);
		if (rootP != rootQ) {
			id[rootP] = rootQ;
		}
	}

	/**
	 * This method finds clusters based on a MST and a threshold for the coefficient
	 * of variation.
	 *
	 * It is based in the idea that removing edges from a MST will create a
	 * partition into several connected components, which are the clusters.
	 * The edges are removed based on the threshold given. For further explanation
	 * see the exercise sheet.
	 *
	 * @param threshold for the coefficient of variation
	 */
	public void findClusters(double threshold) {
		// 1. Create a minimum spanning tree (MST) from the graph G using Prim's
		// algorithm.
		boolean[] marked = new boolean[G.V()]; // marked[v] = true if v on tree
		Queue<Edge> mst = new PriorityQueue<>(); // edges in the MST
		Queue<Edge> pq = new PriorityQueue<>(); // edges with one endpoint in tree

		visit(G, 0, marked, mst, pq);
		while (!pq.isEmpty()) {
			Edge e = pq.poll(); // Get lowest-weight edge from pq.
			int v = e.either(), w = e.other(v);
			if (marked[v] && marked[w])
				continue; // Skip if ineligible.
			mst.add(e); // Add edge to tree.
			if (!marked[v])
				visit(G, v, marked, mst, pq);
			if (!marked[w])
				visit(G, w, marked, mst, pq);
		}

		// 2. Sort the edges of the MST by weight.
		List<Edge> edges = new ArrayList<>(mst);
		edges.sort(Comparator.comparingDouble(Edge::weight));

		// 3. Start with the smallest edge and keep adding the next smallest edge to a
		// temporary list
		// until the coefficient of variation of the list exceeds the threshold.
		List<Edge> temp = new ArrayList<>();
		for (Edge e : edges) {
			temp.add(e);
			if (coefficientOfVariation(temp) > threshold) {
				temp.remove(e);
				break;
			}
		}

		// 4. The edges remaining in the temporary list form the MST for the clusters.
		// Use a Union-Find structure to find these clusters.
		int[] id = new int[G.V()];
		for (int i = 0; i < id.length; i++) {
			id[i] = i;
		}
		for (Edge e : temp) {
			int v = e.either(), w = e.other(v);
			union(v, w, id); // Use the union method here.
		}

		// 5. Store these clusters in the 'clusters' field.
		clusters = new ArrayList<>();
		Map<Integer, List<Integer>> clusterMap = new HashMap<>();
		for (int v = 0; v < G.V(); v++) {
			int clusterId = find(v, id);
			if (!clusterMap.containsKey(clusterId)) {
				clusterMap.put(clusterId, new ArrayList<>());
			}
			clusterMap.get(clusterId).add(v);
		}
		for (List<Integer> cluster : clusterMap.values()) {
			Collections.sort(cluster); // Sort the elements within each cluster
			clusters.add(cluster);
		}
		// Sort the clusters based on the first element of each cluster.
		clusters.sort(Comparator.comparingInt(list -> list.get(0)));
	}

	/**
	 * Evaluates the clustering based on a fixed number of clusters.
	 * 
	 * @return array of the number of the correctly classified data points per
	 *         cluster
	 */
	public int[] validation() {
		// 1. For each cluster, count the number of data points that have the same label
		// as the majority of the cluster.
		int[] counts = new int[clusters.size()];
		for (int i = 0; i < clusters.size(); i++) {
			List<Integer> cluster = clusters.get(i);
			Map<String, Integer> labelCounts = new HashMap<>();
			if (labels != null) { // Only count labels if they exist
				for (int v : cluster) {
					String label = labels.get(v); // Use the get method to access elements of the labels list.
					labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
				}
				String majorityLabel = Collections.max(labelCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
				int count = 0;
				for (int v : cluster) {
					if (labels.get(v).equals(majorityLabel)) { // Use the get method here as well.
						count++;
					}
				}
				counts[i] = count;
			}
		}
		return counts;
	}

	/**
	 * Calculates the coefficient of variation.
	 * For the formula see exercise sheet.
	 * 
	 * @param part list of edges
	 * @return coefficient of variation
	 */
	public double coefficientOfVariation(List<Edge> part) {
		// If 'part' is empty, return 0.0.
		if (part.isEmpty()) {
			return 0.0;
		}

		// 1. Calculate the mean and standard deviation of the weights of the edges in
		// 'part'.
		double sum = 0.0;
		for (Edge e : part) {
			sum += e.weight();
		}
		double mean = sum / part.size();

		double sumOfSquares = 0.0;
		for (Edge e : part) {
			sumOfSquares += Math.pow(e.weight() - mean, 2);
		}
		double standardDeviation = Math.sqrt(sumOfSquares / part.size());

		// 2. Return the coefficient of variation (standard deviation divided by mean).
		return standardDeviation / mean;
	}

	/**
	 * Plots clusters in a two-dimensional space.
	 */
	public void plotClusters() {
		int canvas = 800;
		StdDraw.setCanvasSize(canvas, canvas);
		StdDraw.setXscale(0, 15);
		StdDraw.setYscale(0, 15);
		StdDraw.clear(new Color(0, 0, 0));
		Color[] colors = { new Color(255, 255, 255), new Color(128, 0, 0), new Color(128, 128, 128),
				new Color(0, 108, 173), new Color(45, 139, 48), new Color(226, 126, 38), new Color(132, 67, 172) };
		int color = 0;
		for (List<Integer> cluster : clusters) {
			if (color > colors.length - 1)
				color = 0;
			StdDraw.setPenColor(colors[color]);
			StdDraw.setPenRadius(0.02);
			for (int i : cluster) {
				StdDraw.point(G.getCoordinates()[i][0], G.getCoordinates()[i][1]);
			}
			color++;
		}
		StdDraw.show();
	}

	public static void main(String[] args) {
		// FOR TESTING
	}
}
