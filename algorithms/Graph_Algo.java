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
import java.util.Set;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Copy;

import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.graph;
import dataStructure.node_data;
import dataStructure.Node;

/** 		// TODO complete the following comments.
 * This class represents a set of graph-theory algorithms.
 * In this class you will be able to run the following algorithms:
 * 		1. isConnected - Check if the graph is connected. 
 * 		   time complexity = O(|V|+|E|) such that V is the nodes of the graph and E is the edges.
 * 		2. ShortestPath - Returns the Shortest Path between two given nodes in the graph.
 * 		   time complexity = 
 * 		3. TSP - Given a list of node targets the method will Return a simple short path between the targets. 
 * 		   time complexity = 
 * @author Ibrahem Chahine, Ofir Peller.
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
	/*
	 * Inits from graph. 
	 */
	@Override
	public void init(graph g) {
		this.Graph = (DGraph) g;
	}
	public void init(Graph_Algo g) {
		this.Graph = (DGraph) g.copy();
	}
	/** 
	 * This method Computes a deep copy of this graph.
	 * @param copy The deep copy of this graph
	 * @return Deep copy of this graph
	 */
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
	/*
	 * This method saves the project to file.
	 * @param file_name The given filename of the project.
	 * @param file the File.
	 * @param tempToFile A Graph_Algo object.
	 */
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
			e.printStackTrace();
		} 
	}
	/*
	 * This method inits the project from graph.
	 * @param file_name The filename of the project.
	 * @param file the File of the project.
	 */
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
	/**
	 * Returns true if and only if (iff) there is a valid path from EVREY node to each
	 * other node. NOTE: assume directional graph - a valid path (a-->b) does NOT imply a valid path (b-->a).
	 * Go to the check(Graph) method to get further Explanation on how this task is done. 
	 * @return true if and only if (iff) there is a valid path from EVREY node to each other node
	 */
	@Override
	public boolean isConnected() {
		return check(Graph);
	}
	/*
	 * This method returns the Weight of the shortest path from src to dest.
	 * @param tmp the shortest path from src to dest.
	 * @param lastNodeInPath the destination node.
	 * return the Weight of the shortest path from src to dest.
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		try {
			List<node_data> temp = shortestPath(src, dest);
			Node lastNodeInPath = (Node) temp.get(temp.size()-1);
			return lastNodeInPath.getWeight();
		}
		catch(Exception e) {
			return Double.POSITIVE_INFINITY;
		}

	}

	//TODO Documentation
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
	//TODO Documentation
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		if(!this.isConnected()) {//as Instructed by Boaz - if the Graph isn't connected, return null.
			System.err.println("Graph isn't connected. Returning NULL for TSP");
			return null;
		}
		
		double minimum = Double.POSITIVE_INFINITY;
		ArrayList<Integer> minimumNodeKeyList = new ArrayList<Integer>();
		HashMap<Integer, HashMap<Integer,Double>> Distances = new HashMap<Integer, HashMap<Integer,Double>>();
		for (int i : targets) {
			Distances.put(i, new HashMap<Integer,Double>()); //create a new inner HashMap for each of the targets
			for (int j : targets) {
				if(i==j) {continue;}
				double currentDistance = shortestPathDist(i,j);
				Distances.get(i).put(j, currentDistance);
			}
		}//end calculate all ShortestPath of all targets to each other
		
		int targets_size = targets.size();
		for (int i = 0; i < targets_size; i++) {//who is the first node in the path
			double currentPathDistance = 0;
			int currentPrev = targets.get(i);
			ArrayList<Integer> CURRENT_targets = new ArrayList<Integer>();
			ArrayList<Integer> CURRENT_Path = new ArrayList<Integer>();
			CURRENT_Path.add(currentPrev); //add the beginning node to the current path list
			for (int j = 0; j < targets_size; j++) { //add all the targets that aren't the 1st node to the current targets list
				if(i!=j) {CURRENT_targets.add(targets.get(j));}				
			}
			
			while(CURRENT_targets.size()!=0) {//now choose the current minimum shortestPath from the current node to any of those left, until non are left
				double currentMinimum = Double.POSITIVE_INFINITY;
				int nextNodeKey = -1;
				int nextNodeIndex = -1;
				for (int j = 0; j < CURRENT_targets.size(); j++) {
					double currentCandidate = Distances.get(currentPrev).get(CURRENT_targets.get(j));
					if (currentCandidate<=currentMinimum) {
						currentMinimum = currentCandidate;
						nextNodeKey = CURRENT_targets.get(j);
						nextNodeIndex = j;
					}
				}
				if(CURRENT_targets.size() == 1) {
					currentMinimum = Distances.get(currentPrev).get(CURRENT_targets.get(0));
					nextNodeKey = CURRENT_targets.get(0);
					nextNodeIndex = 0;
				}
				CURRENT_targets.remove(nextNodeIndex); //remove the chosen next node
				currentPrev = nextNodeKey; // update who is the next node to travel FROM (the chosen next node)
				currentPathDistance = currentPathDistance + currentMinimum; //update distance until now
				CURRENT_Path.add(nextNodeKey);
			}
			
			if (currentPathDistance<minimum) { //if the current path is the shortest - update the minimum path distance and it's the list showing it's path
				minimum = currentPathDistance;
				minimumNodeKeyList.clear();
				for (int p: CURRENT_Path) {
					minimumNodeKeyList.add(p);
				}
			}//end if (currentPathDistance<minimum) 
		}//end who is the first node in the path
		
		List <node_data> answer = new ArrayList<node_data>();
		for (int currentNodeKey : minimumNodeKeyList) {
			answer.add(this.Graph.getNode(currentNodeKey));
		}
		
		System.out.println(minimum);
		System.out.println(answer.toString());
		return answer;
		
	}//end TSP
	//TODO Documentation 
	public ArrayList<ArrayList<Integer>> permuteFatherMethod(int[] arr) {
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		permuteHelper(list, new ArrayList<>(), arr);
		return list;
	}
	//TODO Documentation
	private void permuteHelper(ArrayList<ArrayList<Integer>> list, ArrayList<Integer> resultList, int [] arr){
		// Base case
		if(resultList.size() == arr.length){
			list.add(new ArrayList<>(resultList));
		} 
		else{
			for(int i = 0; i < arr.length; i++){ 

				if(resultList.contains(arr[i])) 
				{
					// If element already exists in the list then skip
					continue; 
				}
				// Choose element
				resultList.add(arr[i]);
				// Explore
				permuteHelper(list, resultList, arr);
				// Unchoose element
				resultList.remove(resultList.size() - 1);
			}
		}
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
	/*
	 * This method checks if a given graph is connected or not.
	 * Explanation :
			 * The idea is, if every node can be reached from a vertex v, and every node can reach v, 
			 * then the graph is strongly connected.
			 * then, we check if all vertices are reachable from v. 
			 * then, we check if all vertices can reach v (In reversed graph, if all vertices are reachable from v, 
			 * then all vertices can reach v in original graph).
	 * @param graph The given graph.
	 * @param visited A Hashmap such that the keys belong to nodes in the graph and the values are boolean values stating if 
	 * 		  the node that belongs to the key are been visited.
	 * @param NodeList A list of nodes in the graph
	 * @return true if the graph is connected, else false.
	 */
	public boolean check(DGraph graph)
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
		for (int b: visited.keySet())
			if (!visited.get(b))
				return false;
		for (int i : graph.getNodes().keySet()) {
			visited.put(i, false);
		}
		DGraph gr = (DGraph) copy();
		for(int u : this.Graph.Edges.keySet()) {
			for(int k : this.Graph.Edges.get(u).keySet()) {
				if(!(this.Graph.Edges.containsKey(k) && this.Graph.Edges.get(k).containsKey(u))) {
					gr.connect(k,u,0);
					gr.removeEdge(u,k);
				}
			}
		}
		// Again run a DFS starting at v
		DFS(gr, v, visited);
		// If DFS traversal doesn’t visit all vertices,
		// then graph is not strongly connected
		for (int b: visited.keySet())
			if (!visited.get(b))
				return false;
		// if graph "passes" both DFSs, it is strongly connected
		return true;
	}
//	public boolean check(DGraph graph) {
//		for(int v : graph.getNodes().keySet()) {
//			HashMap<Integer,Boolean> visited = new HashMap<Integer,Boolean>();
//			for (int i : graph.getNodes().keySet()) {
//				visited.put(i, false);
//			}
//			DFS(graph, v, visited);
//			for (int b: visited.keySet())
//				if (!visited.get(b))
//					return false;
//		}
//		return true;
//	}
//		private static Object deepCopy(Object object) {
//		   try {
//		     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		     ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
//		     outputStrm.writeObject(object);
//		     ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//		     ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
//		     return objInputStream.readObject();
//		   }
//		   catch (Exception e) {
//		     e.printStackTrace();
//		     return null;
//		   }
//	 }


//	@Override
//	public List<node_data> TSP(List<Integer> targets) {
//		double minimum = Double.POSITIVE_INFINITY;
//		ArrayList<Integer> minimumNodeKeyList = new ArrayList<Integer>();
//		HashMap<Integer, HashMap<Integer,Double>> Distances = new HashMap<Integer, HashMap<Integer,Double>>();
//		for (int i : targets) {
//			Distances.put(i, new HashMap<Integer,Double>()); //create a new inner HashMap for each of the targets
//			for (int j : targets) {
//				if(i==j) {continue;}
//				double currentDistance = shortestPathDist(i,j);
//				Distances.get(i).put(j, currentDistance);
//			}
//		}//end calculate all ShortestPath of all targets to each other
//
//		for (int i : targets) {
//			int[] allTargetssBesideBeginningNode = new int[targets.size()-1];
//			int k = 0;
//			for (int j: targets) {
//				if(i!=j) {//if this node isn't the beginning node
//					allTargetssBesideBeginningNode[k]=j;
//					k++;
//				}
//			}
//			ArrayList<ArrayList<Integer>> currentPermutations = new ArrayList<ArrayList<Integer>>();
//			currentPermutations = permuteFatherMethod(allTargetssBesideBeginningNode);
//			for (ArrayList<Integer> specificPermutation : currentPermutations) {
//				double currentMinimum = Distances.get(i).get(specificPermutation.get(0)); //add the 1st journey cost
//				for (int j = 1; j < specificPermutation.size(); j++) {
//					double addToCurrentMinimum = Distances.get(specificPermutation.get(j-1)).get(specificPermutation.get(j)); //add the next journey
//					currentMinimum = currentMinimum + addToCurrentMinimum; //add the next journey cost to the total cost
//				}
//				if (currentMinimum<minimum) { //test if the now found trip is the cheapest, up till now.
//					minimum = currentMinimum;
//					specificPermutation.add(0, i); //add the key of the Node we begin at, to the list.
//					minimumNodeKeyList = specificPermutation;
//				}
//			}//end iterate over permutations of current beginning node
//		}//end iterate, changing beginning node.
//		
//		
//		List <node_data> answer = new ArrayList<node_data>();
//		for (int i = 0; i < minimumNodeKeyList.size(); i++) {
//			answer.add(this.Graph.getNode(minimumNodeKeyList.get(i)));
//		}
//		System.out.println(minimum);
//		System.out.println(answer.toString());
//		return answer;
//	}//end TSP
	
}