package br.ufc.crateus.graph;

public class DijkstraAlgorithm2 {
	public void dijkstra(Graph2 g) {
		Enlace[][] tabelaDeRepasse = new Enlace[g.getLengthDevices()][g.getLengthDevices()];
		
		int numberIter = 0;
//		numberIter = iterationDijkstra(g, 0,numberIter);
		for(int i = 0; i < g.getDevices().length; i++) {
			tabelaDeRepasse[i] = iterationDijkstra(g, i,numberIter);
		}
		
		showTabelaDeRepasse("Inicialização", tabelaDeRepasse, g.getDevices());
	}

	private void showTabelaDeRepasse(String stage, Enlace[][] table, String[] devices) {
		int length = devices.length;
		String str = " ";
		System.out.println("\nFase de "+stage);
		
		for(int i = 0 ; i < length; i++) 
			str += devices[i] + ":\t   | ";
		System.out.println(str);

		
		str = "";
		for(int i = 0 ; i < length; i++) {
			for(int j = 0 ; j < length; j++) {
				str += devices[i]+",(";
				if(table[i][j] != null) 
					str += devices[table[i][j].getPosOrigin()]+","+devices[table[i][j].getPosDestiny()]+"),"+ table[i][j].getCost()+"  | ";
				else
					str += "-,-),-1 | ";
			}
			str +="\n";
		}
		System.out.println(str);
	}
	
	private Enlace[] iterationDijkstra(Graph2 g, int posCurr, int numberIter) {
		int length = g.getLengthDevices();
		
		boolean[] visited= new boolean[length];
		int[] distances = new int[length];
		int[] previous = new int[length];
		Enlace[] enlaces = new Enlace[length];

		for (int i = 0; i < length; i++) {
			distances[i] = -1;//Integer.MAX_VALUE;
			previous[i] = -1;
		}
		distances[posCurr] = 0;
		previous[posCurr] = 0;
		
				;
		for (int i = 0; i < length; i++) {
			int nearestNeighbor = getIndexLowerCostNeighbor(distances, visited);
			visited[nearestNeighbor] = true;

			for (int v = 0; v < length; v++) {
				if (!visited[v] && g.getValueAdjacencia(nearestNeighbor, v) > 0) {
					int tmpDistance =  distances[nearestNeighbor] + g.getValueAdjacencia(nearestNeighbor, v);
					if(distances[nearestNeighbor] == -1) {
						System.out.println("Entrou");
						//continue;
					}
					if(tmpDistance <  distances[v] ||  distances[v] == -1) {
						//System.out.println("Distãncia Temp:"+tmpDistance);
						distances[v] = tmpDistance;
						previous[v] = nearestNeighbor;
					}
				}
			}

		}
		for (int i = 0; i < length; i++) {
			enlaces[i] = new Enlace(posCurr, previous[i],  distances[i]);
			System.out.println(String.format("Distance from %s to %s is %s", g.getDevice(posCurr), g.getDevice(i),  distances[i]));
		}
		return enlaces;
	}

	private int getIndexLowerCostNeighbor(int[] distances, boolean[] visited) {
		int lowestDistance =  -1; 
		int posLowestDistance = -1; 
		
		for (int i = 0; i < distances.length; i++) { 
			if (!visited[i] && (distances[i] < lowestDistance || lowestDistance == -1) && distances[i]!=-1) {
				lowestDistance = distances[i];
				posLowestDistance = i;
			}
		}
		return posLowestDistance;
	}


}
