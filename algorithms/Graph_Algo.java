package algorithms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import dataStructure.DGraph;
import dataStructure.Edge_Data;
import dataStructure.graph;
import dataStructure.node_data;
import dataStructure.Node_Data;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms{
	private DGraph Graph;
	
	@Override
	public void init(graph g) {
		this.Graph = (DGraph) g;
	}
	@Override
	public graph copy() {
		return (graph) deepCopy(this);
	}
	@Override
	public void init(String file_name) {
		
	}
	@Override
	public void save(String file_name) {
		
	}
	@Override
	public boolean isConnected() {
		return check(Graph);
	}
	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		
		return null;
	}
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * DFS
	 * not done.
	 */
	public static void DFS(DGraph g, int v, HashMap<Integer,Boolean> visited) {
		if(visited.containsKey(v)) {
			visited.replace(v, true);
		}
		else {
			visited.put(v,true);
		}
		ArrayList<Node_Data> Neighbors = g.getNodes().get(v).getNeighbors();
		for(Node_Data u2 : Neighbors) {
			if(!visited.get(u2.getKey())) {
				DFS(g,u2.getKey(),visited);
			}
		}
	}
	public static boolean check(DGraph graph)
	{
		// stores vertex is visited or not
		HashMap<Integer,Boolean> visited = new HashMap<Integer,Boolean>();
		for (int i : graph.getNodes().keySet()) {
			visited.put(i, false);
		}// choose random starting point
		Set<Integer> NodeList = graph.getNodes().keySet();
		Object[] arr = NodeList.toArray();
		int v = (int) arr[0];
		
		// run a DFS starting at v
		DFS(graph, v, visited);

		// If DFS traversal doesn’t visit all vertices,
		// then graph is not strongly connected
		Set<Integer> KeySet;
		KeySet = visited.keySet();
		for (int b: KeySet)
			if (!visited.get(b))
				return false;
	
		for (int i : graph.getNodes().keySet()) {
			visited.put(i, false);
		}
		HashMap<Integer, HashMap<Integer, Edge_Data>> edges = new HashMap<Integer, HashMap<Integer, Edge_Data>>();
		for(int u : NodeList) {
			Set<Integer> EdgeList = graph.getEdge(u).keySet();
			for (int z : EdgeList) {
				HashMap<Integer, Edge_Data> value = new HashMap<Integer, Edge_Data>();
				value.put(u,(Edge_Data) graph.getEdge(z, u));
				edges.put(u, value);
			}
		}
		DGraph gr = new DGraph(graph.getNodes(),edges);
		// Again run a DFS starting at v
		DFS(gr, v, visited);

		// If DFS traversal doesn’t visit all vertices,
		// then graph is not strongly connected
		Set<Integer> KeySet2;
		KeySet2 = visited.keySet();
		for (int b: KeySet2)
			if (!visited.get(b))
				return false;

		// if graph "passes" both DFSs, it is strongly connected
		return true;
	}
	private static Object deepCopy(Object object) {
	   try {
	     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	     ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
	     outputStrm.writeObject(object);
	     ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
	     ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
	     return objInputStream.readObject();
	   }
	   catch (Exception e) {
	     e.printStackTrace();
	     return null;
	   }
 }
	
}
