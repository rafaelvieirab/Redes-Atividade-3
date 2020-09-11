package br.ufc.crateus.graph;

public class Enlace {
	private int posOrigin;
	private int posDestiny;
	private int cost;
	
	public Enlace(int posOrigin, int posDestiny, int cost) {
		this.posOrigin = posOrigin;
		this.posDestiny = posDestiny;
		this.cost = cost;
	}
	
	public int getPosOrigin() {
		return posOrigin;
	}

	public int getPosDestiny() {
		return posDestiny;
	}

	public int getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return "("+posOrigin+","+posDestiny+"),"+cost+" ";
	}
}

