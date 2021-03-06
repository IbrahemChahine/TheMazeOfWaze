package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import dataStructure.DGraph;
import dataStructure.Node;
import utils.Point3D;

/**
 *  A component that draws a picture of a graph.
 */
public class GraphComponent extends JComponent {
	  private static final long serialVersionUID = -4822415017845138079L;
	
	 /** The radius of the circle to draw for a node. */
	  public static final int NODE_RADIUS = 16;
	
	  /** The diameter of the circle to draw for a node. */
	  public static final int NODE_DIAMETER = 2 * NODE_RADIUS;
	
	  /** The length of a stroke of an arrow head. */
	  public static final int ARROW_HEAD_LENGTH = 15;
	
	  /** The angle of the stroke of an arrow head with respect to the line. */
	  public static final double ARROW_ANGLE = 9.0*Math.PI/10.0;
	  /** 
	   *  The graph to draw.  Note that this graph is based on the Node and Edge classes,
	   *  so the data associated with a node includes its key ,position and color.
	   */
	  DGraph Graph;
	  /** 
	   *  Constructor.
	 * @param Graph 
	   *
	   *  @param graphWithPlacement - the graph to draw
	   */
	  public GraphComponent(DGraph Graph) {
	    this.Graph = Graph;
	  	fix();
	    setMinimumSize(new Dimension(500, 500));
	    setPreferredSize(new Dimension(750, 500));
	  }
	  
	  /**
	   *  This method Draws the graph.
	   */
	  public void paint(Graphics g) {
	        for (int u : this.Graph.getEdges().keySet()) {
		    	for(int v : this.Graph.getEdge(u).keySet()) {
					try {
						int x1 = this.Graph.getNode(u).getLocation().ix();
						int y1 = this.Graph.getNode(u).getLocation().iy();
						int x2 = this.Graph.getNode(v).getLocation().ix();
						int y2 = this.Graph.getNode(v).getLocation().iy();
						double theta = Math.atan2(y2 - y1, x2 - x1);  // angle of edge's arrow
						//
						// line
						double edgeX1 = x1 + Math.cos(theta)*NODE_RADIUS+1;
						double edgeY1 = y1 + Math.sin(theta)*NODE_RADIUS+1;
						double edgeX2 = x2 - Math.cos(theta)*NODE_RADIUS+1;
						double edgeY2 = y2 - Math.sin(theta)*NODE_RADIUS+1;
						// arrow
						int tag = this.Graph.getEdge(u).get(v).getTag();
						if(tag == 1) {
							g.setColor(Color.RED);
							g.drawLine((int)Math.round(edgeX1), (int)Math.round(edgeY1), (int)Math.round(edgeX2), (int)Math.round(edgeY2));
						}
						else {
							g.setColor(Color.green);
							g.drawLine((int)Math.round(edgeX1), (int)Math.round(edgeY1), (int)Math.round(edgeX2), (int)Math.round(edgeY2));
						}
						g.setColor(Color.red);
						String sss = ""+String.valueOf((double) this.Graph.getEdge(u).get(v).getWeight());
						Node p = (Node) this.Graph.getNode(u);
						Node p2 = (Node) this.Graph.getNode(v);
						g.drawString(sss, 1+(int)((p.getLocation().ix()*0.7)+(0.3*p2.getLocation().ix())), 
								(int)((p.getLocation().iy()*0.7)+(0.3*p2.getLocation().iy()))-2);
						// arrow head
						if(tag == 1) {g.setColor(Color.RED);}
						else {g.setColor(Color.GREEN);}
					    double arrowX1 = edgeX2 + Math.cos(theta-ARROW_ANGLE)*ARROW_HEAD_LENGTH;
					    double arrowY1 = edgeY2 + Math.sin(theta-ARROW_ANGLE)*ARROW_HEAD_LENGTH;
					    double arrowX2 = edgeX2 + Math.cos(theta+ARROW_ANGLE) * ARROW_HEAD_LENGTH;
					    double arrowY2 = edgeY2 + Math.sin(theta+ARROW_ANGLE) * ARROW_HEAD_LENGTH;
					    Polygon arrowHead = new Polygon(new int[]{(int)Math.round(edgeX2),
					                                              (int)Math.round(arrowX1),
					                                              (int)Math.round(arrowX2)},
					                                    new int[]{(int)Math.round(edgeY2),
					                                              (int)Math.round(arrowY1),
					                                              (int)Math.round(arrowY2)}, 3);
					    g.fillPolygon(arrowHead);
					} catch (Exception e) {}
										
				}
        	}
						        
		  for (int k : this.Graph.getNodes().keySet()) {
		    g.setColor(Color.RED);
		    g.fillOval(this.Graph.getNode(k).getLocation().ix() - NODE_RADIUS, this.Graph.getNode(k).getLocation().iy() - NODE_RADIUS, 
		    		NODE_DIAMETER, NODE_DIAMETER);
		    if(this.Graph.getNodes().get(k).getTag() == 1) {
		    	g.setColor(Color.black);
			    g.drawOval(this.Graph.getNode(k).getLocation().ix() - NODE_RADIUS, this.Graph.getNode(k).getLocation().iy() - NODE_RADIUS, 
			    		NODE_DIAMETER, NODE_DIAMETER);
		    }
		    else {
			    g.setColor(Color.GREEN);
			    g.drawOval(this.Graph.getNode(k).getLocation().ix() - NODE_RADIUS, this.Graph.getNode(k).getLocation().iy() - NODE_RADIUS, 
			    		NODE_DIAMETER, NODE_DIAMETER);
		    }
		    g.setColor(Color.BLACK);
		    g.drawString(String.valueOf(this.Graph.getNode(k).getKey()), this.Graph.getNode(k).getLocation().ix() - (NODE_RADIUS/4), 
		    		this.Graph.getNode(k).getLocation().iy() + (NODE_RADIUS/4));
		  }
	  }
	  /*
	   * This method change the points on the nodes such that they will be in the canvas range. 
	   */
	  public void fix() {
		  for(int u : this.Graph.getNodes().keySet()) {
			  double x = Math.random()*250+100;
			  double y = Math.random()*250+100;
			  this.Graph.getNodes().get(u).setLocation(new Point3D(x,y,0));
		  }
	  }
	  /*
	   * This method save the image of the graph.
	   */
	  public void saveImage(String name,String type) {
		  	BufferedImage image = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g2 = image.createGraphics();
			paint(g2);
			try{
				ImageIO.write(image, type, new File(name+"."+type));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	  }
	
