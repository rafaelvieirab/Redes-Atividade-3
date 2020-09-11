package br.ufc.crateus.graph;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BuildGraph2 {
	private static Scanner scan;
	
	public static Graph2 readFile() {
		String fileName = "src/test.txt";
		return readFile(fileName);
	}
	
	public static Graph2 readFile(String fileName) {
		Graph2 graph = null;
		try {
			if(fileName == "") 
				fileName = "src/test.txt";
			
			scan = new Scanner(new File(fileName));
			graph = extractDevices();
			extractLinks(graph);
			scan.close();
		} catch (Exception e) {
			System.err.println("Erro ao ler o arquivo"+fileName);
		}
		return graph;
	}
	
	private static Graph2 extractDevices() {
		String[] devices = splitLineInValue();
		return new Graph2(devices);
	}
	
	private static void extractLinks(Graph2 graph) {
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
	
	public static List<String[]> extractRoutes() {
		List<String[]> routes = null;
		if(scan == null)
			return null;
		
		try {
			String[] values = splitLineInValue();
			routes =  new LinkedList<String[]>();
			
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
		return routes;
	}
		
	private static String[] splitLineInValue() {
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
