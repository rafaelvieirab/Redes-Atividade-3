package br.ufc.crateus.principal;

import br.ufc.crateus.graph.BuildGraph;
import br.ufc.crateus.graph.Graph;

public class Main {

	public static void main(String[] args) {
		Graph graph = BuildGraph.readFile("test3.txt");
		graph.algorithmDijkstra();
		
		//Calcular Rotas
		//BuildGraph2.getRoutes();
	}

}
