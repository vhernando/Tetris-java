import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;

import java.awt.image.BufferedImage;

/**
 * Clase que se encarga directamente de la interfaz gráfica.
 * Se encarga también de llamar a los métodos adecuados de la clase Tetris
 * cuando llegan eventos.
 * 
 * @author Vicente Hernando Ara
 * @version 2011.4.14
 */
public class Window implements KeyListener
{
    // instance variables

    // Tamaño de la imagen donde se dibujará el tablero.
    private final int IMAGE_WIDTH = 400;
    private final int IMAGE_HEIGHT = 510;

    private JFrame ventana;
    private JLabel labelDeImagen;
    private BufferedImage imagen;
    private JLabel etiquetaEstado;

    private Tetris tetris;
    
    /**
     * Constructor de la clase Window.
     *
     * @param tetris clase principal del juego, que gestionará los eventos de teclado.
     */
    public Window(Tetris tetris)
    {
        // initialise instance variables
	this.tetris = tetris;

        ventana = new JFrame("Tetris");
	ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// ventana.addWindowListener(new WindowAdapter() {
	// 	public void windowClosing(WindowEvent we) {
	// 	    System.exit(0);
	// 	}
	//     });

	Container panelContenedor = ventana.getContentPane();

	construirBarraDeMenu(ventana);
	
	imagen = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
	labelDeImagen = new JLabel(new ImageIcon(imagen));
	panelContenedor.add(labelDeImagen, BorderLayout.CENTER);

	etiquetaEstado = new JLabel("Tetris");
	panelContenedor.add(etiquetaEstado, BorderLayout.SOUTH);

	ventana.addKeyListener(this);

	ventana.pack();
	ventana.setVisible(true);
    }

    /**
     * Muestra una cadena en la etiqueta de estatus.
     * 
     * @param  str Cadena que le pasamos.
     */
    public void status(String str)
    {
	etiquetaEstado.setText(str);
    }

    /**
     * Devuelve la anchura de la imagen donde dibujaremos el tablero.
     * 
     * @return Devuelve el número de pixels de longitud de la imagen.
     */
    public int getWidth() 
    {
	return IMAGE_WIDTH;
    }

    /**
     * Devuelve la altura de la imagen donde dibujaremos el tablero.
     * 
     * @return Devuelve el número de pixels de altura de la imagen.
     */
    public int getHeight()
    {
	return IMAGE_HEIGHT;
    }

    /**
     * Colorea toda la imagen del mismo color.
     * 
     * @param  cl  Color con el que colorearemos la imagen.
     */
    public void llenarImagen(Color cl)
    {
	int ancho, alto;
	ancho = imagen.getWidth();
	alto = imagen.getHeight();

	for (int i=0; i<ancho; i++) 
	    {
		for (int j=0; j<alto; j++)
		    {
			imagen.setRGB(i,j,cl.getClr());
		    }
	    }
    }


    /**
     * Dibuja un rectángulo de color en la imagen.
     * 
     * @param  startX coordenada x de la esquina inferior izquierda del rectángulo.
     * @param  startY coordenada y de la esquina inferior izquierda del rectángulo.
     * @param  width  anchura del rectángulo.
     * @param  height altura del rectángulo.
     * @param  cl color con el que colorearemos el rectángulo.
     */
    public void llenarRecuadro(int startX, int startY, int width, int height, Color cl)
    {
	for (int i=startX; i < (startX + width); i++)
	    {
		for (int j=startY; j < (startY + height); j++)
		    {
			imagen.setRGB(i, j, cl.getClr());
		    }
	    }
    }


    /**
     * Dibuja un rectángulo de color, con un borde negro de un pixel, en la imagen.
     * 
     * @param  startX coordenada x de la esquina inferior izquierda del rectángulo.
     * @param  startY coordenada y de la esquina inferior izquierda del rectángulo.
     * @param  width  anchura del rectángulo.
     * @param  height altura del rectángulo.
     * @param  cl color con el que colorearemos el rectángulo.
     */
    public void llenarRecuadroBorde(int startX, int startY, int width, int height, Color cl)
    {
	int i,j;
	Color borde = Color.negro;

	// Rellenamos el fondo del recuadro.
	for (i=startX+1; i < (startX + width - 1); i++)
	    {
		for (j=startY+1; j < (startY + height - 1); j++)
		    {
			imagen.setRGB(i, j, cl.getClr());
		    }
	    }

	// Dibujamos los bordes del recuadro.
	for (i=startX; i < (startX + width); i++)
	    {
		imagen.setRGB(i, startY, borde.getClr());
		imagen.setRGB(i, startY + height - 1, borde.getClr());
	    }
	for (j=startY; j < (startY + height); j++)
	    {
		imagen.setRGB(startX, j, borde.getClr());
		imagen.setRGB(startX + width - 1, j, borde.getClr());
	    }
    }

    /**
     * Genera la barra de menú de la aplicación.
     * 
     * @param  JFrame  marco de la ventana donde añadiremos el menú.
     */
    private void construirBarraDeMenu(JFrame ventana)
    {
	JMenuBar barraDeMenu = new JMenuBar();
	ventana.setJMenuBar(barraDeMenu);

	// crea el menú Juego.
	JMenu menuJuego = new JMenu("Juego");
	barraDeMenu.add(menuJuego);

	JMenuItem elementoEmpezar = new JMenuItem("Empezar");
	elementoEmpezar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    tetris.empezar();
		}
	    });
   
	menuJuego.add(elementoEmpezar);
	JMenuItem elementoTerminar = new JMenuItem("Terminar");
	elementoTerminar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    tetris.terminar();
		}
	    });
	menuJuego.add(elementoTerminar);
    }


    /**
     * Repinta todo el marco principal mostrando los cambios que hayamos hecho.
     * 
     */
    public void repaint()
    {
	ventana.repaint();
    }


    /* KeyListener interface. */

    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
    }
    
    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {

	int keyCode = e.getKeyCode();

	switch (e.getKeyCode())
	    {
	    case KeyEvent.VK_LEFT:
		// Left cursor key.
		// Mueve la pieza a la izquierda.
		tetris.move(Tetris.Command.LEFT);
		break;
	    case KeyEvent.VK_UP:
		// UP cursor key.
		// Rota la pieza.
		if (tetris.checkRotateTime())
		    {
			tetris.move(Tetris.Command.ROTATE);
		    }
		break;
	    case KeyEvent.VK_RIGHT:
		// Right cursor key.
		// Mueve a la derecha.
		tetris.move(Tetris.Command.RIGHT);
		break;
	    case KeyEvent.VK_DOWN:
		// Desciende la pieza un máximo de 4 líneas de golpe.
		for (int i=0; i<4; i++)
		    {
			if (!tetris.desciende())
			    break;
		    }
		break;
	    case KeyEvent.VK_SPACE:
		// Space.
		// Desciende la pieza de golpe.
		while (tetris.desciende())
		    {
		    }
		break;
	    case KeyEvent.VK_P:
		// P - pause
		// Pausa y reanuda el juego.
		tetris.pause();
		break;
	    default:
		// Unknown key.
		break;
	    }
    }
    
    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
    }

}
