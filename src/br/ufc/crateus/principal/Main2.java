package br.ufc.crateus.principal;

import br.ufc.crateus.graph.BuildGraph2;
import br.ufc.crateus.graph.DijkstraAlgorithm;
import br.ufc.crateus.graph.Graph2;

public class Main2 {
	public static void main(String[] args) {
		Graph2 graph = BuildGraph2.readFile();
		DijkstraAlgorithm alg = new DijkstraAlgorithm();
		alg.dijkstra(graph);
		//Calcular Rotas
		//BuildGraph2.getRoutes();
	}
}
