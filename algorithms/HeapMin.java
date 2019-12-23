package algorithms;
import dataStructure.Node;
public class HeapMin {

	double _positiveInfinity = Double.POSITIVE_INFINITY;
	final int INIT_SIZE = 10;
	private Node _a[];
	private int _size;
	public HeapMin(Node arr[]){
		_size = arr.length;
		_a = new Node[_size];
		for (int i=0; i<_size; i++){
			_a[i]=arr[i];
		}
	}
	public HeapMin(){
		_a = new Node[0];
	}
	/** returns the heap size*/
	public int getSize(){return _size;}
	/** returns the heap array */
	public Node[] getA(){ return _a;}
	
	/** parent returns the parent of name  i*/
	private  int parent(int i){return (i-1)/2;}
	
	/** leftChild returns the left child of name  i*/
	private  int leftChild(int i){return 2*i+1;}
	/** rightChild returns the right child of name  i*/
	private  int rightChild(int i){return 2*i+2;}
	/** returns the heap minimum */
	public Node heapMinimum(){return _a[0];}
	/** returns true if the heap is empty, otherwise false */
	public boolean isEmpty(){
		boolean ans = false;
		if (_size == 0) ans = true;
		return ans;
	}
	/** the minHeapfy function maintains the min-heap property */
	private void minHeapify(int v, int heapSize){
		int smallest;
		int left = leftChild(v);
		int right = rightChild(v);
		if (left<heapSize && _a[left].getWeight()<_a[v].getWeight()){
			smallest = left;
		}
		else{
			smallest = v;
		}
		if (right<heapSize && _a[right].getWeight()<_a[smallest].getWeight()){
			smallest = right;
		}
		if (smallest!=v){
			exchange(v, smallest);
			minHeapify(smallest, heapSize);
		}		
	}
	/** the heap minimum element extraction */
	public Node heapExtractMin(){
		double min = _positiveInfinity; 
		Node v=null;
		if (!isEmpty()){
			v = _a[0];
			min = v.getWeight();
			_a[0]=_a[_size-1];
			_size = _size-1;
			minHeapify(0, _size);
		}
		return v;
	}
	/** the heapDecreaseKey implements the Decrease Key operation*/
	public void heapDecreaseKey(Node node){
		int v = node.name;
		int i = 0;
		while (i<_size && v!=_a[i].name) i++;
		if (node.getWeight() <_a[i].getWeight()){
			_a[i] = node;
			while (i>0 && _a[parent(i)].getWeight()>_a[i].getWeight()){
				exchange(i, parent(i));
				i = parent(i);
			}
		}
	}
	/** minHeapInsert function implements the Insert-Key operation*/
	public void minHeapInsert(Node node){
		resize(1);
		_a[_size-1] = new Node(node);
		_a[_size-1].getWeight() = _positiveInfinity;
		heapDecreaseKey(node);
	}
	/** increment an array*/
	private void resize(int increment){
		Node temp[] = new Node[_size+increment];
		for (int i=0; i<_size; i++){
			temp[i]=_a[i];
		}
		_a = temp;
		_size = _size+increment;
	}	
	/** exchange two array elements*/
	private void exchange(int i, int j){
		Node t = _a[i];
		_a[i] = _a[j];
		_a[j] = t;
	}
/*	*//** print a heap array **//*
	public void print(){
		for (int i=0; i<_size; i++){
			System.out.print(_a[i]+"; ");
		}
		System.out.println();
	}*/
	public boolean contains(int name){
		boolean ans = false;
		for (int i=0; !ans && i<_size; i++){
			if (_a[i].name == name) ans = true;
		}
		return ans;
	}
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node nodes1[] = {new Node(16,0),
						 new Node(5,1),
						 new Node(8,2),
						 new Node(4,3),
						 new Node(2,4),
						 new Node(1,5)}; 
		//int nodes1[] = {16,8,4,2,1}; 
		//int nodes2[] = {5,13,2,25,7,17,2,8,4}; 
		//int nodes3[] = {16,14,10,8,7,9,3,2,4,1}; 
		HeapMin hp = new HeapMin(nodes1);
		hp.buildMinHeap();
		hp.print();
		Node node = new Node(3,nodes1.length);
		hp.minHeapInsert(node);
		hp.print();
		System.out.println("min = "+ hp.heapExtractMin());
		hp.print();
		System.out.println("min = "+ hp.heapExtractMin());
		hp.print();
/// heap sort
		
		HeapMin hps = new HeapMin(nodes1);
		hps.heapSort();
		hps.print();
	}
*/
}
