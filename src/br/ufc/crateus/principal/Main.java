package br.ufc.crateus.principal;

import java.util.List;

import br.ufc.crateus.graph.BuildGraph;
import br.ufc.crateus.graph.Graph;

public class Main {

	public static void main(String[] args) {
		Graph graph = BuildGraph.readFile("test3.txt");
		List<String[]> routes = BuildGraph.extractRoutes();
		
		graph.algorithmDijkstra();

	}

}
