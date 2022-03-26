package core;

public class Cell{
    private final Point pnt;
    private final Node node;
    private final int distance;


    public Cell(Node node, Point pnt, int distance) {
        this.node= node;
        this.pnt = pnt;
        this.distance = distance;
    }

    public Point getPnt() {
        return pnt;
    }
    public boolean isAlive() {
        return node.isAlive();
    }

    public int getDistance() {
        return distance;
    }

    public Node getNode() {
        return node;
    }
}
