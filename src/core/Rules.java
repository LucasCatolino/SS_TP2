package core;

@FunctionalInterface
public interface Rules {

    //true si va a vivier en el estado siguinete
    //false si muere en el estado siguiente
    //no tiene que cambiar el estado de la celda
    boolean apply(Cell cell, int numLiveCells);

}
