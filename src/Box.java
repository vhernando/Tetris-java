
/**
 * Clase que dibuja rectángulos de color en la imagen que proporciona la clase Window.
 * 
 * @author Vicente Hernando Ara
 * @version 2011.4.14
 */
public class Box
{
    // Constants:
    private final int BOX_WIDTH = 240;
    private final int BOX_HEIGHT = 500;

    // instance variables 
    private Window v;

    // Dimensiones del tablero.
    private int numX, numY;
    
    // Coordenadas en bloques donde se dibuja la siguiente pieza.
    private int fueraX, fueraY;

    // Coordenadas en pixels de la esquina superior izquierda del tablero.
    private int startX, startY;
    
    // Anchura y altura en pixels de un bloque.
    private int pieceX, pieceY;

    /**
     * Constructor para objetos de la clase Box.
     *
     * @param v clase que abstrae la interfaz gráfica.
     * @param numX longitud del tablero en número de bloques.
     * @param numY altura del tablero en número de bloques.
     */
    public Box(Window v, int numX, int numY)
    {
        // initialise instance variables
	this.v = v;
	this.numX = numX;
	this.numY = numY;
	calculaValores();
	inicializa();
    }

    /**
     * Función que calcula unos valores iniciales que se calculan una
     * sóla vez para toda la clase.
     */
    public void calculaValores()
    {
	startX = 5;
	startY = (v.getHeight() - BOX_HEIGHT)/2;
	pieceX = BOX_WIDTH/numX;
	pieceY = BOX_HEIGHT/numY;
	fueraX = numX + 3;
	fueraY = 4;
    }


    /**
     * Devuelve la coordenada x en bloques donde dibujaremos la pieza siguiente.
     *
     * @return devuelve un entero con la coordenada x.
     */
    public int getFueraX()
    {
	return fueraX;
    }

    /**
     * Devuelve la coordenada y en bloques donde dibujaremos la pieza siguiente.
     *
     * @return devuelve un entero con la coordenada y.
     */
    public int getFueraY()
    {
	return fueraY;
    }

    /**
     * Dibuja el fondo y la caja donde caen las fichas.
     */
    public void inicializa()
    {
	v.llenarImagen(Color.cFondoImagen);
	v.llenarRecuadro(startX, startY, BOX_WIDTH, BOX_HEIGHT, Color.cFondo);
    }

    /**
     * Dibuja un bloque en el tablero.
     * El bloque tendrá borde si es un bloque de una pieza.
     *
     * @param x coordenada x en bloques del bloque que vamos a dibujar.
     * @param y coordenada y en bloques del bloque que vamos a dibujar.
     * @param cl color que usaremos para el rectángulo. 
     */
    public void dibujaBloque(int x, int y, int cl)
    {
	Color color = Color.get(cl);
	if ((cl == Color.ID_FONDO) || (cl == Color.ID_FONDO_IMAGEN))
	    {
		v.llenarRecuadro(startX + x*pieceX, startY + y*pieceY, pieceX, pieceY, color);
	    }
	else
	    {
		v.llenarRecuadroBorde(startX + x*pieceX, startY + y*pieceY, pieceX, pieceY, color);
	    }
    }

}
