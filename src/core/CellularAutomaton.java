package core;

import java.util.ArrayList;
import java.util.List;

public class CellularAutomaton {
    private int totalCell;
    private int size = 100;
    private Space space;
    private static final int MAX_ITERATIONS = 50;

    public CellularAutomaton(String staticFile, String dynamicFile){
        //TODO:hacer
        //lee del static el size y si el 3D o no
        space = new Space(size, true); //el de tn

        //TODO:hacer
        //a mediada que lee el dynamic llena el spacio1
        space.add(10, 1, 1);
    }

    public void solve(Rules rules){
      /*  int t=0;
        while (check()){
            List<Cell> liveCell = new ArrayList<>(); //donde se van guardando las celdas vivas
            while (space1.hasNext()){
                Cell currentCell = space1.next();
                int numLiveNeighCells = space1.getNumLiveNeighborsCell(currentCell.getPnt().getX(),currentCell.getPnt().getY(),currentCell.getPnt().getZ());
                if(rules.apply(currentCell, numLiveNeighCells)){
                   liveCell.add(currentCell);
                }
            }
            space1.update(liveCell);
            writeOutput(liveCell, t);
            t++;
        }*/
        int t=0;{
            while(check() && t < MAX_ITERATIONS){
                space = space.update();
                writeOutput(space,t);
                t++;
            }
        }
    }

    private boolean check(){
        //chequea si se puede seguir con el algoritmo
        //-si no hay celdas vivas terminas
        //si hace varios ciclos no hay celldas nuevas termina
        //si una celda borde vive se termina
        return space.gameOver();
    }


    private void writeOutput(Space liveCell, int t){
        //TODO:hacer
        //escrive todas las al final del archivo
    }



}