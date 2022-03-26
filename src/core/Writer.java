package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	
	private static final int RANGE= 5;
		
    public Writer(int L, int dim, int max, String type) {
        try {
            File file = new File("./resources/" + type + ".txt");
            FileWriter myWriter = new FileWriter("./resources/" + type + ".txt");
            try {
            	if (type.compareTo("static") == 0) {
					this.staticFile(L, dim, myWriter);
				} else {
					this.randomizePositions(L, dim, max, myWriter);
				}
			} catch (Exception e) {
				System.err.println("IOException");
			}
            myWriter.close();
            System.out.println("Successfully wrote to the file ./resources/" + type + ".txt");
        } catch (IOException e) {
            System.out.println("IOException ocurred");
            e.printStackTrace();
        }
    }

	private void randomizePositions(int l, int dim, int startingMax, FileWriter myWriter) throws IOException {
		int middle= (int) Math.floor(l/2);
		int max= (dim == 2) ? startingMax : startingMax * 10;
		for (int i = 0; i < max; i++) {
			int x= (int) Math.floor(Math.random() * 2 * RANGE) + (middle - RANGE);
			int y= (int) Math.floor(Math.random() * 2 * RANGE) + (middle - RANGE);
			int z= (dim == 2) ? 0 :  (int) Math.floor(Math.random() * 2 * RANGE) + (middle - RANGE);

			myWriter.write("" + x + "\t" + "" + y + "\t" + "" + z + "\n");
		}
		
	}

	private void staticFile(int l, int dim, FileWriter myWriter) throws IOException {
		myWriter.write("" + l + "\n");
		myWriter.write("" + dim + "D\n");
	}
}