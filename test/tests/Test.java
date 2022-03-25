package tests;

import static org.junit.jupiter.api.Assertions.*;

import core.Cell;

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
	
	

}
