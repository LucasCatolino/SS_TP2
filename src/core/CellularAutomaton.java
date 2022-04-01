package core;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.sun.tools.javac.jvm.StringConcat;

import javafx.geometry.Point3D;

public class CellularAutomaton {
	
    private int size = 100;
    private Space space;
    private static final int MAX_ITERATIONS = 1000;
    Scanner dynamicScanner;
    
    private String dynamicFile;
    private boolean treeD;
    //private double maxDist=50;


    public CellularAutomaton(String staticFile, String dynamicFileInput){

    	this.dynamicFile= dynamicFileInput.concat(".xyz");
    	System.out.println(dynamicFile);
        InputStream dynamicStream = CellularAutomaton.class.getClassLoader().getResourceAsStream(dynamicFileInput + ".txt");
        assert dynamicStream != null;
        InputStream staticStream = CellularAutomaton.class.getClassLoader().getResourceAsStream(staticFile + ".txt");
        assert staticStream != null;

        dynamicScanner = new Scanner(dynamicStream);
        Scanner staticScanner = new Scanner(staticStream);

        size = Integer.parseInt(staticScanner.next()); //Tam del tablero
        //maxDist= Math.sqrt(Math.pow(size/2, 2) + Math.pow(size/2, 2));
        
        String dim = staticScanner.next(); //Dim del tablero
        staticScanner.close();

        treeD = dim.equals("3D");
        //lee del static el size y si el 3D o no
        space = new Space(size, treeD);
        
        int initialParticles= Integer.parseInt(dynamicScanner.next()); //First line initial particles

        dynamicScanner.next(); //Skip comments line
        
        int i= 0;
        while (dynamicScanner.hasNext() && i < initialParticles) {
        	int x= Integer.parseInt(dynamicScanner.next());
        	int y= Integer.parseInt(dynamicScanner.next());
        	int z= Integer.parseInt(dynamicScanner.next());

        	space.add(x, y, z);
        	i++;
        }
        dynamicScanner.close();
    }

    public void solve(Rules rules){
    	int t=1;
        while(t< MAX_ITERATIONS && space.cellAmmo != 0) {
            space = space.update(rules);
            writeOutput(space,t);
            t++;
        }
    }
    
    public void getAnimationFile() {
        try {
			writeAnimationFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    private void writeOutput(Space liveCell, int t){
    	try {
    	    String filename= "./resources/" + dynamicFile;
    	    FileWriter fw = new FileWriter(filename,true);
    	    fw.write(space.cellAmmo + "\n");
    	    fw.write("t=" + Integer.toString(t) + "\n");
    	    //
    	    for(int i=0;i<size; i++){
    			for(int j=0; j<size; j++){
    				if(space.isTreeD()){
    					for(int k=0; k<size; k++){
    						if(space.getSpace()[i][j][k].isAlive()) {
    							fw.write(i + "\t" + j + "\t" + k + "\n");
    						}
    					}
    				} else {
    					if(space.getSpace()[i][j][0].isAlive()){
    						fw.write(i + "\t" + j + "\t0" + "\n");
    					}
    				}
    			}
    		}
    	    
    	    fw.close();
    	}
    	catch(IOException ioe) {
    	    System.err.println("IOException: " + ioe.getMessage());
    	}
    }
    
    private void writeAnimationFile() throws IOException{
    	//abro el dynamic
    	//leo los tokens y voy escribiendo
    		//cantidad
    		//tiempo
    		//x y z r g b t
    	//cierro el dynamic
    	
    	int centerXY= size/2;
        int centerZ= (treeD) ? (size/2) : 0;
        Point3D center = new Point3D(centerXY, centerXY ,centerZ);
        double maxDist= Math.sqrt(Math.pow(size/2, 2) + Math.pow(size/2, 2));
    	
        dynamicScanner = new Scanner(dynamicFile);
        InputStream dynamicStream = CellularAutomaton.class.getClassLoader().getResourceAsStream(dynamicFile);
        assert dynamicStream != null;
        
        dynamicScanner = new Scanner(dynamicStream);

        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        
	    String filename= ("./resources/animation_" + dynamicFile).concat("_" + date);
	    FileWriter fw = new FileWriter(filename,true);
	    
	    boolean stopX= false;
	    boolean stopY= false;
	    boolean stopZ= false;
        
        while (dynamicScanner.hasNext() && !stopX && !stopY && !stopZ) {
        	int initialParticles= Integer.parseInt(dynamicScanner.next()); //First line initial particles
    	    fw.write(treeD ? (initialParticles + 8) + "\n" : (initialParticles + 4) + "\n");
    	    
    	    String time= dynamicScanner.next(); //time line
    	    fw.write(time + "\n");
    	    
    	    for (int i = 0; i < initialParticles; i++) {
    	    	int x= Integer.parseInt(dynamicScanner.next());
            	int y= Integer.parseInt(dynamicScanner.next());
            	int z= Integer.parseInt(dynamicScanner.next());
            	
    			Point3D auxPoint= new Point3D(x,  y, z);
            	
            	fw.write("" + x + "\t" + "" + y + "\t" + "" + z + "\t" + (1 - auxPoint.distance(center)/maxDist) + "\t" + (auxPoint.distance(center)/maxDist) + "\t" + (auxPoint.distance(center)/maxDist) + "\t0\n");
            	
            	stopX= (stopX) ? true : margin(x);
            	stopY= (stopY) ? true : margin(y);
            	stopZ= (stopZ) ? true : ((treeD) ? margin(z) : false); //In 2D does not make sense
    	    }
    	    
    	    //Escribo las particulas de borde
    	    if(space.isTreeD()){
    			fw.write("0\t0\t0\t0\t0\t0\t100\n" +
    					size + "\t" + size + "\t" + size + "\t0\t0\t0\t100\n" +
    					"0\t0\t" + size + "\t0\t0\t0\t100\n" +
    					size + "\t0\t0\t0\t0\t0\t100\n" +
    					"0\t" + size + "\t0\t0\t0\t0\t100\n" +
    					size + "\t" + size + "\t0\t0\t0\t0\t100\n" +
    					size + "\t0 " + size + "\t0\t0\t0\t100\n" +
    					"0\t" + size + "\t" + size + "\t0\t0\t0\t100\n");
    		} else {
    			fw.write("0\t0\t0\t0\t0\t0\t100\n" +
    					size + "\t" + size + "\t0\t0\t0\t0\t100\n" +
    					"0\t" + size + "\t0\t0\t0\t0\t100\n" +
    					size + "\t0\t0\t0\t0\t0\t100\n");
    		}
		}
        
        fw.close();
        dynamicStream.close();
    }
/*
        //dynamicScanner.next(); //Skip comments line
        
        int i= 0;
        while (dynamicScanner.hasNext() && i < initialParticles) {
        	int x= Integer.parseInt(dynamicScanner.next());
        	int y= Integer.parseInt(dynamicScanner.next());
        	int z= Integer.parseInt(dynamicScanner.next());

        	space.add(x, y, z);
        	i++;
        }



    	
    	
    	try {
    	    String filename= "./resources/" + dynamicFile;
    	    FileWriter fw = new FileWriter(filename,true);
    	    fw.write(treeD ? (space.cellAmmo + 8) + "\n" : (space.cellAmmo + 4) + "\n");
    	    //fw.write("t=" + Integer.toString(t) + "\n");
    	    //
    	    for(int i=0;i<size; i++){
    			for(int j=0; j<size; j++){
    				if(space.isTreeD()){
    					for(int k=0; k<size; k++){
    						Cell auxCell= space.getSpace()[i][j][k];
    						if(auxCell.isAlive()) {
    							fw.write(i + "\t" + j + "\t" + k + "\t" + ((1 - auxCell.getDistance()/maxDist)) + "\t" + auxCell.getDistance()/maxDist + "\t" + auxCell.getDistance()/maxDist + "\t0" + "\n"); //TODO: RGB y t harcodeados, cambiar
    						}
    					}
    				} else {
						Cell auxCell= space.getSpace()[i][j][0];
    					if(space.getSpace()[i][j][0].isAlive()){
    						fw.write(i + "\t" + j + "\t0" + "\t" + (1 - auxCell.getDistance()/maxDist)+ "\t" + auxCell.getDistance()/maxDist + "\t" + auxCell.getDistance()/maxDist + "\t0" + "\n"); //TODO: RGB y t harcodeados, cambiar
    					}
    				}
    			}
    		}

    	    //Escribo las particulas de borde
    	    if(space.isTreeD()){
    			fw.write("0\t0\t0\t0\t0\t0\t100\n" +
    					size + "\t" + size + "\t" + size + "\t0\t0\t0\t100\n" +
    					"0\t0\t" + size + "\t0\t0\t0\t100\n" +
    					size + "\t0\t0\t0\t0\t0\t100\n" +
    					"0\t" + size + "\t0\t0\t0\t0\t100\n" +
    					size + "\t" + size + "\t0\t0\t0\t0\t100\n" +
    					size + "\t0 " + size + "\t0\t0\t0\t100\n" +
    					"0\t" + size + "\t" + size + "\t0\t0\t0\t100\n");
    		}else {
    			fw.write("0\t0\t0\t0\t0\t0\t100\n" +
    					size + "\t" + size + "\t0\t0\t0\t0\t100\n" +
    					"0\t" + size + "\t0\t0\t0\t0\t100\n" +
    					size + "\t0\t0\t0\t0\t0\t100\n");
    		}
    	    
    	    fw.close();
    	}
    	catch(IOException ioe) {
    	    System.err.println("IOException: " + ioe.getMessage());
    	}
    }
*/

	private boolean margin(int pos) {
		if (pos == 1 || pos == (size)) {
			return true;
		}
		return false;
	}

	static public void main(String[] args) {
		CellularAutomaton cell= new CellularAutomaton("static","dynamic");
		cell.solve(new Rules() {
			@Override
			public int apply(Cell new_cell,Boolean isAlive, int numLiveCells) {
				if(numLiveCells == 3 && !isAlive ){
					new_cell.revive();
					return 1;
				}
				if(isAlive && numLiveCells == 2){
					new_cell.revive();
					return 1;
				}
				if(isAlive && numLiveCells ==3){
					new_cell.revive();
					return 1;
				}
				return 0;

			}
		});

		boolean keepGoing= false;
		Scanner scan = new Scanner(System.in);

		while (!keepGoing) {    		
    		System.out.println("Insert any key to create animated file");
    		scan.nextLine();
    		keepGoing= true;
    	}
		
		cell.getAnimationFile();
		System.out.println("End");
    }
}