package dataStructure;

import java.util.Collection;
import java.util.HashMap;


public class DGraph implements graph{
	
	private HashMap<Integer, Node_Data> Nodes;
	private HashMap<Integer, HashMap<Integer,Edge_Data>> Edges;
	private int EdgeCount;
	private int MC;
	@Override
	public node_data getNode(int key) {
		if(!Nodes.containsKey(key)) {
			throw new RuntimeException("The given key doesn't belong to any Nodes in this Graph.");
		}
		return Nodes.get(key);
	}
	@Override
	public edge_data getEdge(int src, int dest) {
		if(!Nodes.containsKey(src) || !Nodes.containsKey(dest)) {
			throw new RuntimeException("One or Two of the given Keys doesn't belong to any Node in this Graph");
		}
		if(!Edges.get(src).containsKey(dest)) {
			throw new RuntimeException("the given Keys doesn't belong to any Edge in this Graph.");
		}
		return Edges.get(src).get(dest);
	}
	@Override
	public void addNode(node_data n) {
		Nodes.put(n.getKey(), (Node_Data) n);
		MC++;
	}
	@Override
	public void connect(int src, int dest, double w) {
		if(!Nodes.containsKey(src) || !Nodes.containsKey(dest)) {
			throw new RuntimeException("One or Two of the given Keys doesn't belong to any Node in this Graph.");
		}
		if(w<0) {
			throw new RuntimeException("The weight of an edge cannot be negative");
		}
		if(!Edges.containsKey(src)) {
			Edge_Data value = new Edge_Data(Nodes.get(src),Nodes.get(dest), w);
			this.Edges.put(src, new HashMap< Integer ,Edge_Data>() );
			this.Edges.get(src).put(dest,value);
		}
		else if(Edges.containsKey(src)){
			Edge_Data value = new Edge_Data(Nodes.get(src),Nodes.get(dest), w);
			this.Edges.get(src).put(dest,value);
		}
		EdgeCount++;
		MC++;
	}
	@Override
	public Collection<node_data> getV() {
		HashMap<Integer, Node_Data> shallowCopy = (HashMap<Integer, Node_Data>) this.Nodes.clone();
		return (Collection<node_data>) shallowCopy;
	}
	/*
	 * not done.
	 */
	@Override
	public Collection<edge_data> getE(int node_id) {
		HashMap<Integer, Edge_Data> shallowCopy = (HashMap<Integer ,Edge_Data>) this.Edges.get(node_id).clone();
		return (Collection<edge_data>) shallowCopy;
	}
	@Override
	public node_data removeNode(int key) {
		if(!Nodes.containsKey(key)) {
			throw new RuntimeException("The given key doesn't belong to any Node in this Graph.");
		}
		for (int i = 0; i < Nodes.get(key).getNeighbors().size(); i++) {
			removeEdge(key, Nodes.get(key).getNeighbors().get(i).getKey());
		}
		node_data node = Nodes.get(key);
		Nodes.remove(key);
		MC++;
		return node;
	}
	@Override
	public edge_data removeEdge(int src, int dest) {
		if(!this.Edges.get(src).containsKey(dest)) {
			throw new RuntimeException("One or Two of the given Keys doesn't belong to any Edge in this Graph.");
		}
		edge_data Edge = this.Edges.get(src).get(dest);
		this.Edges.get(src).remove(dest);
		EdgeCount--;
		MC++;
		return Edge;
	}
	@Override
	public int nodeSize() {
		return this.Nodes.size();
	}
	@Override
	public int edgeSize() {
		return this.EdgeCount;
	}
	@Override
	public int getMC() {
		return this.MC;
	}
}
