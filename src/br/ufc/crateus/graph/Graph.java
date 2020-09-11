package br.ufc.crateus.graph;

public class Graph {
	private String[] devices;
	private int[][] adjacencias;
	private Enlace[][] tabelaDeRepasse;
	
	public Graph(String[] devices) {
		int length = devices.length;
		this.devices = devices;
		this.adjacencias = new int[length][length];
		this.tabelaDeRepasse = new Enlace[length][length];

		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) 
				adjacencias[i][j] = -1; 
			this.adjacencias[i][i] = 0;
		}
	}
	
	public void addLink(String deviceStr1,String deviceStr2, int cost) {
		try {
			int p1 = indexDevice(deviceStr1);
			int p2 = indexDevice(deviceStr2);
			
			if(p1 == -1 || p2==-1)
				throw new Exception("O device não está cadastrado na Rede/Grafo");
			adjacencias[p1][p2] = cost;
			adjacencias[p2][p1] = cost;
			
		} catch (Exception e) {
			if(e.getMessage().length() == 0)
				System.err.println("O Arquivo não está no formato pré-estabelecido");
			else 
				System.err.println(e.getMessage());
		}
	}

	private int indexDevice(String device) {
		for(int i = 0; i < devices.length; i++)
			if(device.equals(devices[i]))
				return i;
		return -1;
	}
	
	public void algorithmDijkstra() {
		initializesAlgorithmDijkstra();
		
		int iteration = 0;
		for(int i = 0 ; i < devices.length; i++) {	
			iteration = iterationAlgorithmDijkstra(i, iteration);
		}
		
	}
	
	private void initializesAlgorithmDijkstra() {
		for(int i = 0; i < devices.length; i++) 
			for(int j = 0; j < devices.length; j++) 
				if(adjacencias[i][j] != -1) 
					tabelaDeRepasse[i][j] = new Enlace(i,j,adjacencias[i][j]);
		showTabelaDeRepasse("Fase de Inicialização");
	}
	
	private int iterationAlgorithmDijkstra (int posDevCurr, int iteration) {
		
	    boolean[] visitedVertex = new boolean[devices.length];
		
	    for(int i = 0; i < devices.length; i++) {
			int posNeighborLowest =  getIndexLowerCostNeighbor(posDevCurr, visitedVertex);
			if(posNeighborLowest == -1)
				break;
			visitedVertex[posNeighborLowest] = true;
			iteration = addAnalyzeNeighbor(posDevCurr, posNeighborLowest, visitedVertex, iteration);

			iteration++;
			showTabelaDeRepasse("Fase de Iteração " + iteration);
	    }
		return iteration;
	}

	private int getIndexLowerCostNeighbor(int posDevCurr, boolean[] visitedVertex) {
		int lowerCost = -1;
		int posLowerCost = -1;
		
		for(int i = 0; i < tabelaDeRepasse[posDevCurr].length; i++) {
			if(visitedVertex[i] || tabelaDeRepasse[posDevCurr][i] == null || posDevCurr == i)
				continue;
			int tmpCost = tabelaDeRepasse[posDevCurr][i].getCost();
			if(tmpCost < lowerCost || lowerCost==-1) {
				lowerCost = tmpCost;
				posLowerCost = i;
			}
		}
		return posLowerCost;
	}
	
	private int addAnalyzeNeighbor(int posDev, int posNeig, boolean[] visitedVertex, int iteration) {
		
		for(int v = 0 ; v < tabelaDeRepasse[posNeig].length; v++) {
			
			if(visitedVertex[v] || posNeig==v || tabelaDeRepasse[posNeig][v] == null) 
				continue;
			
			int costSum = tabelaDeRepasse[posDev][posNeig].getCost()+tabelaDeRepasse[posNeig][v].getCost();
			if(tabelaDeRepasse[posDev][v] == null || costSum < tabelaDeRepasse[posDev][v].getCost()) {
				tabelaDeRepasse[posDev][v] = new Enlace(posDev, posNeig, costSum);
			}
		}
		return iteration;
	}
	
	private void showTabelaDeRepasse (String stage) {
		System.out.println("\n"+stage);
		String str = " ";
		
		for(int i = 0 ; i < devices.length; i++) 
			str += devices[i] + ":\t   | ";
		System.out.println(str);

		
		str = "";
		for(int i = 0 ; i < devices.length; i++) {
			for(int j = 0 ; j < devices.length; j++) {
				str += devices[i]+",(";
				if(tabelaDeRepasse[i][j] != null) 
					str += devices[tabelaDeRepasse[i][j].getPosOrigin()]+","+devices[tabelaDeRepasse[i][j].getPosDestiny()]+"),"+ tabelaDeRepasse[i][j].getCost()+"  | ";
				else
					str += "-,-),-1 | ";
			}
			str +="\n";
		}
		System.out.println(str);
	}

	public int getLengthDevices() {
		return devices.length;
	}
	
	public String getDevice(int pos) {
		if(pos < devices.length)
			return devices[pos];
		return "";
	}

	public String[] getDevices() {
		return devices;
	}

	public int getValueAdjacencia(int i, int j) {
		if(i < devices.length && j < devices.length)
			return adjacencias[i][j];
		return -1;
	}
	
	public int[][] getAdjacencias() {
		return adjacencias;
	}
	
	@Override
	public String toString() {
		String devicesTxt = "[";
		String adjacenciasTxt = "";
		String tabelaTxt = "";

		for(String device : devices)
			devicesTxt += device + ",";
		devicesTxt+="]";
		
		for(int[] adjacenciaLine : adjacencias) {
			adjacenciasTxt+="\n[";
			for(int adjacencia : adjacenciaLine)
				adjacenciasTxt += adjacencia + ",";
			adjacenciasTxt+="],";
		}
		adjacenciasTxt+="";
		
		for(Enlace[] line : tabelaDeRepasse) {
			tabelaTxt+="\n[";
			for(Enlace enlace : line) {
				tabelaTxt += ( enlace != null ? enlace.toString() : "-1") + ",";
			}
			tabelaTxt+="],";
		}
		tabelaTxt+="";

		return "\nDevices:"+devicesTxt+
				"\nAdjacencia:"+adjacenciasTxt+
				"\nEnlaces:"+tabelaTxt;
	}
	
}