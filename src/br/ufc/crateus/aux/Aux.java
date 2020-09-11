package br.ufc.crateus.aux;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Aux {
	private Scanner scan;
	private List<String[]> routes = new LinkedList<String[]>();
	private Graph graph;
	
	public void readFile() {
		String fileName = "src/test.txt";
		readFile(fileName);
	}
	
	public void readFile(String fileName) {
		try {
			if(fileName == "") 
				fileName = "src/test.txt";
			
			scan = new Scanner(new File (fileName));
			graph = extractDevices();
			extractLinks();
			extractRoutes();
			scan.close();
			//System.out.println(graph.toString());
			graph.algorithmDijkstra();
			
		} catch (Exception e) {
			System.err.println("Erro ao ler o arquivo"+fileName);
		}
	}
	
	private Graph extractDevices() {
		String[] devices = splitLineInValue();
		Graph graph = new Graph(devices);
		return graph;
	}
	
	private void extractLinks() {
		try {
			String[] values = splitLineInValue();
			
			for(int i=0; i<values.length; i+=3) {
				String device1 = values[i].substring(1);
				String device2 = values[i+1]; 
				String costStr = values[i+2].substring(0, values[i+2].length()-1);  
				int cost = Integer.parseInt(costStr);

				graph.addLink(device1, device2, cost);
			}

		} catch (Exception e) {
			System.err.println("Formato de arquivo incoerente com o padrão!");
		}
	}
	
	private void extractRoutes() {
		try {
			String[] values = splitLineInValue();

			for(int i=0; i < values.length; i+=2) {
				String origin = values[i].substring(1); 
				String destiny = values[i+1].substring(0, values[i+1].length()-1);  
				
				String[] devices  = new String[2];
				devices[0] = origin;
				devices[1] = destiny;
				routes.add(devices);
			}
		} catch (Exception e) {
			System.err.println("Formato de arquivo incoerente com o padrão!");
		}
	}
		
	private String[] splitLineInValue() {
		String line = scan.nextLine();
		String[] values = null;
		
		try {
			String data = line.split(":")[1];
			values = data.split(",");
		} catch (Exception e) {
			System.err.println("Erro no formato do arquivo");
		}
		return values;
	}
	
}
