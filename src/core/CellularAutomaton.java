package core;

import java.util.ArrayList;
import java.util.List;

public class CellularAutomaton {
    private int totalCell;
    private Space space;

    public CellularAutomaton(String staticFile, String dynamicFile){
        //TODO:hacer
        //lee del static el size y si el 3D o no
        //space = new Space(size, true);

        //TODO:hacer
        //a mediada que lee el dynamic llena el spacio
        space.add(10, 1, 1);
    }

    public void solve(Rules rules){
        int t=0;
        while (check()){
            List<Cell> liveCell = new ArrayList<>(); //donde se van guardando las celdas vivas
            while (space.hasNext()){
                Cell currentCell = space.next();
                int numLiveNeighCells = space.getNumLiveNeighborsCell(currentCell.getPnt());
                if(rules.apply(currentCell, numLiveNeighCells)){
                   liveCell.add(currentCell);
                }
            }
            space.update(liveCell);
            writeOutput(liveCell, t);
            t++;
        }
    }

    private boolean check(){
        //TODO:hacer
        //chequea si se puede seguir con el algoritmo
        //-si no hay celdas vivas terminas
        //si hace varios ciclos no hay celldas nuevas termina
        //si una celda borde vive se termina
        return true;
    }


    private void writeOutput(List<Cell> liveCell, int t){
        //TODO:hacer
        //escrive todas las al final del archivo
    }



}