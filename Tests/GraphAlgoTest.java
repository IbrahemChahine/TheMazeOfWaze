package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

class GraphAlgoTest {
	
	@Test
	public void isConnectedTest() {
		DGraph Graph = new DGraph();
		Node n1 = new Node(10,new Point3D(0,0,0),"");
		Node n2 = new Node(11,new Point3D(0,0,0),"");
		Graph.addNode(n1);
		Graph.addNode(n2);
		Graph.connect(n1.getKey(), n2.getKey(), 10.0);
		Graph.connect(n2.getKey(), n1.getKey(), 10.0);
		Graph_Algo Algo = new Graph_Algo();
		Algo.init(Graph);
		assertTrue(Algo.isConnected());
	}
	@Test
	public void isNotConnectedTest() {
		DGraph Graph = new DGraph();
		Node n1 = new Node(10,new Point3D(0,0,0),"");
		Node n2 = new Node(11,new Point3D(0,0,0),"");
		Graph.addNode(n1);
		Graph.addNode(n2);
		Graph.connect(n1.getKey(), n2.getKey(), 10.0);
		Graph_Algo Algo = new Graph_Algo();
		Algo.init(Graph);
		if(Algo.isConnected()) {
			fail("Shoudn't be connected");
		}
	}
	@Test
	public void shortestPathDistTest() {
		DGraph Graph = new DGraph();
		Node n1 = new Node(10,new Point3D(0,0,0),"");
		Node n2 = new Node(11,new Point3D(0,0,0),"");
		Node n3 = new Node(12,new Point3D(0,0,0),"");
		Graph.addNode(n1);
		Graph.addNode(n2);
		Graph.addNode(n3);
		Graph.connect(n1.getKey(), n3.getKey(), 1000.0);
		Graph.connect(n1.getKey(), n2.getKey(), 10.0);
		Graph.connect(n2.getKey(), n3.getKey(), 10.0);
		Graph_Algo Algo = new Graph_Algo();
		Algo.init(Graph);
		double dist = Algo.shortestPathDist(n1.getKey(),n3.getKey());
		assertEquals(dist,20);
	}
	@Test
	public void shortestPathTest() {
		DGraph Graph = new DGraph();
		Node n1 = new Node(10,new Point3D(0,0,0),"");
		Node n2 = new Node(11,new Point3D(0,0,0),"");
		Node n3 = new Node(12,new Point3D(0,0,0),"");
		Graph.addNode(n1);
		Graph.addNode(n2);
		Graph.addNode(n3);
		Graph.connect(n1.getKey(), n3.getKey(), 1000.0);
		Graph.connect(n1.getKey(), n2.getKey(), 10.0);
		Graph.connect(n2.getKey(), n3.getKey(), 10.0);
		Graph_Algo Algo = new Graph_Algo();
		Algo.init(Graph);
		ArrayList<node_data> path = (ArrayList<node_data>) Algo.shortestPath(n1.getKey(),n3.getKey());
		boolean PathBool = true;
		if(!(n1.getKey() == path.get(0).getKey())) {
			PathBool = false;
		}
		if(!(n2.getKey() == path.get(1).getKey())) {
			PathBool = false;
		}
		if(!(n3.getKey() == path.get(2).getKey())) {
			PathBool = false;
		}
		assertTrue(PathBool);
	}
//	@Test // TODO Fix the TSP.
//	public void TSPTest() {
//		DGraph Graph = new DGraph();
//		Node n1 = new Node(10,new Point3D(0,0,0),"");
//		Node n2 = new Node(11,new Point3D(0,0,0),"");
//		Node n3 = new Node(12,new Point3D(0,0,0),"");
//		Graph.addNode(n1);
//		Graph.addNode(n2);
//		Graph.addNode(n3);
//		Graph.connect(n1.getKey(), n3.getKey(), 1000.0);
//		Graph.connect(n1.getKey(), n2.getKey(), 10.0);
//		Graph.connect(n2.getKey(), n3.getKey(), 10.0);
//		Graph_Algo Algo = new Graph_Algo();
//		Algo.init(Graph);
//		ArrayList<Integer> targets = new ArrayList<Integer>();
//		
//		List<node_data> path = Algo.TSP(targets);
//	}
}
