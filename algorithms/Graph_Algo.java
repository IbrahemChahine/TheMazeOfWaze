package algorithms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.graph;
import dataStructure.node_data;
import dataStructure.Node;

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
	/*
	 * Shortest path.
	 * TODO Implement an if() to check if the destination is reachable by the source.
	 */
//	@Override
//	public List<node_data> shortestPath(int src, int dest) {
//		if(!Graph.getNodes().containsKey(src) || !Graph.getNodes().containsKey(dest)) {
//			throw new RuntimeException("One or Two of the given keys doesn't belong to any node in the graph. ");
//		}
//		HashMap<Integer, Double> distance = new HashMap<Integer, Double>();
//		LinkedList<node_data> predecessor = new LinkedList<node_data>();
//		for(int i = 0; i<100000000; i++) {
//			predecessor.add(null);
//		}
//		for(int u : this.Graph.getNodes().keySet()) {
//			distance.put(u,(double) 100000);
//			predecessor.set(u,null);
//		}
//		distance.replace(src,(double) 0);
//		for(int i = 0; i < Graph.getNodes().size(); i++) {
//			for(int v : Graph.getEdges().keySet()) {
//				for(int u : Graph.getEdge(v).keySet()) {
//					if(( distance.get(v) + Graph.getEdge(v).get(u).getWeight() )< distance.get(u)) {
//						distance.replace(u,distance.get(v)+Graph.getEdge(v).get(u).getWeight());
//						predecessor.set(u,Graph.getNodes().get(v));
//					}
//				}
//			}
//		}
//		return predecessor;
//	}
	
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		Node source = (Node) this.Graph.getNode(src);
		source.setWeight(0);
		source.predecessor = null;
		HashMap<Integer, Node> graphNodes = this.Graph.getNodes();
		MyMinheap myHeap = new MyMinheap(graphNodes.size());
		for (int i : graphNodes.keySet()) {
			if(graphNodes.get(i).getKey()!=src) { graphNodes.get(i).setWeight(Double.POSITIVE_INFINITY);}
			graphNodes.get(i).visited = false;
			myHeap.add(graphNodes.get(i));
		}
		
		
		
		while(!myHeap.isEmpty()) {
			Node currentNode = myHeap.heapExtractMin();
			int currentKey = currentNode.getKey();
			
			for(int i : this.Graph.getEdges().get(currentKey).keySet()) {
				Node currentNextNode = (Node) this.Graph.getNode(i);
				if(!currentNextNode.visited) {
//					if(currentKey==1) {
//						System.out.println("currentKey==1");
//					}
//					Edge tempEdge = this.Graph.getEdges().get(src).get(dest);
					
					double currentEdgeWeight = this.Graph.getEdges().get(currentKey).get(i).getWeight();
					double optionalNewDistance = currentNode.getWeight() + currentEdgeWeight;
					
					if(optionalNewDistance<currentNextNode.getWeight()) {
						currentNextNode.setWeight(optionalNewDistance);
						currentNextNode.predecessor = currentNode;
						
						for (int j = 0; j < myHeap.size; j++) { //update the minHeap
							if(myHeap.heapNodeArray[j]==currentNextNode) {
								myHeap.minHeapify((j-1)/2); //heapify the parent node
								j=myHeap.size;
							}
						}//end updating minHeap	
					}
				}
			}//end for loop
			currentNode.visited=true;
		}
		Node currentNode2 = (Node) this.Graph.getNode(dest);
		ArrayList<node_data> Answer = new ArrayList<node_data>();
		while(currentNode2!=null) {
			Answer.add(currentNode2);
			currentNode2 = currentNode2.predecessor;
		}
		Collections.reverse(Answer);
		
		
		return Answer;
	}
	
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		return null;
	}
	public static void DFS(DGraph g, int v, HashMap<Integer,Boolean> visited) {
		if(visited.containsKey(v)) {
			visited.replace(v, true);
		}
		else {
			visited.put(v,true);
		}
		ArrayList<Node> Neighbors = g.getNodes().get(v).getNeighbors();
		for(Node u2 : Neighbors) {
			if(!visited.get(u2.getKey())) {
				DFS(g,u2.getKey(),visited);
			}
		}
	}
	public static boolean check(DGraph graph) {
		for(int v : graph.getNodes().keySet()) {
			HashMap<Integer,Boolean> visited = new HashMap<Integer,Boolean>();
			for (int i : graph.getNodes().keySet()) {
				visited.put(i, false);
			}
			DFS(graph, v, visited);
			for (int b: visited.keySet())
				if (!visited.get(b))
					return false;
		}
		return true;
	}
//	public static boolean check(DGraph graph)
//	{
//		// stores vertex is visited or not
//		HashMap<Integer,Boolean> visited = new HashMap<Integer,Boolean>();
//		for (int i : graph.getNodes().keySet()) {
//			visited.put(i, false);
//		}// choose random starting point
//		Set<Integer> NodeList = graph.getNodes().keySet();
//		Object[] arr = NodeList.toArray();
//		int v = (int) arr[0];
//		
//		// run a DFS starting at v
//		DFS(graph, v, visited);
//
//		// If DFS traversal doesn’t visit all vertices,
//		// then graph is not strongly connected
//		Set<Integer> KeySet;
//		KeySet = visited.keySet();
//		for (int b: KeySet)
//			if (!visited.get(b))
//				return false;
//	
//		for (int i : graph.getNodes().keySet()) {
//			visited.put(i, false);
//		}
//		HashMap<Integer, HashMap<Integer, Edge_Data>> edges = new HashMap<Integer, HashMap<Integer, Edge_Data>>();
//		for(int u : NodeList) {
//			Set<Integer> EdgeList = graph.getEdge(u).keySet();
//			for (int z : EdgeList) {
//				HashMap<Integer, Edge_Data> value = new HashMap<Integer, Edge_Data>();
//				value.put(u,(Edge_Data) graph.getEdge(u, z));
//				edges.put(z,value);
//			}
//		}
//		DGraph gr = new DGraph(graph.getNodes(),edges);
//		// Again run a DFS starting at v
//		DFS(gr, v, visited);
//
//		// If DFS traversal doesn’t visit all vertices,
//		// then graph is not strongly connected
//		Set<Integer> KeySet2;
//		KeySet2 = visited.keySet();
//		for (int b: KeySet2)
//			if (!visited.get(b))
//				return false;
//
//		// if graph "passes" both DFSs, it is strongly connected
//		return true;
//	}
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
