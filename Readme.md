# TheMazeOfWaze

This Project is an Exercise in an object  **Object-oriented**  programming (**OOP**) course in  **Ariel university**.

Authors:  **Ibrahem chahine, Ofir Peller.**

## Description

This project is made to create and use  **Graph Theory Algorithms**, in this project the user will be able 
to create graphs with the GUI.


**- In the DGraph class** 
There are 2 constructors. A default constructor and a copy constructor.
• The user can use the following methods :
```
getNode(int key) - Will return the node data with the key.
getEdge(int src, int dest) - Will return the Edge data with the source and destination keys.
addNode(node_data n) - This method adds a node to the Graph.
connect(int src, int dest, double w) - This method creates an edge such that the source is src and the destination in dest.
getV() - Will return a shallow copy of the Collection of Nodes in the Graph.
getE(int node_id) - Will return a shallow copy of the Collection of Edges in the Graph.
removeNode(int key) - This method removes the node the belongs to the given key.
removeEdge(int src, int dest) - This method removes the edge of the src anf dest.
nodeSize() - Will return the number of nodes in the graph.
edgesize() - Will return the number of edges in the graph.
getMC - Will return the number of changes in the graph. 
```
**- In the Graph_Algo class**:
```
init(graph g) - Inits with graph g.
copy() - Returns a deepcopy of the graph.
save(String file_name) - Saves the Project to file.
init(String file_name) - Inits the Project from file.
isConnected() - Check if the Graph is srongly connected.
shortestPathDist(int src, int dest) - Returns the Destination of the shortest path from src to dest. 
shortestPath(int src, int dest) - Returns a list of nodes representing the shortest path from src to dest.
TSP(List<Integer> targets) - Returns a list of nodes representing the TSP path from the targets.
```
**- In the GraphGUI class**
```
In this class the user will see a Visualization of the graph the user created.
And the user will be able to edit the graph in the GUI window.
```
## Support

For help you can go to the javadoc. you can get a better explanations for the methods in the classes.

In the wiki we explain how to use this project, its prefered to read the instructions in the  **wiki pages.**

## Contributing

If you want to make changes to the code i will recommend to go over the tester before, it will help you to understand how the Methods and the Classes work. 

## Authors and acknowledgment

The Authors of this project are  **Ibrahem chahine, Ofir Peller.**

I want to thank all the GitHub open source users. Also, we thank :

1.  GeeksforGeeks.org,
2.  [stackoverflow.com].
3.  [GitHub.com].
4.  [[https://www.math.ucla.edu/~akrieger/teaching/18s/pic20a/examples/complex/](https://www.math.ucla.edu/~akrieger/teaching/18s/pic20a/examples/complex/)]
5.  [[https://www.makeareadme.com/#template-1](https://www.makeareadme.com/#template-1)]
6.  [youtube.com]
