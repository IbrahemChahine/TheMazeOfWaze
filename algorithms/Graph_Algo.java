package algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
public class Graph_Algo implements graph_algorithms, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5167313342016016336L;
	/**
	 * 
	 */
	public DGraph Graph;
	
	@Override
	public void init(graph g) {
		this.Graph = (DGraph) g;
	}
	
	public void init(Graph_Algo g) {
		this.Graph = (DGraph) g.copy();
	}
	
	@Override
	public graph copy() {
		DGraph copy = new DGraph();
		for (int i : this.Graph.getNodes().keySet()) {
			copy.addNode(new Node(i,this.Graph.getNodes().get(i).getWeight(),this.Graph.getNodes().get(i).getLocation(),this.Graph.getNodes().get(i).getInfo()));
		}
		for( int u : this.Graph.getNodes().keySet()) {
			for (int v : this.Graph.getEdge(u).keySet()) {
				copy.connect(u,v,this.Graph.getEdge(u).get(v).getWeight());
			}
		}
		return copy;
	}
	@Override
	public void save(String file_name) {
		try {
			FileOutputStream file = new FileOutputStream(file_name); 
	        ObjectOutputStream out;
			out = new ObjectOutputStream(file);
			DGraph graphCopy = (DGraph)this.copy();
			Graph_Algo tempToFile = new Graph_Algo();
			tempToFile.init(graphCopy);
	        out.writeObject(tempToFile); 
	        out.close(); 
	        file.close();
	        System.out.println("Object has been serialized"); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	@Override
	public void init(String file_name) {
        try
        {    
            FileInputStream file = new FileInputStream(file_name); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            init((Graph_Algo)in.readObject());
            in.close(); 
            file.close(); 
              
            System.out.println("Object has been deserialized"); 
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
	}
	@Override
	public boolean isConnected() {
		return check(Graph);
	}
	@Override
	public double shortestPathDist(int src, int dest) {
		List<node_data> temp = shortestPath(src, dest);
		Node lastNodeInPath = (Node) temp.get(temp.size());
		return lastNodeInPath.getWeight();
	}
	
	
	@Override
	public List<node_data> shortestPath(int src, int dest) {
//		first check if the two nodes are connected
		HashMap<Integer,Boolean> visited1 = new HashMap<Integer,Boolean>();
		for(int u : this.Graph.getNodes().keySet()) {
			visited1.put(u,false);
		}
		DFS(this.Graph, src, visited1);
		if(!(visited1.get(dest))) {
			throw new RuntimeException("There is no existing path between the source and the destination!");
		}
		
		Node source = (Node) this.Graph.getNode(src);
		source.setWeight(0);
		source.predecessor = null;
		HashMap<Integer, Node> graphNodes = this.Graph.getNodes();
		MyMinheap myHeap = new MyMinheap(graphNodes.size());
		for (int i : graphNodes.keySet()) {
			if(graphNodes.get(i).getKey()!=src) { graphNodes.get(i).setWeight(Double.POSITIVE_INFINITY);}
			graphNodes.get(i).visited = false;
			graphNodes.get(i).predecessor = null;
			myHeap.add(graphNodes.get(i));
		}
		
		
		
		while(!myHeap.isEmpty()) {
			Node currentNode = myHeap.heapExtractMin();
			int currentKey = currentNode.getKey();
			
			for(int i : this.Graph.getEdges().get(currentKey).keySet()) {
				Node currentNextNode = (Node) this.Graph.getNode(i);
				if(!currentNextNode.visited) {

					
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
		HashMap<Integer, HashMap<Integer,Double>> Distances = new HashMap<Integer, HashMap<Integer,Double>>();
		for (int i : targets) {
			Distances.put(i, new HashMap<Integer,Double>()); //create a new inner HashMap for each of the targets
			for (int j : targets) {
				if(i==j) {continue;}
				double currentDistance = shortestPathDist(i,j);
				Distances.get(i).put(j, currentDistance);
			}
		}
		
		
		return null;
	}
	public void DFS(DGraph g, int v, HashMap<Integer,Boolean> visited) {
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
	public boolean check(DGraph graph) {
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
//	private static Object deepCopy(Object object) {
//	   try {
//	     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//	     ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
//	     outputStrm.writeObject(object);
//	     ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//	     ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
//	     return objInputStream.readObject();
//	   }
//	   catch (Exception e) {
//	     e.printStackTrace();
//	     return null;
//	   }
// }
	
}
