package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import core.Writer;

@SuppressWarnings("unused") //Warnings because Writer is not used, it only creates files
public class FilesCreator {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {

    	boolean correctL= false;
    	int L= 0;
    	while (!correctL) {    		
    		System.out.println("Insert L (> 10)");
    		BufferedReader readerL = new BufferedReader(new InputStreamReader(System.in));
    		String auxL = readerL.readLine();
    		L= Integer.valueOf(auxL);
    		correctL= (L > 10) ? true : false;
    	}
  
		System.out.println("Insert option: 2D/3D (default 2D)");
		BufferedReader readerDim = new BufferedReader(new InputStreamReader(System.in));
		String auxDim = readerDim.readLine();
		
		int dim= (auxDim.contains("3")) ? 3 : 2;


    	System.out.println("L: " + L + " dimension: " + dim + "D");
    	
    	Writer writerStatic = new Writer(L, dim, "static");
		Writer writerDynamic = new Writer(L, dim, "dynamic");

    }

}
