package Tests;

import static org.junit.jupiter.api.Assertions.*;
import dataStructure.*;
import utils.Point3D;

import org.junit.jupiter.api.Test;

class DGraphTest {

	@Test 
	public void getNodeTest() {
		DGraph Graph = new DGraph();
		Node N = new Node(10,new Point3D(0,0,0),"");
		Graph.addNode(N);		
		assertEquals(Graph.getNode(10), N);
	}
	@Test
	public void addNodeTest() {
		DGraph Graph = new DGraph();
		Node N = new Node(10,new Point3D(0,0,0),"");
		Graph.addNode(N);
		assertTrue(Graph.getNodes().containsKey(N.getKey()));
	}
	@Test
	public void connectTest() {
		DGraph Graph = new DGraph();
		Node N = new Node(10,new Point3D(0,0,0),"");
		Graph.addNode(N);
		Node N2 = new Node(11,new Point3D(0,0,0),"");
		Graph.addNode(N2);
		Graph.connect(N.getKey(),N2.getKey(),10);
		assertTrue(Graph.getEdges().get(N.getKey()).containsKey(N2.getKey()));
	}
	@Test 
	public void removeNodeTest() {
		DGraph Graph = new DGraph();
		Node N = new Node(10,new Point3D(0,0,0),"");
		Graph.addNode(N);		
		Graph.removeNode(N.getKey());
		assertTrue(!Graph.getNodes().containsKey(N.getKey()));
	}
	@Test 
	public void removeEdgeTest() {
		DGraph Graph = new DGraph();
		Node N = new Node(10,new Point3D(0,0,0),"");
		Graph.addNode(N);
		Node N2 = new Node(11,new Point3D(0,0,0),"");
		Graph.addNode(N2);
		Graph.connect(N.getKey(),N2.getKey(),10);
		Graph.removeEdge(N.getKey(),N2.getKey());
		assertTrue(!Graph.getEdges().get(N.getKey()).containsKey(N2.getKey()));
	}
}
