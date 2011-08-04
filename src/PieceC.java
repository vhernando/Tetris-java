import java.util.ArrayList; 

/**
 * Write a description of class PieceA here.
 * 
 * @author Vicente Hernando Ara
 * @version 2011.05.02
 */
public class PieceC extends Piece
{
    /**
     * Constructor de objetos de la clase Piece.
     * @param box Clase box asociada.
     * @param id identificador del color de la pieza.
     */
    public PieceC(Box box, int id)
    {
	super(box, id);
	enableRotation = false;
	
    	// Composición en bloques de la pieza.
	posX = 5;
	posY = 1;
	bl.add(new Bloque(0,0));
	bl.add(new Bloque(0,-1));
	bl.add(new Bloque(1,0));
	bl.add(new Bloque(1,-1));
    }
}
