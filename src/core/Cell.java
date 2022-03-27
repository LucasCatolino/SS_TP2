package core;

public class Cell{
    private final int distance;
    private boolean alive;


    public Cell(Boolean alive, int distance) {
        this.alive = alive;
        this.distance = distance;
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

    public void ChangeStatus(){
        alive = !alive;
    }

    public int getDistance() {
        return distance;
    }

}
