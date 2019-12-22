package dataStructure;

import java.util.ArrayList;

import utils.Point3D;

public class Node_Data implements node_data{
	private int key;
	private int tag;
	private double weight;
	private Point3D Location;
	private String Info;
	private ArrayList<Node_Data> Neighbors = new ArrayList<Node_Data>();
	
	public Node_Data(int key,double weight, Point3D location,String info) {
		this.key = key;
		this.weight = weight;
		this.Location = location;
		this.Info = info;
	}
	
	public ArrayList<Node_Data> getNeighbors(){
		return this.Neighbors;
	}
	public void addNeighbor(Node_Data n) {
		this.Neighbors.add(n);
	}
	public void clearNeighbors() {
		this.Neighbors.clear();
	}
	@Override
	public int getKey() {
		return this.key;
	}
	
	@Override
	public Point3D getLocation() {
		return this.Location;
	}

	@Override
	public void setLocation(Point3D p) {
		this.Location = p;
	}
	
	@Override
	public double getWeight() {
		return this.weight;
	}
	
	@Override
	public void setWeight(double w) {
		if(w<0) {
			throw new RuntimeException("the Weight cannot be negative");
		}
		this.weight = w;
	}

	@Override
	public String getInfo() {
		return this.Info;
	}

	@Override
	public void setInfo(String s) {
		this.Info = s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;		
	}
	
}
