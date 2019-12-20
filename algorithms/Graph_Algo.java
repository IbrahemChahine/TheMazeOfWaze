package algorithms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node_data;
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(String file_name) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		return null;
	}

/**
 * Makes a deep copy of any Java object that is passed.
 */
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
