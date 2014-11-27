package fr.graph;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GraphTest {

    private Vertex lille = new Vertex("Lille");
    private Vertex paris = new Vertex("Paris");
    private Vertex reims = new Vertex("Reims");
    private Vertex nancy = new Vertex("Nancy");
    private Vertex lyon = new Vertex("Lyon");
    private Vertex marseille = new Vertex("Marseille");
    private Vertex lemans = new Vertex("Le Mans");
    private Vertex nantes = new Vertex("Nantes");
    private Vertex bordeaux = new Vertex("Bordeaux");
    private Vertex toulouse = new Vertex("Toulouse");
    private Vertex clermont = new Vertex("Clermont Ferrant");
    private Vertex montpellier = new Vertex("Montpellier");

    @Before
    public void setup() {
        lille.connectTo(reims, 206);
        lille.connectTo(paris, 222);
        lille.connectTo(nancy, 418);

        reims.connectTo(paris, 144);
        reims.connectTo(nancy, 245);
        reims.connectTo(lyon, 489);

        paris.connectTo(lyon, 465);
        paris.connectTo(lemans, 208);
        paris.connectTo(clermont, 423);

        lyon.connectTo(clermont, 166);
        lyon.connectTo(marseille, 313);
        lyon.connectTo(montpellier, 304);

        lemans.connectTo(nantes, 189);
        lemans.connectTo(bordeaux, 443);

        nantes.connectTo(bordeaux, 347);

        bordeaux.connectTo(toulouse, 243);

        toulouse.connectTo(montpellier, 245);

        montpellier.connectTo(marseille, 169);
        montpellier.connectTo(toulouse, 245);

        marseille.connectTo(montpellier, 169);

        clermont.connectTo(lyon, 166);
        clermont.connectTo(montpellier, 333);
        clermont.connectTo(marseille, 474);

    }

    @Test
    public void getDistanceForTwoAdjacentVertices() {
        Graph graph = new Graph(paris, lyon);
        Assert.assertEquals(graph.getDistance("Paris", "Lyon"), 465);
        Assert.assertEquals(graph.getDistanceAdjacentVertices("Paris", "Lyon"), 465);
    }

    @Test
    public void getDistanceViaAnIntermediateVertex() {
        Graph graph = new Graph(lille, paris, lyon, bordeaux);
        Assert.assertEquals(graph.getDistanceViaAVertex("Lille", "Lyon"), 687);
        Assert.assertEquals(graph.getDistanceViaAVertex("Lille", "Bordeaux"), -1);
    }

    @Test
    public void getMinimumDistanceViaTwoIntermediateVertices() {
        Graph graph = new Graph(paris, lemans, bordeaux, toulouse, montpellier, clermont);
        Assert.assertEquals(graph.getDistanceViaTwoVertices("Paris", "Toulouse"), 894);
    }

    @Test
    public void getDistanceViaNVertices() {
        Graph graph = new Graph(lille, paris, reims, nancy, lemans, nantes, bordeaux, clermont, lyon, toulouse, marseille, montpellier);
        Assert.assertEquals(graph.getDistanceViaNVertices("Lille", "Montpellier", 3), 1106);
    }

    @Test
    public void getOptimalDistance() {
        Graph graph = new Graph(lille, paris, reims, nancy, lemans, nantes, bordeaux, clermont, lyon, toulouse, marseille, montpellier);
        Assert.assertEquals(graph.getDistance("Lille", "Montpellier"), 978);
        Assert.assertEquals(graph.getDistance("Lille", "Marseille"), 1000);
    }

    @Test
    public void EmptyGraph(){
        Graph graph = new Graph();
        Assert.assertEquals(graph.getDistance("Paris", "Lille"), -1);
    }

    @Test
    public void NonexistentCity(){
        Graph graph = new Graph(lille, paris, reims, nancy, lemans, nantes, bordeaux, clermont, lyon, toulouse, marseille, montpellier);
        Assert.assertEquals(graph.getDistance("Fougere", "Grenoble"),-1);
    }

}