package dataStructure;

public class Edge implements edge_data{

	private Node Src;
	private Node Dest;
	private int Tag;
	private double weight;
	private String Info;
	public Edge(Node src, Node dest, double weight) {
		this.Src = src;
		this.Dest = dest;
		this.weight = weight;
	}
	@Override
	public int getSrc() {
		return this.Src.getKey();
	}

	@Override
	public int getDest() {
		return this.Dest.getKey();
	}

	@Override
	public double getWeight() {
		return this.weight;
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
		return this.Tag;
	}

	@Override
	public void setTag(int t) {
		this.Tag = t;
	}

}