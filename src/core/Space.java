package core;

import javafx.geometry.Point3D;

import java.util.Iterator;
import java.util.List;

public class Space implements Iterator<Cell>{
    private static final int  centerX = 50, centerY = 50, centerZ = 50;
    private final Node[][][] space;
    private final int size;
    private final boolean treeD; //3D
    private final Point3D center;

    public Space(int size, boolean treeD ) {
        this.treeD = treeD;
        this.size = size;
        if (treeD){
            space = new Node[size][size][size];
        }else{
            space = new Node[size][size][0];
        }
        center = new Point3D(centerX, centerY ,centerZ);
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


    public int getNumLiveNeighborsCell(int x,int y,  int z){
        int toRet = 0;



        if(x>0){ //creo que faltan
            toRet += space[x-1][y][z].isAlive() ? 1:0;           // x-1 y z
            if(y>0){
                toRet += space[x-1][y-1][z].isAlive() ? 1:0;     // x-1 y-1 z
                if(z >0){
                    toRet += space[x-1][y-1][z-1].isAlive() ? 1:0;  // x-1 y-1 z-1
                }
                if(z < size-1 && treeD){
                    toRet += space[x-1][y-1][z+1].isAlive() ? 1:0;   // x-1 y-1 z+1
                }
            }
            if(y < size-1){
                toRet += space[x-1][y+1][z].isAlive() ? 1:0;    // x-1 y+1 z
                if(z >0){
                    toRet += space[x-1][y+1][z-1].isAlive() ? 1:0;  // x-1 y+1 z-1
                }
                if(z < size-1 && treeD){
                    toRet += space[x-1][y+1][z+1].isAlive() ? 1:0;   // x-1 y+1 z+1
                }
            }


            if(z > 0){
                toRet += space[x-1][y][z-1].isAlive() ? 1:0;    // x-1 y z-1
            }
            if(z < size-1 && treeD){
                toRet += space[x-1][y][z+1].isAlive() ? 1:0;   // x-1 y z+1
            }
        }

        if(y>0){
            toRet += space[x][y-1][z].isAlive() ? 1:0;       // x y-1 z
            if(x < size-1){
                toRet += space[x+1][y-1][z].isAlive() ? 1:0;    // x+1 y-1 z
                if(z > 0){
                    toRet += space[x+1][y-1][z-1].isAlive() ? 1:0;    // x+1 y-1 z-1
                }
                if(z < size-1 && treeD){
                    toRet += space[x+1][y-1][z+1].isAlive() ? 1:0;   // x+1 y-1 z+1
                }
            }
            if(z > 0){
                toRet += space[x][y-1][z-1].isAlive() ? 1:0;    // x y-1 z-1
            }
            if(z < size-1 && treeD){
                toRet += space[x][y-1][z+1].isAlive() ? 1:0;   // x y-1 z+1
            }
        }

        if(z>0){
            toRet += space[x][y][z-1].isAlive() ? 1:0; // x y z-1
            if(y < size-1){
                toRet += space[x][y+1][z-1].isAlive() ? 1:0; // x y+1 z-1
            }
            if(x<size-1){
                toRet += space[x+1][y][z-1].isAlive() ? 1:0; // x+1 y z-1
                if(y < size-1){
                    toRet += space[x+1][y+1][z-1].isAlive() ? 1:0; //x+1 y+1 z-1
                }
            }

        }
        //TODO:hacer
        return 0;
    }

    private double getDistOrigin(int x, int y, int z){
        //TODO:hacer
        //dada una posicion devulve la posicion al origen
        return center.distance( x, y, z);
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


    public boolean gameOver(){
        boolean allAreDead = true,
                spaceDidntChange = false; //lo pongo en false pero tiene que ser true. habría que comparar tn con tn+1 y si difieren entonces apagar el flag
        if(treeD) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        if (space[i][j][k].isAlive()) {
                            allAreDead = false;
                            if ((k == 0 || j == 0 || i == 0 || k == size - 1 || j == size - 1 || i == size - 1)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (space[i][j][0].isAlive()) {
                        allAreDead = false;
                        if ((j == 0 || i == 0 || j == size - 1 || i == size - 1)) {
                            return true;
                        }
                    }
                }
            }
        }
        return allAreDead || spaceDidntChange;
    }


}