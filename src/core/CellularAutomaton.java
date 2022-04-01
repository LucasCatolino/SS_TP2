package core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CellularAutomaton {
    private int totalCell;
    private int size = 100;
    private Space space;
    private static final int MAX_ITERATIONS = 50;
    Scanner dynamicScanner;
    
    private String dynamicFile;
    private boolean treeD;



    public CellularAutomaton(String staticFile, String dynamicFile){
        //TODO:hacer
    	this.dynamicFile= dynamicFile;
        InputStream dynamicStream = CellularAutomaton.class.getClassLoader().getResourceAsStream(dynamicFile);
        assert dynamicStream != null;
        InputStream staticStream = CellularAutomaton.class.getClassLoader().getResourceAsStream(staticFile);
        assert staticStream != null;

        dynamicScanner = new Scanner(dynamicStream);
        Scanner staticScanner = new Scanner(staticStream);

        size = Integer.parseInt(staticScanner.next()); //Tam de tablero
        String dim = staticScanner.next(); //
        staticScanner.close();

        treeD = dim.equals("3D");
        //lee del static el size y si el 3D o no
        space = new Space(size, treeD);
        
        int initialParticles= Integer.parseInt(dynamicScanner.next()); //First line initial particles
        initialParticles= (treeD) ? initialParticles - 8 : initialParticles - 4;
    	System.out.println("Cantidad: " + initialParticles);

        dynamicScanner.next(); //Skip comments line
        
        int i= 0;
        while (dynamicScanner.hasNext() && i < initialParticles) {
        	int x= Integer.parseInt(dynamicScanner.next());
        	int y= Integer.parseInt(dynamicScanner.next());
        	int z= Integer.parseInt(dynamicScanner.next());
        	dynamicScanner.next(); //R
        	dynamicScanner.next(); //G
        	dynamicScanner.next(); //B
        	dynamicScanner.next(); //Transparency
        	System.out.println("x: " + x + " y: " + y + " z: " + z);
        	space.add(x, y, z);
        	i++;
        }

        //TODO:hacer
        //a mediada que lee el dynamic llena el spacio1

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
        int t=1;
        //while(check() && t < MAX_ITERATIONS){
        while(t< MAX_ITERATIONS) {
            space = space.update(rules);
            writeOutput(space,t);
            t++;
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
    	try
    	{
    	    String filename= "./resources/" + dynamicFile;
    	    FileWriter fw = new FileWriter(filename,true);
    	    fw.write(treeD ? (space.cellAmmo + 8) + "\n" : (space.cellAmmo + 4) + "\n");
    	    fw.write("t=" + Integer.toString(t) + "\n");
    	    //
    	    for(int i=0;i<size; i++){
    			for(int j=0; j<size; j++){
    				if(space.isTreeD()){
    					for(int k=0; k<size; k++){
    						if(space.getSpace()[i][j][k].isAlive()) {
    							fw.write(i + "\t" + j + "\t" + k + "\t1" + "\t0" + "\t0" + "\t0" + "\n"); //TODO: RGB y t harcodeados, cambiar
    						}
    					}
    				}
    				else{
    					if(space.getSpace()[i][j][0].isAlive()){
    						fw.write(i + "\t" + j + "\t0" + "\t1" + "\t0" + "\t0" + "\t0" + "\n"); //TODO: RGB y t harcodeados, cambiar
    					}
    				}
    			}
    		}

    	    //Escribo las particulas de borde
    	    if(space.isTreeD()){
    			fw.write("0\t0\t0\t0\t0\t0\t0\n" +
    					size + "\t" + size + "\t" + size + "\t0\t0\t0\t100\n" +
    					"0\t0\t" + size + "\t0\t0\t0\t100\n" +
    					size + "\t0\t0\t0\t0\t0\t100\n" +
    					"0\t" + size + "\t0\t0\t0\t0\t100\n" +
    					size + "\t" + size + "\t0\t0\t0\t0\t100\n" +
    					size + "\t0 " + size + "\t0\t0\t0\t100\n" +
    					"0\t" + size + "\t" + size + "\t0\t0\t0\t100\n");
    		}
    		else{
    			fw.write("0\t0\t0\t0\t0\t0\t100\n" +
    					size + "\t" + size + "\t0\t0\t0\t0\t100\n" +
    					"0\t" + size + "\t0\t0\t0\t0\t100\n" +
    					size + "\t0\t0\t0\t0\t0\t100\n");
    		}
    	    
    	    //fw.write("add a line\n");
    	    fw.close();
    	    System.out.println(t);
    	}
    	catch(IOException ioe)
    	{
    	    System.err.println("IOException: " + ioe.getMessage());
    	}
        //TODO:hacer
        //escrive todas las al final del archivo
    }



}