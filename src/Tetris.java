import java.util.Random;
import java.util.ArrayList; 

/**
 * Clase que controla las reglas del juego tetris.
 * Se encarga de crear las demás clases necesarias del juego.
 * Proporciona los metodos necesarios para cuando las demás clases lanzan eventos de tiempo, teclado, etc.
 * 
 * @author Vicente Hernando Ara
 * @version 2011.04.18
 */
public class Tetris
{
    // Constantes. Tamaño en bloques del tablero.
    private final int numX = 12;
    private final int numY = 25;

    // instance variables
    private Window ventana; // Ventana general.
    private Box box; // Caja donde se coloca el tablero y las piezas.
    private Matriz matriz;  // Tablero.

    private Random rand;    
    private Piece piece, piezaSiguiente;    // Piezas en el tablero, actual y siguiente.

    private int numeroLineas; // Número de líneas completadas.

    private TTimer timer; // Controlador de tiempo.

    private ArrayList<Piece> pieceArray; // Array con las piezas posibles.

    /**
     * Constructor para objetos de la clase tetris.
     * Es privado porque se llama solo desde la funcion main de esta misma clase.
     */
    private Tetris()
    {
        // initialise instance variables.
	this.ventana = new Window(this);

	this.box = new Box(ventana, numX, numY);
	this.matriz = new Matriz(box, numX, numY);  // Crea el tablero.
	box.inicializa();

	// Inicializamos el pieceArray.
	pieceArray = new ArrayList<Piece>();
	try 
	    {
		pieceArray.add(new PieceA(box, Color.register(new Color(255,0,0))));
		pieceArray.add(new PieceB(box, Color.register(new Color(0,255,0))));
		pieceArray.add(new PieceC(box, Color.register(new Color(0,0,255))));
		pieceArray.add(new PieceD(box, Color.register(new Color(0,255,255))));
		pieceArray.add(new PieceE(box, Color.register(new Color(255,255,0))));
		pieceArray.add(new PieceF(box, Color.register(new Color(255,0,255))));
		pieceArray.add(new PieceG(box, Color.register(new Color(255,255,255))));
		//pieceArray.add(new Trimino(box, Color.register(new Color(200,200,200))));
	    }
	catch (ArrayException e)
	    {
		// No hacemos nada aquí, simplemente tendremos un vector con menos elementos.
	    }

	if (pieceArray.size() == 0)
	    {
		ventana.status("ERROR: NO HAY PIEZAS DISPONIBLES");
	    }
	
	ventana.repaint(); // Los cambios no son visibles hasta que no repintamos.

	timer = new TTimer(this); // Crea una instancia de TTimer.

	this.rand = new Random();
	nuevaPieza(); // Genera la siguiente pieza aleatoriamente.

	// Se empezara con el menu Juego->Empezar.
    }

    /**
     * Main function. Crea una instancia de la clase Tetris.
     */
    public static void main(String[] args)
    {
	new Tetris();
    }

    /**
     * Genera aleatoriamente una nueva pieza.
     * Cada pieza tiene un id entre 1 y el máximo.
     * Todas las piezas son igual de probables.
     */
    private void nuevaPieza()
    {
	this.piezaSiguiente = new Piece(pieceArray.get(rand.nextInt(pieceArray.size())));
    }

    /**
     * Comienza una partida.
     */
    public void empezar()
    {
	numeroLineas = 0;
	ventana.status("Numero Lineas: " + numeroLineas);

	box.inicializa();
	matriz.limpia();
	matriz.dibuja();

	ventana.repaint();

	nuevaPieza(); // Calcula aleatoriamente la siguiente pieza.

	timer.reset(); // Resetea los tiempos del TTimer.
	generarPieza(); // Genera la pieza actual del tablero.
    }


    /**
     * Dibuja la pieza actual.
     * Calcula aleatoriamente la siguiente pieza.
     * Comprueba que no haya finalizado la partida.
     * Inicia el TTimer.
     */
    private void generarPieza()
    {
	this.piece = this.piezaSiguiente;
	nuevaPieza();
	piezaSiguiente.dibujaFuera();
	piece.dibuja(); 
	// Si hay colisión se acaba la partida.
	if (matriz.colision(piece) == true)
	    {
		terminar();
		return;
	    }

	timer.comienza();
    }

    /**
     * Hace descender la pieza actual una línea.
     * Si la pieza llega al fondo del tablero elimina las lineas completas.
     *
     * @return devuelve verdadero si la pieza ha descendido correctamente.
     */
    public boolean desciende()
    {
	
	if (!timer.isPlaying())
	    return false;
	
	timer.stop();
	Piece nuevaPieza = new Piece(piece);
	nuevaPieza.desciende();
	if (matriz.colision(nuevaPieza) == true)
	    {
		matriz.add(piece);
		matriz.dibuja();
		ventana.repaint();

		// Elimina las líneas completadas.
		while(matriz.eliminaLinea())
		    {
			numeroLineas++;
			ventana.status("Numero Lineas: " + numeroLineas);
			matriz.dibuja();
			ventana.repaint();
		    }
		generarPieza();

		// Evitar que siga a partir de aqui.
		return false;
	    }

	timer.start();
	piece.borra();
	piece = nuevaPieza;
	piece.dibuja();

	ventana.repaint();
	return true;
    }

    /**
     * La partida ha finalizado.
     * Muestra un mensaje de fin en la etiqueta de estatus.
     */
    public void terminar()
    {
	timer.finaliza();
	matriz.dibuja();
	ventana.status("GAME OVER: Has hecho " + numeroLineas + " líneas");
	ventana.repaint();
    }

    /**
     * Pausa la partida si está en marcha. Continua la partida si está en pausa.
     */
    public void pause()
    {
	timer.pause();
    }

    /**
     * Devuelve verdadero si ha pasdo el tiempo para que la pieza pueda rotarse.
     */
    public boolean checkRotateTime()
    {
	return timer.checkRotateTime();
    }

    /** 
     * Enumeracion con los posibles movimientos de una pieza.
     */
    public enum Command {
	LEFT, RIGHT, ROTATE
	    }

    /**
     * Mueve una pieza.
     *
     * @param command Indica el tipo de movimiento: izquierda, derecha o rotacion.
     */
    public void move(Command command)
    {
	if (!timer.isPlaying())
	    return;

	// Crea una pieza idéntica a la actual.
	Piece nuevaPieza = new Piece(this.piece);

	// Realiza el movimiento en la copia.
	switch (command)
	    {
	    case LEFT:
		nuevaPieza.left();
		break;
	    case RIGHT:
		nuevaPieza.right();
		break;
	    case ROTATE:
		nuevaPieza.rotate();
		break;
	    }

	// Si el movimiento no se puede hacer lo descarta.
	if (matriz.colision(nuevaPieza) == true)
	    {
	 	return;
	    }

	// Realiza el movimiento.
	piece.borra();
	piece = nuevaPieza;
	piece.dibuja();

	// Muestra los cambios.
	ventana.repaint();
    }

}
