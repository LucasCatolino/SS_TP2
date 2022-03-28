package tests;

import core.Cell;
import core.CellularAutomaton;
import core.Rules;
import core.Space;

import static org.junit.Assert.*;

class Test {

	@org.junit.jupiter.api.Test
	void test01CreacionDeCelda() {
		Cell cell= new Cell(false, 5);
		assertFalse(cell.isAlive());
	}



	@org.junit.jupiter.api.Test
	void test02CambiosDeEstado() {
		Cell cell= new Cell(false, 5);
		
		assertFalse(cell.isAlive());
		
		cell.revive();
		assertTrue(cell.isAlive());
		
		cell.kill();
		assertFalse(cell.isAlive());
	}
	
	@org.junit.jupiter.api.Test
	void test03DistanciaDeLaCelda() {
		Cell cell= new Cell(false, 5);
		
		assertEquals(5, cell.getDistance());
	}

	@org.junit.jupiter.api.Test
	void test04ConteoDeVecinos() {
		Space space= new Space(100, true);
		space.add(5,5,5);
		space.add(4,4,4);
		space.add(4,4,5);
		space.add(4,4,6);
		space.add(4,5,4);
		space.add(4,5,5);
		space.add(4,5,6);
		space.add(4,6,4);
		space.add(4,6,5);
		space.add(4,6,6);
		space.add(5,4,4);
		space.add(5,4,5);
		space.add(5,4,6);
		space.add(5,5,4);
		//space.add(5,5,5);
		space.add(5,5,6);
		space.add(5,6,4);
		space.add(5,6,5);
		space.add(5,6,6);
		space.add(6,4,4);
		space.add(6,4,5);
		space.add(6,4,6);
		space.add(6,5,4);
		space.add(6,5,5);
		space.add(6,5,6);
		space.add(6,6,4);
		space.add(6,6,5);
		space.add(6,6,6);

		assertEquals(26,space.getNeighbors(5,5,5));

	}

	/*@org.junit.jupiter.api.Test
	void test05CreacionDeAutomata() {
		CellularAutomaton cell= new CellularAutomaton(,);
		cell.solve(new Rules() {
			@Override
			public int apply(Cell new_cell,Boolean isAlive, int numLiveCells) {
				if(numLiveCells == 3 && !isAlive ){
					new_cell.revive();
					return 1;
				}
				if(isAlive && (numLiveCells == 2 || numLiveCells ==3)){
					new_cell.revive();
					return 1;
				}
				return 0;

			}
		});
	}
*/



}
