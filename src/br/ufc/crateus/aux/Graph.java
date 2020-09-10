package br.ufc.crateus.aux;

import java.util.LinkedList;
import java.util.List;

public class Graph {
	private String[] devices;
	private boolean[][] adjacencias;
	private Enlace[][] distancesEnlaces;
	
	public Graph(String[] devices) {
		int length = devices.length;
		this.devices = devices;
		this.adjacencias = new boolean[length][length];
		this.distancesEnlaces = new Enlace[length][length];

		for(int i = 0; i < length; i++) {
			this.adjacencias[i][i] = true;
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
	
	
	public void algorithmDijkstra() {		
		showRehearseTable("Fase de Inicialização"); // A fase de "Inicialização"(custos iniciais) já está dentro de addLink
		
		int iteration = 0;
		for(int i = 0 ; i < devices.length; i++) {			
			iterationAlgorithmDijkstra(i, iteration);
		}
		
	}
	
	private void iterationAlgorithmDijkstra (int posDev, int iteration) {
		int pos = posDev;
		int index =  getIndexLowerCostNeighbor(pos);
		
		//Analise dos caminhos do vizinho de menor custo
		addAnalyzeNeighbor(posDev, index, iteration);
	}
	
	private void addAnalyzeNeighbor(int posDev, int posNeig, int iteration) {
		List<Integer> newNeighbors = new LinkedList<Integer>();
		
		for(int i = 0 ; i < distancesEnlaces[posNeig].length; i++) {
			if(distancesEnlaces[posNeig][i] != null) { 
				if(distancesEnlaces[posDev][i] == null) {
					int cost = distancesEnlaces[posNeig][i].getCost();
					distancesEnlaces[posDev][i] = new Enlace(posNeig, i, cost);
					newNeighbors.add(i);
				}
				else 
					if(distancesEnlaces[posNeig][i].getCost() < distancesEnlaces[posDev][i].getCost()) 
						distancesEnlaces[posDev][i].setCost(distancesEnlaces[posNeig][i].getCost());
			}
			iteration++;
			showRehearseTable("Fase de Iteração " + iteration);
			
			for(int newNeig : newNeighbors) {
				System.out.println("Iteração:"+iteration);
				addAnalyzeNeighbor(posDev, newNeig, iteration);
			}
			
		}
	}
	
	private int getIndexLowerCostNeighbor(int posDeviceCurrent) {
		int lowerCost = -1;
		int posLowerCost = 0;
		
		for(int i = 0; i < distancesEnlaces[0].length; i++) {
			int costCurrent = distancesEnlaces[0][i].getCost();
			if( (i!=posDeviceCurrent && costCurrent < lowerCost && costCurrent !=-1) || (lowerCost==-1)) {
				lowerCost = costCurrent;
				posLowerCost = i;
			}
		}
		return posLowerCost;
	}
		
	private void showRehearseTable (String stage) {
		System.out.println("\n"+stage);
		String str = "";
		
		for(int i = 0 ; i < devices.length; i++) 
			str += devices[i] + ":\t  | ";
		System.out.print(str);
		
		str = "";
		for(int i = 0 ; i < devices.length; i++) {
			for(int j = 0 ; j < devices.length; j++) {
				str += devices[i] + ",("+devices[distancesEnlaces[i][j].getPosOrigin()]+","+devices[distancesEnlaces[i][j].getPosDestiny()]+"),"+distancesEnlaces[i][j].getCost()+" | ";
			}
			str +="\n";
		}
		System.out.println(str);
		
	}

	@Override
	public String toString() {
		String devicesTxt = "[";
		String adjacenciasTxt = "";
		

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

		return "\nDevices:"+devicesTxt+"\nAdjacencia:"+adjacenciasTxt;
	}
	
}