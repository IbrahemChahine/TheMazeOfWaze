 package gui;
import static gui.GraphComponent.NODE_RADIUS;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.Node;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.List;
public class GraphGUI{
    /** Holds the graph GUI component */
    private GraphComponent graphComponent; 
    private DGraph Graph;
    private Graph_Algo Algo = new Graph_Algo();
    /** The window */
    private static JFrame frame;

    /** The node last selected by the user. */
    private Node chosenNode;

    /** A node clicked by the user. */
    private Node clickedNode;

    /** A node double-clicked by the user (to be edited). */
    private Node editingNode;

    /** The edge last selected by the user. */
    private Edge chosenEdge;

    /** A edge double-clicked by the user (to be edited). */
    private Edge editingEdge;

    /** Set of all nodes currently selected. */
    private HashMap<Integer, Node> nodesSelected = new HashMap<Integer, Node>();

    /** Set of all edges currently selected. */
    private HashMap<Integer, HashMap<Integer,Edge>> edgesSelected = new HashMap<Integer, HashMap<Integer,Edge>>();
    /** Text box for editing node's data. */
    public JTextField enterNodeData;

    /** Text box for editing edge's data. */
    public JTextField enterEdgeData;

    public JTextField EnterSrc;

    /** Text box for editing edge's data. */
    public JTextField EnterDest;
    
    /** Button to remove selected node(s). */
    public JButton removeNodeButton;
    
    public JMenuBar File;

    public JButton SaveImage;
    private int ImageCount;

    /** Button to remove selected edge(s). */
    public JButton removeEdgeButton;

    double EdgeWeight = -1;
    /** Label that appears when user adds new edge. */
    JLabel instructions;
    /** Label that appears when user clicks on shortest path. */
    JLabel PathInstructions;
    /** Label that appears when user clicks on TSP.*/
    JLabel TspInstructions;
	JLabel connect = new JLabel();

    boolean PathBool;
    boolean TspBool = false;
    boolean LoadBool = false;
    /** Flag telling whether the add edge button has been pressed. */
    public boolean addEBClicked = false;

    /** One of a new edge's nodes. */
    public Node firstN;
    
    

    /** The second of a new edge's nodes.*/
    public Node secondN;
    private int Counter = 0;
    private static boolean windowsOn = false;
    /**
     *  Constructor that builds a completely empty graph.
     */
    public GraphGUI() {
		this.Graph = new DGraph();
		initializeGraph();
		this.ImageCount = 0;
		this.graphComponent = new GraphComponent(this.Graph);
    }
    public GraphGUI(DGraph g) {
		this.Graph = g;
		initializeGraph();
		this.ImageCount = 0;
		this.graphComponent = new GraphComponent(g);
    }
    /**
     *  Create and show the GUI.
     */
    public void createAndShowGUI() {
		// Create and set up the window.
    		windowsOn = true;
    		frame = new JFrame("The Maze Of Waze");
    		
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    		// Add components
    		createComponents(frame.getContentPane());
    	
    		// Display the window.
    		frame.pack();
    		frame.setVisible(true);
    	
    }

    /**
     *  Create the components and add them to the frame.
     *
     *  @param pane the frame to which to add them
     */
    public void createComponents(Container pane) {
		pane.add(this.graphComponent);
		MyMouseListener ml = new MyMouseListener();
		this.graphComponent.addMouseListener(ml);
		this.graphComponent.addMouseMotionListener(ml);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel editpanel = new JPanel();
		editpanel.setLayout(new BorderLayout());
		JPanel tsp = new JPanel();
		tsp.setLayout(new BorderLayout());
		JPanel editnodepanel = new JPanel();
		editnodepanel.setLayout(new FlowLayout());
		JPanel editedgepanel = new JPanel();
		editedgepanel.setLayout(new FlowLayout());
		JPanel isconnected = new JPanel();
		isconnected.setLayout(new FlowLayout());
		JPanel shortestpath = new JPanel();
		shortestpath.setLayout(new FlowLayout());
		JPanel saveimagepanel = new JPanel();
		saveimagepanel.setLayout(new FlowLayout());
		JPanel Load = new JPanel();
		Load.setLayout(new FlowLayout());
		JPanel travpanel = new JPanel();
		travpanel.setLayout(new FlowLayout());
		JPanel newpanel = new JPanel();
		newpanel.setLayout(new FlowLayout());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				windowsOn = false;
			}
		});
		/*
		 * load.
		 */
		JButton LoadFromFile = new JButton("Load");
		LoadFromFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					connect.setVisible(false);
					instructions.setVisible(false);
					JFileChooser LoadFromFile2 = new JFileChooser();
					LoadFromFile2.setDialogTitle("Choose a file to load");
					LoadFromFile2.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					int returnVal = LoadFromFile2.showOpenDialog(pane);
					File file = LoadFromFile2.getSelectedFile();
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						String filename = file.getName();
						Algo.init(filename);
						Graph = Algo.Graph;
						int count = 0;
						for(int u : Graph.getNodes().keySet()) {
							double x = Math.random()*250+100;
							double y = Math.random()*250+100;
							Graph.getNodes().get(u).setLocation(new Point3D(x,y,0));
							if(count < u) {
								count = u;
							}
							Counter = count + 1;
							
						}
						graphComponent = new GraphComponent(Graph);
						graphComponent.repaint();
						execute();
						LoadBool = true;
					}
				} catch (Exception e2) {System.out.println(e2);}
			}
		    });
		Load.add(LoadFromFile);
		/*
		 * save
		 */
		JButton SaveToImage = new JButton("Save Image");
		SaveToImage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				connect.setVisible(false);
				instructions.setVisible(false);
				graphComponent.saveImage("image"+ImageCount,"png");
				ImageCount++;
			}
		    });
		saveimagepanel.add(SaveToImage);
		/**
		 * isconnected.
		 */
		
		
		newpanel.add(connect);
		JButton IsConnected = new JButton("IsConnected");
		IsConnected.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				PaintWhite();
				instructions.setVisible(false);
				try {
					Graph_Algo a = new Graph_Algo();
					a.init(Graph);
					if(a.isConnected()) {
						connect.setText("The Graph is Connected");
					}
					if(!a.isConnected()) {
						connect.setText("The Graph is not Connected");
					}
				    PathInstructions.setVisible(false);
					TspInstructions.setVisible(false);
					connect.setVisible(true);
					System.out.println(a.isConnected());
				} catch (Exception e2) {}
	
			}
		    });
		connect.setVisible(false);
		isconnected.add(IsConnected);
	
		/*
		 *ShortestPath
		 */
		PathInstructions = new JLabel("Select the src node and dest node");
		newpanel.add(PathInstructions);
		PathInstructions.setVisible(false);
		JButton ShortestPath = new JButton("ShortestPath");
		ShortestPath.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					PaintWhite();
					PathBool = true;
			    	connect.setVisible(false);
				    instructions.setVisible(false);
				    TspInstructions.setVisible(false);
				    PathInstructions.setVisible(true);
				} catch (Exception e2) {
					
				}
			}
		    });
		shortestpath.add(ShortestPath);
		/*
		 * TSP 
		 */
		JButton TSP = new JButton("TSP");
		TspInstructions = new JLabel("TSP Mode");
		newpanel.add(TspInstructions);
		TspInstructions .setVisible(false);
		TSP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					connect.setVisible(false);
				    instructions.setVisible(false);
					TspInstructions.setVisible(true);
					PaintWhite();
					String Num = (String)JOptionPane.showInputDialog(
                            frame,
                            "Enter the number of keys that you want in the targets.",
                            null, JOptionPane.PLAIN_MESSAGE,
                            null, null, null);
					int TargetNum = Integer.valueOf(Num);
					if(TargetNum > Graph.nodeSize()) {
						JOptionPane.showMessageDialog(frame, "Error: the number of targets cannot be bigger than the node size.");
					}
					else {
						ArrayList<Integer> target = new ArrayList<Integer>();
						for(int i=0 ; i < TargetNum ; i++) {
							String keystring = (String)JOptionPane.showInputDialog(
		                            frame,
		                            "Enter key num"+i,
		                            null, JOptionPane.PLAIN_MESSAGE,
		                            null, null, null);
							int key = Integer.valueOf(keystring);
							if(!Graph.getNodes().containsKey(key)) {
								JOptionPane.showMessageDialog(frame, "Error: the given doesn't belong to the graph.");
								i--;
							}
							else {
								target.add(key);
//								System.out.println(target.get(i));
							}
						}
						
		    			Algo.init(Graph);
				    	if(Algo.isConnected() == true) {
				    		ArrayList<node_data> targets = (ArrayList<node_data>) Algo.TSP(target);
				    		for(int i = 0; i< targets.size(); i++) {
				    			System.out.println(targets.get(i));
				    		}
					    	for(int k = 0; k<targets.size(); k++) {
								if(k<targets.size()-1) {
									ArrayList<node_data> path = (ArrayList<node_data>)Algo.shortestPath(targets.get(k).getKey(),targets.get(k+1).getKey());
									for(int i = 0; i<path.size(); i++) {
										if(i<path.size()-1) {
											Graph.getEdge(path.get(i).getKey()).get(path.get(i+1).getKey()).setTag(1);
											if(Graph.getEdges().get(path.get(i+1).getKey()).containsKey(path.get(i).getKey())) {
												Graph.getEdge(path.get(i+1).getKey()).get(path.get(i).getKey()).setTag(1);	
											}
											graphComponent.repaint();
										}
									}
								}
							}
					}
					
			    	}
			    	TspBool = false;
					graphComponent.repaint();
					TspInstructions.setVisible(false);
					nodesSelected.clear();	
					
				} catch (Exception e2) {System.out.println(e2);}
			}
		    });
		tsp.add(TSP);
		/*
		 * 
		 */
		JButton addNodeButton = new JButton("Add Node");
		addNodeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				TspInstructions.setVisible(false);
				connect.setVisible(false);
				 PathInstructions.setVisible(false);
				    instructions.setVisible(false);
				try {
					PaintWhite();
					double x = Math.random()*250+100;
					double y = Math.random()*250+100;
				    Graph.addNode(new Node(Counter,0,new Point3D(x,y,0),""));
				    fixCounter(Counter);
				    graphComponent.repaint();
				    PathInstructions.setVisible(false);
				} catch (Exception e2) {}
			}
		    });
		editnodepanel.add(addNodeButton);
		instructions = new JLabel("Set the weight then Select the src node and dest node.");
		newpanel.add(instructions);
		instructions.setVisible(false);
		JButton addEdgeButton = new JButton("Add Edge");
		addEdgeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				TspInstructions.setVisible(false);
				connect.setVisible(false);
				PaintWhite();
			    try {
			    	PathInstructions.setVisible(false);
			    	connect.setVisible(false);
				    
			    	TspInstructions.setVisible(false);
				    instructions.setVisible(true);
				    
				    String w = (String)JOptionPane.showInputDialog(
                            frame,
                            "Enter the Weight of the Edge",
                            null, JOptionPane.PLAIN_MESSAGE,
                            null, null, null);
				    if(!(w == null)) {
				    	addEBClicked = true;
		                EdgeWeight = Double.valueOf(w);
				    }
				    else {
				    	System.out.println("you didn't set a weight");
				    }
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
		    });
		editedgepanel.add(addEdgeButton);
		editpanel.add(isconnected, BorderLayout.EAST);
		editpanel.add(editnodepanel, BorderLayout.NORTH);
		editpanel.add(editedgepanel, BorderLayout.SOUTH);
		editpanel.add(saveimagepanel, BorderLayout.CENTER);
		panel.add(shortestpath, BorderLayout.EAST);
		panel.add(tsp,BorderLayout.WEST);
		panel.add(Load, BorderLayout.CENTER);
		panel.add(editpanel, BorderLayout.NORTH);
		panel.add(travpanel, BorderLayout.SOUTH);
		pane.add(newpanel, BorderLayout.NORTH);
		pane.add(panel, BorderLayout.SOUTH);
    }

    /**
     *  Disables all text fields and sets default text within them to empty.
     */
    public void disableAll() {
		enterNodeData.setText("");
		enterNodeData.setEnabled(false);
		enterEdgeData.setText("");
		enterEdgeData.setEnabled(false);
    }

    /**
     *  Execute the application.
     */
    public void execute() {
	// Schedule a job for the event-dispatching thread:
	// creating and showing this application's GUI.
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			try {
    				if(windowsOn) {
    					frame.setVisible(false);
    					createAndShowGUI();
    				}
    				else {
    					windowsOn = true;
    					createAndShowGUI();
    				}
				} catch (Exception e) {System.out.println(e);}
    			
    		}
	    });
    }

    /**
     *  The obligatory main method for the application.  With no
     *  arguments the application will read the graph from the standard
     *  input; with one argument (a file name) it will read the graph
     *  from the named file.
     *
     *  @param args  the command-line arguments
     */
    public static void main(String[] args) throws IOException {
		GraphGUI graphicGraph;
		graphicGraph = new GraphGUI();
		graphicGraph.execute();
    }

    /**
     *  A mouse listener to handle click and drag actions on nodes.
     */
    private class MyMouseListener extends MouseAdapter {
	/** How far off the center of the node was the click? */
	private int deltaX;
	private int deltaY;

	

	/**
	 *  Finds the shortest distance between mouse click point and line.
	 *  Used to tell whether user clicked within 5px of an edge.
	 *
	 *  @return the distance between the point and the line
	 */
	public double findDist(Edge edge,
			       double edgeX1, double edgeY1, double edgeX2, double edgeY2,
			       double mouseX, double mouseY) {
	    double edgeSlope = (edgeY2 - edgeY1) / (edgeX2 - edgeX1);
	    double dist;

	    if (edgeSlope == 0.0) {
		dist = -1.0;
	    } else {
		double perpSlope = (-1) * (1 / edgeSlope);
		/* solving intersection:
		   edge equation: y = edgeSlope(x) - edgeSlope(edgeX1) + edgeY1
		   perp equation: y = perpSlope(x) - perpSlope(mouseX) + mouseY
		   x = (edgeSlope*edgeX1 - edgeY1 - perpSlope*mouseX + mouseY) / (edgeSlope - perpSlope)
		*/
		double commonX = (edgeSlope*edgeX1 - edgeY1 - perpSlope*mouseX + mouseY) / (edgeSlope - perpSlope);
		double commonY = edgeSlope*(commonX - edgeX1) + edgeY1;
		double dx = Math.abs(mouseX - commonX);
		double dy = Math.abs(mouseY - commonY);
		dist = Math.sqrt(dx*dx + dy*dy);
	    }
	    return dist;
	}

	/**
	 *  Tells whether x is less than y and y is less than z.
	 *
	 *  @return true if x <= y <= z
	 */
	public boolean ordered(double x, double y, double z) {
	    return (x <= y) && (y <= z);
	}

	public void mouseDragged(MouseEvent e) {
	    if (chosenNode != null) {
			chosenNode.setLocation(new Point3D(e.getX()+deltaX,e.getY()+deltaY,0)); 
	    }
	    graphComponent.repaint();
	}
	public void mouseReleased(MouseEvent e) {
	    chosenNode = null;
	    chosenEdge = null;
	    clickedNode = null;
	    editingNode = null;
	    editingEdge = null;
	}

	public void mousePressed(MouseEvent e) {
	    double mouseX = e.getX();
	    double mouseY = e.getY();

	    //disableAll();

	    // Recognize when a node is pressed
	    for (int node : Graph.getNodes().keySet()) {
			double nodeX = Graph.getNode(node).getLocation().x();
			double nodeY = Graph.getNode(node).getLocation().y();
			if (Math.sqrt((nodeX-mouseX)*(nodeX-mouseX)+(nodeY-mouseY)*(nodeY-mouseY)) <= GraphComponent.NODE_RADIUS) {
			    try {
			    	if(Graph.getNodes().containsKey(node)) {
				    	chosenNode = (Node) Graph.getNodes().get(node);
				    }
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
	    }
	    if (chosenNode != null) {
			deltaX = chosenNode.getLocation().ix() - e.getX();
			deltaY = chosenNode.getLocation().iy() - e.getY();
	    }

	    // Handle user clicking two nodes for the tail and head of a new edge to be added
	    if ((addEBClicked) && (chosenNode != null) && (firstN == null)) {
			firstN = chosenNode;
			instructions.setText("Set the weight then Select the src node and dest node.");
	    } else if ((addEBClicked) && (chosenNode != null) && (firstN != null) && (secondN == null)) {
			secondN = chosenNode;
			int edata = Graph.getEdges().size() + 1;
			if (firstN != secondN) {
				try {
					if(EdgeWeight != -1) {
						Graph.connect(firstN.getKey(), secondN.getKey(),EdgeWeight);
					}
				    
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
			EdgeWeight = 0;
			addEBClicked = false;
		//firstN.getData().setBorderColor(myColor); 
			nodesSelected.remove(firstN.getKey());
			nodesSelected.remove(secondN.getKey());
			
			instructions.setVisible(false);
			firstN = null;
			secondN = null;
			graphComponent.repaint();
	    }
	  //shortest path.
	    if ((PathBool) && (chosenNode != null) && (firstN == null)) {
			firstN = chosenNode;
			
	    } else if ((PathBool) && (chosenNode != null) && (firstN != null) && (secondN == null)) {
			secondN = chosenNode;
			Graph_Algo algo = new Graph_Algo();
			if (firstN != secondN) {
				try {
					algo.init(Graph);
					ArrayList<node_data> path = (ArrayList<node_data>) algo.shortestPath(firstN.getKey(), secondN.getKey());
					for(int i = 0; i<path.size(); i++) {
						if(i<path.size()-1) {
							Graph.getEdge(path.get(i).getKey()).get(path.get(i+1).getKey()).setTag(1);
							if(Graph.getEdges().get(path.get(i+1).getKey()).containsKey(path.get(i).getKey())) {
								Graph.getEdge(path.get(i+1).getKey()).get(path.get(i).getKey()).setTag(1);	
							}
							graphComponent.repaint();
						}
					}
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
			PathBool = false;
			nodesSelected.remove(firstN.getKey());
			nodesSelected.remove(secondN.getKey());			
			PathInstructions.setVisible(false);
			firstN = null;
			secondN = null;
			graphComponent.repaint();
	    }
//	  //TSP.
//	    if(TspBool == true && nodesSelected.size() > 1) {
//	    	TSP();
//	    }
////	   
	    
	}
	public void mouseClicked(MouseEvent e) {
	    double mouseX = e.getX();
	    double mouseY = e.getY();

	    // Recognize when a node is clicked
	    for (int node : Graph.getNodes().keySet()) {
			double nodeX = Graph.getNodes().get(node).getLocation().x();
			double nodeY = Graph.getNodes().get(node).getLocation().y();
			if (Math.sqrt((nodeX-mouseX)*(nodeX-mouseX)
				      +(nodeY-mouseY)*(nodeY-mouseY))
			    <= graphComponent.NODE_RADIUS) {
			    clickedNode = Graph.getNodes().get(node);
			}
	    }
	    // If double-click, editing becomes possible
	    if ((clickedNode != null) && (e.getClickCount() == 2)) {
	    	try {
	    		editingNode = clickedNode;
				int curr_enode_data = editingNode.getKey();
				//enterNodeData.setEnabled(false);
				enterNodeData.setText(Integer.toString(curr_enode_data));
			} catch (Exception e2) {
				// TODO: handle exception
			}
			if (nodesSelected.containsKey(editingNode.getKey())) {
			    //editingNode.getData().setBorderColor(myColor);
			    nodesSelected.remove(editingNode.getKey());
			} else {
			    //editingNode.getData().setBorderColor(selectColor);
			    nodesSelected.put(editingNode.getKey(),editingNode);
			}
			graphComponent.repaint();
	    } else if (clickedNode != null) {
//		removeNodeButton.setEnabled(true);
			if (nodesSelected.containsKey(clickedNode.getKey())) {
			    //clickedNode.getData().setBorderColor(myColor);
			    nodesSelected.remove(clickedNode.getKey());
			} else {
			    //clickedNode.getData().setBorderColor(selectColor);
			    nodesSelected.put(clickedNode.getKey(),clickedNode);
			}
			graphComponent.repaint();
	    }

	    // Recognize when an edge is clicked (less than 5px away from line)
	    try {
	    	for (int u : Graph.getEdges().keySet()) {
		    	for(int v : Graph.getEdge(u).keySet()){
		    		int rad = GraphComponent.NODE_RADIUS;
					double theta = Math.atan2(Graph.getNode(u).getLocation().iy() -Graph.getNode(v).getLocation().iy(),
							Graph.getNode(u).getLocation().ix() -Graph.getNode(v).getLocation().ix());
					
					
					double edgeX1 = Graph.getNode(v).getLocation().ix() + Math.cos(theta)*rad;
					double edgeY1 = Graph.getNode(v).getLocation().iy() + Math.sin(theta)*rad;
					double edgeX2 = Graph.getNode(u).getLocation().ix() - Math.cos(theta)*rad;
					double edgeY2 = Graph.getNode(u).getLocation().iy() - Math.sin(theta)*rad;
					double dist = findDist(Graph.getEdge(u).get(v), edgeX1, edgeY1, edgeX2, edgeY2, mouseX, mouseY);
					if (dist == -1.0) {
					    dist = Math.abs(mouseY - edgeY1);
					    if ((dist < 5) && (ordered(edgeX1, mouseX, edgeX2) || ordered(edgeX2, mouseX, edgeX1))) {
					    	chosenEdge = Graph.getEdge(u).get(v);
					    }
					} else if (dist < 5) {
					    if (ordered(edgeX1, mouseX, edgeX2) || ordered(edgeX2, mouseX, edgeX1)) { 
							if (ordered(edgeY1, mouseY, edgeY2) || ordered(edgeY2, mouseY, edgeY1)) { 
							    chosenEdge = Graph.getEdge(u).get(v);
							}
					    }
					}
		    	}
		    }
		} catch (Exception e2) {}
	    // If double-click, editing becomes possible
	    if ((chosenEdge != null) && (e.getClickCount() == 2)) {
			graphComponent.repaint();
	    } else if (chosenEdge != null) {
		if (edgesSelected.containsKey(chosenEdge.getSrc())) {
		} else {
			try {
			} catch (Exception e2) {}
		}
		graphComponent.repaint();
	    }
	}

	public void mouseMoved(MouseEvent e) {
		try {
			double mouseX = e.getX();
		    double mouseY = e.getY();
		    	    	
		    
		    if (nodesSelected.size() == 0) {
//		    	removeNodeButton.setEnabled(false);
		    }
		    if (edgesSelected.size() == 0) {
		    	//removeEdgeButton.setEnabled(false);
		    }
		    
		    // Recognize when a node is moused-over
		    for (int node : Graph.getNodes().keySet()) {
				double nodeX = Graph.getNode(node).getLocation().x();
				double nodeY = Graph.getNode(node).getLocation().y();
				if (Math.sqrt((nodeX-mouseX)*(nodeX-mouseX)
					      +(nodeY-mouseY)*(nodeY-mouseY))
				    <= NODE_RADIUS) {
					 Graph.getNodes().get(node).setTag(1);
				} else if (!nodesSelected.containsKey(node)) {
					Graph.getNodes().get(node).setTag(0);
				}
				graphComponent.repaint();
		    }
		} catch (Exception e2) {}
	} 
    }
   
    public void fixCounter(int count) {
    	for(int u : Graph.getNodes().keySet()) {
			if(count < u) {
				count = u;
			}
		}
		Counter = count + 1;
    }
    public void PaintWhite() {
		for(int u : Graph.getEdges().keySet()) {
	    	for (int v : Graph.getEdges().get(u).keySet()) {
				Graph.getEdge(u).get(v).setTag(0);	
			}
	    }
		graphComponent.repaint();
	}
    private void initializeGraph() {}
}