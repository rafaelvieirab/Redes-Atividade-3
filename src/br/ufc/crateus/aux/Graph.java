package br.ufc.crateus.aux;

import java.util.LinkedList;
import java.util.List;

public class Graph {
	private String[] devices;
	private boolean[][] adjacencias;
	private Enlace[][] tabelaDeRepasse;
	
	public Graph(String[] devices) {
		int length = devices.length;
		this.devices = devices;
		this.adjacencias = new boolean[length][length];
		this.tabelaDeRepasse = new Enlace[length][length];

		for(int i = 0; i < length; i++) {
			this.adjacencias[i][i] = true;
			this.tabelaDeRepasse[i][i] = new Enlace(i,i,0);
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
			tabelaDeRepasse[p1][p2] = new Enlace(p1,p2,cost);
			tabelaDeRepasse[p2][p1] = new Enlace(p2,p1,cost);
			
			
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
		showTabelaDeRepasse("Fase de Inicialização"); 
		
		int iteration = 0;
		for(int i = 0 ; i < devices.length; i++) {	
			iterationAlgorithmDijkstra(i, iteration);
		}
		
	}
	
	private void iterationAlgorithmDijkstra (int posDevCurr, int iteration) {
		int posNeighborLowest =  getIndexLowerCostNeighbor(posDevCurr);
		addAnalyzeNeighbor(posDevCurr, posNeighborLowest, iteration);
	}

	private int getIndexLowerCostNeighbor(int posDevCurr) {
		int lowerCost = -1;
		int posLowerCost = -1;
		
		for(int i = 0; i < tabelaDeRepasse[posDevCurr].length; i++) {
			if(tabelaDeRepasse[posDevCurr][i] == null || posDevCurr == i)
				continue;
			int costCurrent = tabelaDeRepasse[posDevCurr][i].getCost();
			if(costCurrent < lowerCost || lowerCost==-1) {
				lowerCost = costCurrent;
				posLowerCost = i;
			}
		}
		return posLowerCost;
	}
	
	private void addAnalyzeNeighbor(int posDev, int posNeig, int iteration) {
		List<Integer> newNeighbors = new LinkedList<Integer>();
		
		for(int i = 0 ; i < tabelaDeRepasse[posNeig].length; i++) {
			
			if(tabelaDeRepasse[posNeig][i] == null) 
				continue;
			
			if(tabelaDeRepasse[posDev][i] == null) {
				int costSum = tabelaDeRepasse[posDev][posNeig].getCost()+tabelaDeRepasse[posNeig][i].getCost();
				tabelaDeRepasse[posDev][i] = new Enlace(posDev, posNeig, costSum);
				tabelaDeRepasse[i][posDev] = new Enlace(posNeig, posDev, costSum);
				newNeighbors.add(i);
			}
			else {
				if(tabelaDeRepasse[posNeig][i].getCost()+tabelaDeRepasse[posDev][posNeig].getCost() < tabelaDeRepasse[posDev][i].getCost()) { 
					int costSum = tabelaDeRepasse[posDev][posNeig].getCost()+tabelaDeRepasse[posNeig][i].getCost();
					tabelaDeRepasse[posDev][i] = new Enlace(posDev,posNeig,costSum);
					tabelaDeRepasse[i][posDev] = new Enlace(posNeig, posDev, costSum);
				}
			}
		}
		iteration++;
		showTabelaDeRepasse("Fase de Iteração " + iteration);
		//errado
		for(int newNeig : newNeighbors) {
			//addAnalyzeNeighbor(posDev, posNeig, iteration);
			addAnalyzeNeighbor(posDev, newNeig, iteration);
		}
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
		
		for(Enlace[] enlaceLine : tabelaDeRepasse) {
			distancesTxt+="\n[";
			for(Enlace enlace : enlaceLine) {
				distancesTxt += ( enlace != null ? enlace.toString() : "-1") + ",";
			}
			distancesTxt+="],";
		}
		distancesTxt+="";

		return "\nDevices:"+devicesTxt+
				"\nAdjacencia:"+adjacenciasTxt+
				"\nEnlaces:"+distancesTxt;
	}
	
}