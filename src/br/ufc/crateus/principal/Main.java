package br.ufc.crateus.principal;

import java.util.List;

import br.ufc.crateus.graph.BuildGraph;
import br.ufc.crateus.graph.DijkstraAlgorithm;
import br.ufc.crateus.graph.Graph;

public class Main {

	public static void main(String[] args) {
		Graph graph = BuildGraph.readFile("test3.txt");
		DijkstraAlgorithm alg = new DijkstraAlgorithm(graph);
		alg.dijkstra();
		
		List<String[]> routesArray = BuildGraph.getRoutes();
		for(String[] routes : routesArray) 
			alg.shortestRoute(routes[0], routes[1]);
		
	}

}
