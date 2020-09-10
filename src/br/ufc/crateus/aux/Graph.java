package br.ufc.crateus.aux;

public class Graph {
	private String[] devices;
	private boolean[][] adjacencias;
	private int[][] distances;
	private Enlace[][] distancesEnlaces;
	
	public Graph(String[] devices) {
		int length = devices.length;
		this.devices = devices;
		this.adjacencias = new boolean[length][length];
		this.distances = new int[length][length];
		this.distancesEnlaces = new Enlace[length][length];
		
		
		for(int i = 0; i < length; i++) 
			for(int j = 0; j < length; j++) 
				this.distances[i][j] = -1;

		for(int i = 0; i < length; i++) {
			this.adjacencias[i][i] = true;
			this.distances[i][i] = 0;
			this.distancesEnlaces[i][i] = new Enlace(i,i,0);
		}
	}
	
	public void addLink(String deviceStr1,String deviceStr2, int cost) {
		try {
			int p1 = indexDevice(deviceStr1);
			int p2 = indexDevice(deviceStr2);
			
			if(p1 == -1 || p2==-1)
				throw new Exception("O device não está cadastrado na Rede/Grafo");
			adjacencias[p1][p2] = true;
			adjacencias[p2][p1] = true;
			distances[p1][p2] = cost;
			distances[p2][p1] = cost;
			distancesEnlaces[p1][p2] = new Enlace(p1,p2,cost);
			distancesEnlaces[p2][p1] = new Enlace(p2,p1,cost);
			
			
		} catch (Exception e) {
			if(e.getMessage().length() == 0)
				System.err.println("O Arquivo não está no formato pré-estabelecido");
			else 
				System.err.println(e.getMessage());
		}
	}

	private int indexDevice(String device) {
		for(int i = 0; i<devices.length; i++)
			if(device.equals(devices[i]))
				return i;
		return -1;
	}
	
	@Override
	public String toString() {
		String devicesTxt = "[";
		String adjacenciasTxt = "";
		String distancesTxt = "";
		

		for(String device : devices)
			devicesTxt += device + ",";
		devicesTxt+="]";
		
		for(boolean[] adjacenciaLine : adjacencias) {
			adjacenciasTxt+="\n[";
			for(boolean adjacencia : adjacenciaLine)
				adjacenciasTxt += adjacencia + ",";
			adjacenciasTxt+="],";
		}
		adjacenciasTxt+="";

		for(int[] distanceLine : distances) {
			distancesTxt+="\n[";
			for(int distance : distanceLine)
				distancesTxt += distance + ",";
			distancesTxt+="],";
		}
		distancesTxt+="";
		
		return "\nDevices:"+devicesTxt+"\nAdjacencia:"+adjacenciasTxt+"\nDistances:"+distancesTxt;
	}

	public void algorithmDijkstra() {
		// A fase de "Inicialização"(custos iniciais) já está dentro de addLink
		showRehearseTable("Fase de Inicialização");
		
		int lowerCost = -1;
		int posLowerCost = 0;
		
		//Por ENQUANTO o valor 0, onde 0 será mudado para a posição do DEVICE ATUAL;
		for(int i = 0; i < distances[0].length; i++) {
			if(distances[0][i] < lowerCost && distances[0][i] !=-1) {
				lowerCost = distances[0][i];
				posLowerCost = i;
			}
		}
		
		//Menores caminhos de posLowerCost
		
		
		//Exibe iteração depois
		for(int i = 0 ; i < devices.length; i++) {
			showRehearseTable("Fase de Iteração " + i);
		}
	}
	
	private void algorithmDijkstraIteration () {
	}
	
	private void showRehearseTable (String stage) {
		System.out.println("\n"+stage);
		String str = "";
		
		for(int i = 0 ; i < devices.length; i++) {
			str += devices[i] + "\t|";
		}
		
	}
}