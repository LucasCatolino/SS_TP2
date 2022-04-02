package core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


import javafx.geometry.Point3D;

public class CellularAutomaton {
	
    private int size = 100;
    private Space space;
    private static final int MAX_ITERATIONS = 1000;
    Scanner dynamicScanner;
    
    private String dynamicFile;
    private boolean treeD;

    public CellularAutomaton(String staticFile, String dynamicFileInput){

    	this.dynamicFile= dynamicFileInput.concat(".xyz");

        InputStream dynamicStream = CellularAutomaton.class.getClassLoader().getResourceAsStream(dynamicFileInput + ".txt");
        assert dynamicStream != null;
        InputStream staticStream = CellularAutomaton.class.getClassLoader().getResourceAsStream(staticFile + ".txt");
        assert staticStream != null;

        dynamicScanner = new Scanner(dynamicStream);
        Scanner staticScanner = new Scanner(staticStream);

        size = Integer.parseInt(staticScanner.next()); //Tam del tablero
        
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

    	int centerXY= size/2;
        int centerZ= (treeD) ? (size/2) : 0;
        Point3D center = new Point3D(centerXY, centerXY ,centerZ);
        double maxDist= Math.sqrt(Math.pow(size/2, 2) + Math.pow(size/2, 2));
    	
        dynamicScanner = new Scanner(dynamicFile);
        InputStream dynamicStream = CellularAutomaton.class.getClassLoader().getResourceAsStream(dynamicFile);
        assert dynamicStream != null;
        
        dynamicScanner = new Scanner(dynamicStream);

        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        
	    String filename= ("./resources/animation_" + date + dynamicFile);//
	    String filenameLinechart= ("./resources/linechart_").concat("_" + date + ".txt");
	    FileWriter fw = new FileWriter(filename,true);//
	    FileWriter fwLC = new FileWriter(filenameLinechart,true);
	    
	    boolean stopX= false;
	    boolean stopY= false;
	    boolean stopZ= false;
        
    	fwLC.write("t" + "\tParticles\n");
        while (dynamicScanner.hasNext() && !stopX && !stopY && !stopZ) {
        	int initialParticles= Integer.parseInt(dynamicScanner.next()); //First line initial particles
        	String time= dynamicScanner.next(); //time line
        	fwLC.write("" + time + "\t" + initialParticles + "\n");
        	
        	fw.write(treeD ? (initialParticles + 8) + "\n" : (initialParticles + 4) + "\n");//
    	    fw.write(time + "\n");//
    	    
    	    for (int i = 0; i < initialParticles; i++) {
    	    	int x= Integer.parseInt(dynamicScanner.next());
            	int y= Integer.parseInt(dynamicScanner.next());
            	int z= Integer.parseInt(dynamicScanner.next());
            	
    			Point3D auxPoint= new Point3D(x,  y, z);
            	
            	fw.write("" + x + "\t" + "" + y + "\t" + "" + z + "\t" + (1 - auxPoint.distance(center)/maxDist) + "\t" + (auxPoint.distance(center)/maxDist) + "\t" + (auxPoint.distance(center)/maxDist) + "\t0\n");//
            	
            	stopX= (stopX) ? true : margin(x);
            	stopY= (stopY) ? true : margin(y);
            	stopZ= (stopZ) ? true : ((treeD) ? margin(z) : false); //In 2D does not make sense
    	    }
    	    
    	    //Escribo las particulas de borde//
    	    
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
        fwLC.close();
        dynamicStream.close();
    }

	private boolean margin(int pos) {
		if (pos == 1 || pos == (size)) {
			return true;
		}
		return false;
	}

	static public void main(String[] args) {
		CellularAutomaton cell= new CellularAutomaton("static","dynamic");
		System.out.println("Creating simulation");
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
		System.out.println("Simulation done");
		boolean keepGoing= false;
		Scanner scan = new Scanner(System.in);

		while (!keepGoing) {    		
    		System.out.println("Press enter to create visualization files");
    		scan.nextLine();
    		keepGoing= true;
    	}
		
		cell.getAnimationFile();
		
		System.out.println("End");
    }

}

/**/
//
	//Regla 1: Juego de la vida (2D y 3D)
/*
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
*/
	//2D
	//Regla 2: muerta y mas de 4 vive, viva y menos de 5 vive
/*
	@Override
	public int apply(Cell new_cell,Boolean isAlive, int numLiveCells) {
		if(!isAlive && numLiveCells > 4){
			new_cell.revive();
			return 1;
		}
		if(isAlive && numLiveCells < 5){
			new_cell.revive();
			return 1;
		}
		return 0;
	}

*/
	//Regla 3: muerta y más de 1 vive, viva y más de 1 vive
/*
	@Override
	public int apply(Cell new_cell,Boolean isAlive, int numLiveCells) {
		if(!isAlive && numLiveCells > 1){
			new_cell.revive();
			return 1;
		}
		if(isAlive && numLiveCells > 1){
			new_cell.revive();
			return 1;
		}
		return 0;
	}
*/
	//3D
	//Regla 2: muerta y mas de 6 vive, viva y menos de 16 vive 
/*
	@Override
	public int apply(Cell new_cell,Boolean isAlive, int numLiveCells) {
		if(!isAlive && numLiveCells > 6){
			new_cell.revive();
			return 1;
		}
		if(isAlive && numLiveCells < 16){
			new_cell.revive();
			return 1;
		}
		return 0;
	}
*/
	//Regla 3: muerta y mas de 6 vive, viva y mas de 12 vive
/*
	@Override
	public int apply(Cell new_cell,Boolean isAlive, int numLiveCells) {
		if(!isAlive && numLiveCells > 6){
			new_cell.revive();
			return 1;
		}
		if(isAlive && numLiveCells > 12){
			new_cell.revive();
			return 1;
		}
		return 0;
	}
*/
//
/**/