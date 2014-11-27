package fr.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juliencandela on 10/11/14.
 */
public class Vertex {
    private String name;

    private List<Edge> edges  = new ArrayList<Edge>();

    public Vertex(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public List<Edge> getEdges(){
        return edges;
    }

    public void connectTo(Vertex target, int distance){
        edges.add(new Edge(target, distance));

    }

}
