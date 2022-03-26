package core;

import java.util.Iterator;
import java.util.List;

public class Space implements Iterator<Cell>{

    private final Node[][][] space;
    private final int size;
    private final boolean treeD; //3D

    public Space(int size, boolean treeD ) {
        this.treeD = treeD;
        this.size = size;
        if (treeD){
            space = new Node[size][size][size];
        }else{
            space = new Node[size][size][0];
        }
        //TODO: creamos todo los nodos y los dejamos muertos
    }

    public void add(int x, int y, int z){
        space[x][y][z].revive();
    }

    public void update(List<Cell> liveCell) {
        //TODO:hacer
        //primero matamos todos
        //itera sobre la lista reviveindo las celdas que estan en la lista
    }


    public int getNumLiveNeighborsCell(Point pnt){
        //TODO:hacer
        return 0;
    }

    private int getDistOrigin(int x, int y, int z){
        //TODO:hacer
        //dada una posicion devulve la posicion al origen
        return 0;
    }


    @Override
    public boolean hasNext() {
        //TODO:hacer
        return false;
    }

    @Override
    public Cell next() {
        //TODO:hacer
        return null;
    }


}