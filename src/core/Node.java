package core;

public class Node {
    private boolean alive;

    public Node(boolean alive) {
        this.alive = alive;
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
}
