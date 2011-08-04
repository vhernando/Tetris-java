import java.awt.event.*;
import javax.swing.Timer;
import java.util.Calendar;

/**
 * Clase que se encarga de todo lo relacionado con el tiempo.
 * TTimer es una abreviatura de Tetris Timer.
 * Maneja el temporizador, llama al método desciende de la clase Tetris. 
 * Lleva el tiempo de juego y el de la última rotación.
 * Pausa el juego y lo reanuda.
 * Controla la velocidad con la que caen las piezas, incrementándose ésta con el tiempo.
 * 
 * @author Vicente Hernando Ara
 * @version 2011.4.18
 */
public class TTimer
{
    // instance variables
    private Tetris tetris;

    private final long DELAY = 1000; // Tiempo que tarda una pieza en descender una posición.
    private final long LEVEL_TIME = 100000; // En este tiempo se reduce el delay a la mitad, a 1/3, 1/4, etc.
    private long currentDelay; // Delay de la pieza actual.
    private Timer timer; // Temporizador.

    private long gameTime; // Instante de comienzo del juego.
    private long pauseTime; // Instante en el que se ha pausado el juego.
    private long rotateTime; // Tiempo de juego de cuando se rotó la pieza por última vez.

    // Estado del juego.
    private boolean jugando; // Indica si estamos jugando o si el juego se ha acabado.
    private boolean pausado; // Indica si el juego está en pausa.


    /**
     * Constructor para objetos de la clase TTimer.
     * @param tet clase Tetris principal del juego.
     */
    public TTimer(Tetris tet) 
    {

	this.tetris = tet;

	currentDelay = DELAY;

	// Tiempos fake que indican que no se ha establecido el tiempo.
	rotateTime = 0;
	this.gameTime = 0;

	// Al principio aún no se ha comenzado la partida.
	this.jugando = false;
	this.pausado = false;
    }

    /**
     * Resetea el valor del delay actual.
     */
    public void reset()
    {
	currentDelay = DELAY;
	gameTime = 0;
	rotateTime = 0;
    }

    /**
     * Devuelve verdadero si la partida está en marcha y no pausada.
     */
    public boolean isPlaying()
    {
	return 	(jugando && !pausado);
    }

    /**
     * Devuelve verdadero si ha pasado el tiempo necesario para una nueva rotación.
     */
    public boolean checkRotateTime()
    {
	if (!isPlaying())
	    return false;

	// He cambiado el tiempo para que se pueda rotar 10 veces por segundo.
	final long MIN_TIME = 25; // 250

	long currentTime = getPlayTime();
	if ((currentTime - rotateTime) < MIN_TIME)
	    {
		return false;
	    }
	else 
	    {
		rotateTime = currentTime;
		return true;
	    }
    }


    /**
     * Función que pausa el juego si la partida está en marcha, y reaunuda el
     * juego si la partida está en pausa.
     */
    public void pause()
    {
	if (this.jugando == false)
	    return;
	
	if (this.pausado == false)
	    {
		//Pausamos el juego.
		timer.stop();
		pauseTime = Calendar.getInstance().getTimeInMillis();
		this.pausado = true;
	    }
	else
	    {
		// El juego continua otra vez.
		timer.restart();
		// Añadimos el tiempo que hemos estado en pausa.
		gameTime += (Calendar.getInstance().getTimeInMillis() - pauseTime);
		this.pausado = false;
	    }
    }


    /**
     * Inicia el TTimer para una pieza que comienza a caer.
     */
    public void comienza()
    {
	// Comienza una nueva pieza a caer. 
	if (gameTime == 0)
	    {
		gameTime = Calendar.getInstance().getTimeInMillis();
	    }

	this.jugando = true;
	this.pausado = false;
	// Esta fórmula reduce el delay con el tiempo de juego.
	currentDelay = ((DELAY * LEVEL_TIME) / (LEVEL_TIME + getPlayTime()));
	ActionListener taskPerformer = new ActionListener() {
	 	public void actionPerformed(ActionEvent evt) {
		    tetris.desciende();
		}
	    };
	timer = new Timer((int)currentDelay, taskPerformer);
	timer.restart();
    }

    /**
     * Para el TTimer porque la partida ha finalizado.
     */
    public void finaliza()
    {
	timer.stop();
	this.jugando = false;
	this.pausado = false;
    }

    /**
     * Reanuda el timer que estaba pausado.
     * La pieza seguira descendiendo.
     */
    public void start()
    {
	timer.start();
    }

    /**
     * Pausa el timer.
     * La pieza parará de descender.
     */
    public void stop()
    {
	// Paramos el timer.
	timer.stop();
    }

    /**
     * Devuelve el tiempo que llevamos jugando en milisegundos.
     */
    private long getPlayTime()
    {
	long currentTime = Calendar.getInstance().getTimeInMillis();
	return (currentTime - gameTime);
    }

}
