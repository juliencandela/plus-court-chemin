package fr.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by juliencandela on 10/11/14.
 */
public class Graph {

    private List<Vertex> vertices = new ArrayList<Vertex>();

    public Graph(Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public int getDistanceAdjacentVertices(String from, String to) {
        int distance = -1;
        for (Vertex vertex : this.getVertices()) {
            if (vertex.getName() == from) {
                for (Edge edge : vertex.getEdges()) {
                    if (edge.getTarget().getName() == to) {
                        distance = edge.getDistance();
                    }
                }
            }
        }
        return distance;
    }

    public int getDistanceViaAVertex(String from, String to) {
        int distance = -1;
        List<Integer> distances = new ArrayList<Integer>();
        List<Vertex> intermediateVertices = this.getIntermediateVertices(from);

        for (Vertex intermediate_vertex : intermediateVertices) {
            List<Vertex> nextVertices = this.getIntermediateVertices(intermediate_vertex.getName());
            for (Vertex nextVertex : nextVertices) {
                if (nextVertex.getName() == to) {
                    distances.add(getDistanceViaWithVia(from, to, intermediate_vertex.getName()));
                }
            }
        }

        if (!distances.isEmpty()) {
            distance = Collections.min(distances);
        }

        return distance;
    }

    public int getDistanceViaTwoVertices(String from, String to) {

        List<Integer> distances = new ArrayList<Integer>();
        List<Vertex> intermediateVertices = this.getIntermediateVertices(from);
        int distance = -1;
        for (Vertex intermediate_vertex : intermediateVertices) {
            List<Vertex> secondIntermediateVertices = this.getIntermediateVertices(intermediate_vertex.getName());

            for (Vertex second_intermediate_vertex : secondIntermediateVertices) {
                List<Vertex> nextVertices = this.getIntermediateVertices(second_intermediate_vertex.getName());

                for (Vertex nextv : nextVertices) {
                    if (nextv.getName() == to) {

                        distances.add(getDistanceViaTwoVerticesWithVia(from, to, intermediate_vertex.getName(), second_intermediate_vertex.getName()));
                    }
                }
            }
        }

        if (!distances.isEmpty()) {
            distance = Collections.min(distances);
        }

        return distance;

    }

    public int getDistanceViaNVertices(String from, String to, int numberOfNodes) {
        int distance = -1;

        if (numberOfNodes >= 2) {
            ArrayList<Integer> distances = new ArrayList<Integer>();
            ArrayList<String> cities = new ArrayList<String>();

            for (Vertex vertex : this.getVertices()) {
                if (vertex.getName() == from) {
                    for (Edge edge : vertex.getEdges()) {
                        String by = edge.getTarget().getName();
                        int d = getDistanceViaNVertices(by, to, numberOfNodes - 1);
                        if (d != -1) {
                            int _distance = d + getDistanceAdjacentVertices(from, by);
                            distances.add(_distance);
                        }
                    }
                }
            }
            if (!distances.isEmpty()) {
                distance = Collections.min(distances);
            }
        }

        if (numberOfNodes == 1) {
            distance = getDistanceViaAVertex(from, to);
        }

        if (numberOfNodes == 0) {
            distance = getDistanceAdjacentVertices(from, to);
        }

        return distance;

    }

    public int getDistance(String from, String to) {
        ArrayList<Integer> distances = new ArrayList<Integer>();
        int distance = -1;
        for (int n = 0; n <= this.vertices.size(); n++) {
            int d = getDistanceViaNVertices(from, to, n);
            if (d != -1) {
                distances.add(d);
            }
        }
        if (!distances.isEmpty()) {
            distance = Collections.min(distances);
        }
        return distance;
    }

    private List<Vertex> getIntermediateVertices(String from) {
        List<Vertex> intermediateVertices = new ArrayList<Vertex>();

        for (Vertex vertex : this.getVertices()) {
            if (vertex.getName() == from) {
                for (Edge edge : vertex.getEdges()) {
                    if (vertices.contains(edge.getTarget())) {
                        intermediateVertices.add(edge.getTarget());
                    }
                }
            }
        }
        return intermediateVertices;
    }

    private int getDistanceViaWithVia(String from, String to, String via) {
        return (this.getDistanceAdjacentVertices(from, via) + this.getDistanceAdjacentVertices(via, to));
    }

    private int getDistanceViaTwoVerticesWithVia(String from, String to, String via_first, String via_second) {
        return (this.getDistanceViaWithVia(from, via_second, via_first) + this.getDistanceAdjacentVertices(via_second, to));
    }
}
