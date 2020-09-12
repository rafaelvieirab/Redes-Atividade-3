package br.ufc.crateus.graph;

public class DijkstraAlgorithm {
	private Enlace[][] tabelaDeRepasse;
	Graph graph;
	int numberDevices;
	
	public DijkstraAlgorithm(Graph g) {
		this.graph = g;
		this.numberDevices = g.getLengthDevices();
		this.tabelaDeRepasse= new Enlace[numberDevices][numberDevices];
		dijkstra();
	}
	
	public void dijkstra() {
		initializes();
		int iteration = 0;
		for(int i = 0; i < numberDevices; i++) 
			iteration = iteration(i, iteration);
	}

	private void initializes() {
		for(int i = 0; i < numberDevices; i++) 
			for(int j = 0; j < numberDevices; j++) 
				if(graph.getValueAdjacencia(i, j) != -1) 
					tabelaDeRepasse[i][j] = new Enlace(i, j, graph.getValueAdjacencia(i, j));
		showTabelaDeRepasse("Fase de Inicialização");
	}

	private int iteration (int posCur, int iteration) {
	    boolean[] visitedVertex = new boolean[numberDevices];
		
	    for(int i = 0; i < numberDevices; i++) {
			int posNeighborLowest =  getIndexLowerCostNeighbor(posCur, visitedVertex);
			if(posNeighborLowest == -1)
				break;
			visitedVertex[posNeighborLowest] = true;
			iteration = addAnalyzeNeighbor(posCur, posNeighborLowest, visitedVertex, iteration);
	    }
	    iteration++;
		showTabelaDeRepasse("Fase de Iteração " + iteration);
		return iteration;
	}

	private int getIndexLowerCostNeighbor(int posCur, boolean[] visitedVertex) {
		int lowerCost = -1;
		int posLowerCost = -1;
		
		for(int i = 0; i < numberDevices; i++) {
			if(visitedVertex[i] || tabelaDeRepasse[posCur][i] == null || posCur == i)
				continue;
			int tmpCost = tabelaDeRepasse[posCur][i].getCost();
			if(tmpCost < lowerCost || lowerCost == -1) {
				lowerCost = tmpCost;
				posLowerCost = i;
			}
		}
		return posLowerCost;
	}
	
	private int addAnalyzeNeighbor(int posCur, int posNeig, boolean[] visitedVertex, int iteration) {
		
		for(int v = 0 ; v < tabelaDeRepasse[posNeig].length; v++) {
			if(visitedVertex[v] || posNeig==v || tabelaDeRepasse[posNeig][v] == null) 
				continue; 
			
			int costSum = tabelaDeRepasse[posCur][posNeig].getCost()+tabelaDeRepasse[posNeig][v].getCost();
			if(tabelaDeRepasse[posCur][v] == null || costSum < tabelaDeRepasse[posCur][v].getCost()) 
				tabelaDeRepasse[posCur][v] = new Enlace(posCur, posNeig, costSum);
		}
		return iteration;
	}
	
	private void showTabelaDeRepasse(String stage) {
		String[] devices = graph.getDevices();
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
				if(tabelaDeRepasse[i][j] != null) 
					str += devices[tabelaDeRepasse[i][j].getPosOrigin()] + ","
						   + devices[tabelaDeRepasse[i][j].getPosDestiny()] + ")," 
						   + tabelaDeRepasse[i][j].getCost() + "  | ";
				else
					str += "-,-),-1 | ";
			}
			str +="\n";
		}
		System.out.println(str);
	}
	
	public void shortestRoute(String origin, String destiny) {
		System.out.print("\nRota (" + origin + "," + destiny + "):");
		
		try {
			int posOrig = graph.indexDevice(origin);
			int posDest = graph.indexDevice(destiny);
			
			if(posOrig == -1 || posDest==-1)
				throw new Exception("O device não está cadastrado na Rede/Grafo");
			
			String route1 = returnRouteBetween(posOrig, posDest);
			String route2 = returnRouteBetween(posDest, posOrig);
			
			if(route1.length() >= route2.length())
				System.out.println(route1);
			else
				System.out.println(route1);
			
		} catch (Exception e) {
			if(e.getMessage().length() == 0)
				System.err.println("O Arquivo não está no formato pré-estabelecido");
			else 
				System.err.println(e.getMessage());
		}
	}

	private String returnRouteBetween(int posOrig, int posDest) {
		int orig = posOrig;
		int dest = tabelaDeRepasse[orig][posDest].getPosDestiny();
		String str = "(" + graph.getDevice(orig) + "," + graph.getDevice(dest) + "),";
		
		do {
			orig = dest;
			dest = tabelaDeRepasse[orig][posDest].getPosDestiny();
			str +="(" + graph.getDevice(orig) + "," + graph.getDevice(dest) + "),";
		} while(dest != posDest);
		return str;
	}
	
}