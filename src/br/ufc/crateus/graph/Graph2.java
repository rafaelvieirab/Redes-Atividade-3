package br.ufc.crateus.graph;

public class Graph2 {
	private String[] devices;
	private int[][] adjacencias;
	
	public Graph2(String[] devices) {
		this.devices = devices;
		this.adjacencias = new int[devices.length][devices.length];
		
		for(int i = 0; i < devices.length; i++) 
			for(int j = 0; j < devices.length; j++) 
				this.adjacencias[i][j] = -1;
		for(int i = 0; i < devices.length; i++) 
			this.adjacencias[i][i] = 0;
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
	
	@Override
	public String toString() {
		String devicesTxt = "[";
		String adjacenciasTxt = "";

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
		
		return "\nDevices:"+devicesTxt+
				"\nAdjacencia:"+adjacenciasTxt;
	}
	
}
