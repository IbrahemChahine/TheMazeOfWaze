package algorithms;
import java.util.ArrayList;

import dataStructure.*;
import utils.Point3D;

public class test {
	
	public static void main(String[] args) {
		
		Node first = new Node(0, 0, new Point3D(0, 0), "first");
		Node second = new Node(1, 0, new Point3D(0, 0), "second");
		Node third = new Node(2, 0, new Point3D(0, 0), "third");
		Node fourth = new Node(3, 0, new Point3D(0, 0), "fourth");
		
		DGraph testGraph = new DGraph();
		testGraph.addNode(first);
		testGraph.addNode(second);
		testGraph.addNode(third);
		testGraph.addNode(fourth);
		
		testGraph.connect(0, 1, 1);
		testGraph.connect(0, 2, 2);
		testGraph.connect(1, 3, 100);
		testGraph.connect(2, 3, 2);
		
		Graph_Algo graphAlgoTest = new Graph_Algo();
		graphAlgoTest.init(testGraph);
		ArrayList<node_data> testAnswer = (ArrayList<node_data>) graphAlgoTest.shortestPath(0, 3);
		System.out.println(testAnswer.toString());
	}
	
	

	
}
