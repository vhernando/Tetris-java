import java.util.ArrayList; 


/**
 * Clase que representa una pieza. Puede ser la pieza actual en el tablero,
 * la pieza siguiente que va a caer, o símplemente una pieza para probar un posible movimiento.
 * 
 * @author Vicente Hernando Ara
 * @version 2011.4.18
 */
public class Piece
{

    /**
     * Clase anidada que representa un bloque del tablero, mediante sus coordenadas x e y en bloques.
     *
     */
    protected class Bloque
    {
	private int x, y;

	/**
	 * Constructor de la clase bloque.
	 * @param x coordenada x del bloque.
	 * @param y coordenada y del bloque.
	 */
	public Bloque(int x, int y)
	{
	    this.x = x;
	    this.y = y;
	}

	/**
	 * Devuelve la coordenada x del bloque.
	 *
	 * @return entero con la coordenada x del bloque.
	 */
	public int getX()
	{
	    return x;
	}

	/**
	 * Devuelve la coordenada y del bloque.
	 *
	 * @return entero con la coordenada y del bloque.
	 */
	public int getY()
	{
	    return y;
	}

	/**
	 * Rota el bloque 90 grados en el sentido de las agujas del reloj, 
	 * sobre el bloque de coordenadas 0,0.
	 */
	public void rotate()
	{
	    int temp;
	    temp = this.x;
	    this.x = -y;
	    this.y = temp;
	}
    }


    // instance variables
    protected int id; // Identificador del tipo de pieza.
    protected int posX, posY; // Posición de la pieza en la matriz.
    protected ArrayList<Bloque> bl; // Array de bloques.
    protected boolean enableRotation; 

    protected Box box;

    /**
     * Constructor de objetos de la clase Piece.
     * @param box Clase box asociada.
     * @param id Color identifier associated.
     */
    public Piece(Box box, int id)
    {
	// initialise instance variables.
	enableRotation = true;
    	this.box = box;
    	this.id = id;
    	bl = new ArrayList<Bloque>();
    }
    

    /**
     * Crea una nueva pieza idéntica a la pieza original.
     *
     * @param orig pieza original que vamos a copiar.
     */
    public Piece(Piece orig) {
	this.id = orig.id;
	this.box = orig.box;
	this.posX = orig.posX;
	this.posY = orig.posY;
	this.enableRotation = orig.enableRotation;
	this.bl = new ArrayList<Bloque>();

	for (Bloque b : orig.bl)
	    {
		this.bl.add(new Bloque(b.getX(), b.getY()));
	    }
    }


    /**
     * Devuelve el numero de bloques de la pieza.
     */
    public int getNumBloques()
    {
	return bl.size();
    }

    /**
     * Devuelve la coordenada X del bloque cuyo indice le pasamos.
     *
     * @param index índice del bloque cuya coordenada X queremos averiguar.
     * @return devuelve el valor de la coordenada X del bloque.
     */
    public int getBloqueX(int index)
    {
	return posX + bl.get(index).getX();
    }

    /**
     * Devuelve la coordenada Y del bloque cuyo indice le pasamos.
     *
     * @param index índice del bloque cuya coordenada Y queremos averiguar.
     * @return devuelve el valor de la coordenada Y del bloque.
     */
    public int getBloqueY(int index)
    {
	return posY + bl.get(index).getY();
    }


    /** 
     * Devuelve el identificador de la pieza.
     */
    public int getId()
    {
	return this.id;
    }


    /**
     * Dibuja un bloque de la pieza.
     *
     * @param posX coordenada x del bloque.
     * @param posY coordenada y del bloque.
     * @param id identificador de la pieza.
     */
    private void _dibuja(int posX, int posY, int id)
    {
	for (Bloque b : bl)
	    {
		box.dibujaBloque(b.getX() + posX, b.getY() + posY, id);
	    }
	
    }

    /**
     * Dibuja los bloques de la pieza en el tablero.
     */
    public void dibuja()
    {
	// Dibuja los bloques de la pieza.
	_dibuja(this.posX, this.posY, this.id);
    }

    /**
     * Dibuja los bloques de la pieza actual con el color de fondo.
     * Lo que causa su borrado.
     */
    public void borra()
    {
	_dibuja(this.posX, this.posY, Color.ID_FONDO);
    }

    /**
     * Dibuja la pieza fuera del tablero. Usado para mostrar la pieza siguiente.
     */
    public void dibujaFuera()
    {
	final int ANCHO = 3;
	int fueraX, fueraY, maxX, minX, maxY, minY;
	fueraX = box.getFueraX();
	fueraY = box.getFueraY();
	minX = fueraX - ANCHO;
	maxX = fueraX + ANCHO;
	minY = fueraY - ANCHO;
	maxY = fueraY + ANCHO;
	
	// Dibujamos el fondo de la pieza.
	for (int i=minX ; i<maxX; i++)
	    {
		for (int j=minY; j<maxY; j++)
		    {
			box.dibujaBloque(i,j,Color.ID_FONDO_IMAGEN);
		    }
	    }

	// Dibujamos la pieza en sí.
	_dibuja(fueraX, fueraY, this.id);
    }


    /**
     * Rota la pieza 90 grados en el sentido de las agujas del reloj.
     */
    public void rotate()
    {
	if (enableRotation == false)
	    return;

	// Returns a rotated piece.
	for (Bloque b: bl)
	    {
		b.rotate();
	    }
    }

    /**
     * Mueve la pieza una posición a la izquierda.
     */
    public void left()
    {
	posX -= 1;
    }

    /**
     * Mueve la pieza una posición a la derecha.
     */
    public void right()
    {
	posX += 1;
    }

    /**
     * Desciende la pieza una posición.
     */
    public void desciende()
    {
	posY += 1;
    }
    
}
