package br.ufc.crateus.graph;

public class DijkstraAlgorithm {
	public static void dijkstra(Graph2 g) {
		int numberIter = 0;
		numberIter = iterationDijkstra(g, 0,numberIter);
		for(int i = 0; i < g.getDevices().length; i++) {
			numberIter = iterationDijkstra(g, i,numberIter);
		}
	}

	private static int iterationDijkstra(Graph2 g, int posCurr, int numberIter) {
		int length = g.getLengthDevices();
		boolean[] visited= new boolean[length];
		int[] distance = new int[length];
		int[] previous = new int[length];

		for (int i = 0; i < length; i++) {
			visited[i] = false;
			distance[i] = -1;//Integer.MAX_VALUE;
			previous[i] = -1;
		}
		distance[posCurr] = 0;

		for (int i = 0; i < length; i++) {

			int nearestNeighbor = findLowestDistance(distance, visited);
			visited[nearestNeighbor] = true;

			for (int v = 0; v < length; v++) {
				if (!visited[v] && g.getValueAdjacencia(nearestNeighbor, v) > 0) {
					int tmpDistance = distance[nearestNeighbor] + g.getValueAdjacencia(nearestNeighbor, v);

					if(distance[nearestNeighbor] == -1) {
						System.out.println("Entrou");
						//continue;
					}
					if(tmpDistance < distance[v] || distance[v] == -1) {
						System.out.println("Distãncia Temp:"+tmpDistance);
						distance[v] = tmpDistance;
						previous[v] = nearestNeighbor;
					}
				}
			}

		}
		for (int i = 0; i < distance.length; i++) 
			System.out.println(String.format("Distance from %s to %s is %s", g.getDevice(posCurr), g.getDevice(i), distance[i]));
		return numberIter;
	}

	private static int findLowestDistance(int[] distance, boolean[] visited) {
		int lowestDistance = distance[0]; 
		int posLowestDistance = 0; 
		
		System.out.println("\nComeça aqui");
		for (int i = 0; i < distance.length; i++) { 
			System.out.print(distance[i]+",");
			if (!visited[i] && distance[i] < lowestDistance && distance[i]!=-1) {
				System.out.println("Entrou: "+distance[i]);
				lowestDistance = distance[i];
				posLowestDistance = i;
			}
		}
		System.out.println("");
		System.out.println("Lowest:"+distance[posLowestDistance]);
		return posLowestDistance;
	}

	public static void main(String[] args) {
		Graph2 graph = BuildGraph2.readFile("test3.txt");
		DijkstraAlgorithm.dijkstra(graph);
	}

}
