package core;


public class Cell {

	private boolean alive;
	private final int distance;
	
	public Cell(boolean state, int dist) {
		this.alive= state;
		this.distance= dist;
	}

	public boolean isAlive() {
		return alive;
	}

	public void revive() {
		alive= true;
	}

	public void kill() {
		alive= false;
	}

	public int getDistance() {
		return distance;
	}

}
