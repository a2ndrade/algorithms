package course.algorithms.week4;

import static java.util.Collections.sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Download the text file here: http://spark-public.s3.amazonaws.com/algo1/programming_prob/SCC.txt
 * 
 * The file contains the edges of a directed graph. Vertices are labeled as positive integers from 1 to 875714. Every
 * row indicates an edge, the vertex label in first column is the tail and the vertex label in second column is the head
 * (recall the graph is directed, and the edges are directed from the first column vertex to the second column vertex).
 * So for example, the 11th row looks liks : "2 47646". This just means that the vertex with label 2 has an outgoing
 * edge to the vertex with label 47646
 * 
 * Your task is to code up the algorithm from the video lectures for computing strongly connected components (SCCs), and
 * to run this algorithm on the given graph.
 * 
 * Output Format: You should output the sizes of the 5 largest SCCs in the given graph, in decreasing order of sizes,
 * separated by commas (avoid any spaces). So if your algorithm computes the sizes of the five largest SCCs to be 500,
 * 400, 300, 200 and 100, then your answer should be "500,400,300,200,100". If your algorithm finds less than 5 SCCs,
 * then write 0 for the remaining terms. Thus, if your algorithm computes only 3 SCCs whose sizes are 400, 300, and 100,
 * then your answer should be "400,300,100,0,0".
 * 
 * @author rcxr
 */
public class Question1 {
  
  public static void main(String[] args) throws FileNotFoundException {
    Scanner in = new Scanner(new File("src/course/algorithms/week4/SCC.txt"));
    // Create a graph
    Graph graph = new Graph();
    while (in.hasNext()) {
      // Incrementally construct the graph in linear time O(m)
      graph.addEdge(in.nextInt(), in.nextInt());
    }
    // Get the 
    List<Integer> sccSizes = graph.getSCCSizes();
    // Sort the CSS sizes in O(n log (n))
    sort(sccSizes);
    for (Integer size : sccSizes) {
      // Print the dimension of the strongly connected component classes
      System.out.println(size);
    }
  }

  /**
   * Custom representation of a graph. We only care about the nodes since the nodes intrinsically keep reference to the
   * edges.
   */
  public static class Graph {
    // A map of nodes to improve performance and to allow the algorithm to work with a dynamic number of nodes
    HashMap<Integer, Node> nodes;
    
    public Graph() {
      nodes = new HashMap<Integer, Node>();
    }
    
    public List<Integer> getSCCSizes() {
      // For performance we remember how many nodes we have
      int n = nodes.size();
      // For performance we use an array list
      // This list will hold the nodes in finishing time order
      List<Node> list = new ArrayList<Node>(n);
      for (Node node : nodes.values()) {
        // We travel through the graph backwards and fill the list
        node.visitBackwards(list);
      }
      List<Integer> sccSizes = new LinkedList<Integer>();
      while (0 < n--) {
        // We use DFS taking the finishing time order. Every time we are done with a node branch we can tell that we
        // visited all the nodes in a SCC
        sccSizes.add(list.get(n).countReachableNonVisitedComponents());
      }
      return sccSizes; 
    }

    void addEdge(int fromId, int toId) {
      Node from = nodes.get(fromId);
      if (null == from) {
        from = new Node(fromId);
        nodes.put(fromId, from);
      }
      Node to = nodes.get(toId);
      if (null == to) {
        to = new Node(toId);
        nodes.put(toId, to);
      }
      from.addTo(to);
      to.addFrom(from);
    }
    
    class Node {
      /**
       * Whether the node has been visited when traveling through the graph with the edges reversed
       */
      boolean visitedBackwards;
      /**
       * Whether the node has been visited when traveling through the graph normally
       */
      boolean visited;
      /**
       * List of nodes reaching this one
       */
      List<Node> from;
      /**
       * List of nodes that are reachable from this one
       */
      List<Node> to;
      
      Node(int id) {
        from = new LinkedList<Node>();
        to = new LinkedList<Node>();
      }
      
      public int countReachableNonVisitedComponents() {
        if (visited) {
          return 0;
        }
        // Stop propagation of recursivity
        visited = true;
        int count = 0;
        for (Node node : to) {
          count += node.countReachableNonVisitedComponents();
        }
        // All the nodes that are reachable plus this one
        return 1 + count;
      }
      
      public void visitBackwards(List<Node> list) {
        if (visitedBackwards) {
          return;
        }
        visitedBackwards = true;
        for (Node node : from) {
          node.visitBackwards(list);
        }
        list.add(this);
      }

      void addTo(Node to) {
        this.to.add(to);
      }
      
      void addFrom(Node from) {
        this.from.add(from);
      }
    }
  }
}
