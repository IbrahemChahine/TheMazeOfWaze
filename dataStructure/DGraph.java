package dataStructure;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DGraph implements graph, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5469746264553631873L;
	//TODO add value isConnected that gives old answer if MC hasn't changed
	public HashMap<Integer, Node> Nodes;
	public HashMap<Integer, HashMap<Integer,Edge>> Edges;
	private int EdgeCount;
	private int MC;
	public DGraph() {
		this.Nodes = new HashMap<Integer, Node>();
		this.Edges = new HashMap<Integer, HashMap<Integer,Edge>>();
		this.EdgeCount = 0;
		this.MC = 0;
	}
	public DGraph(HashMap<Integer, Node> nodes, HashMap<Integer, HashMap<Integer,Edge>> edges) {
		this.Nodes = nodes;
		this.Edges = edges;
		Set<Integer> NodeList = this.Nodes.keySet();
		for(int u : NodeList) {
			this.Nodes.get(u).clearNeighbors();
			Set<Integer> EdgeList = this.Edges.get(u).keySet();
			for(int v : EdgeList) {
				this.Nodes.get(v).addNeighbor(this.Nodes.get(u));
			}
				
		}
	}
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
		if(this.Nodes.containsKey(n.getKey())) {
			throw new RuntimeException("the given Key already belong to a node in this Graph");
		}
		this.Nodes.put(n.getKey(), (Node) n);
		HashMap<Integer,Edge> edge = new HashMap<Integer,Edge>();
		this.Edges.put(n.getKey(), edge);
		this.MC++;
	}
	@Override
	public void connect(int src, int dest, double w) {
		if(!Nodes.containsKey(src) || !Nodes.containsKey(dest)) {
			throw new RuntimeException("One or Two of the given Keys doesn't belong to any Node in this Graph.");
		}
		if(w<0) {
			throw new RuntimeException("The weight of an edge cannot be negative.");
		}
		if(Nodes.get(src) == Nodes.get(dest)) {
			throw new RuntimeException("The given keys belong to the same node.");
		}
		if(!Edges.containsKey(src)) {
			Edge value = new Edge(Nodes.get(src),Nodes.get(dest), w);
			this.Edges.put(src, new HashMap< Integer ,Edge>() );
			this.Edges.get(src).put(dest,value);
			this.Nodes.get(src).addNeighbor((Node) this.getNode(dest));
		}
		else if(Edges.containsKey(src)){
			if(Edges.get(src).containsKey(dest)) {
				throw new RuntimeException("This Edge already exists.");
			}
			Edge value = new Edge(Nodes.get(src),Nodes.get(dest), w);
			this.Edges.get(src).put(dest,value);
			this.Nodes.get(src).addNeighbor((Node) this.getNode(dest));
		}
		EdgeCount++;
		MC++;
	}
	@Override
	public Collection<node_data> getV() {
		HashMap<Integer, Node> shallowCopy = (HashMap<Integer, Node>) this.Nodes;
		return (Collection<node_data>) shallowCopy;
	}
	@Override
	public Collection<edge_data> getE(int node_id) {
		HashMap<Integer, Edge> shallowCopy = (HashMap<Integer ,Edge>) this.Edges.get(node_id);
		return (Collection<edge_data>) shallowCopy;
	}
	public HashMap<Integer, Node> getNodes() {
		HashMap<Integer, Node> Copy = (HashMap<Integer, Node>) this.Nodes;
		return Copy;
	}
	public HashMap<Integer, Edge> getEdge(int node_id) {
		HashMap<Integer, Edge> Copy = (HashMap<Integer ,Edge>) this.Edges.get(node_id);
		return Copy;
	}
	@Override
	public node_data removeNode(int key) { //TODO update neighbors
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
	public edge_data removeEdge(int src, int dest) { //TODO update neighbors
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
	public HashMap<Integer, HashMap<Integer, Edge>> getEdges() {
		HashMap<Integer, HashMap<Integer, Edge>> Copy = (HashMap<Integer, HashMap<Integer, Edge>>) this.Edges;
		return Copy;
	}
}
