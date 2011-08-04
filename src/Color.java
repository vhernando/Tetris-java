import java.util.ArrayList; 

/**
 * Color class deals with RGB pixel colors.
 * 
 * @author Vicente Hernando Ara
 * @version 2011.4.18
 */
class Color
{
    private int red;
    private int green;
    private int blue;
    private int clr;
    private static ArrayList<Color> colorArray = null;

    public Color(int red, int green, int blue)
    {
	this.red = red;
	this.green = green;
	this.blue = blue;
	    
	clr = (red & 0x00ff) << 16 | (green & 0x00ff) << 8 | (blue & 0x00ff);
    }

    public Color(int clr)
    {
	this.red   = (clr & 0x00ff0000) >> 16;
	this.green = (clr & 0x0000ff00) >> 8;
	this.blue  =  clr & 0x000000ff;
    }

    public int getClr()
    {
	return clr;
    }


    /**
     * Registers a color in colorArray.
     * @param Color to register
     * @return returns the index of the array were Color was placed or -1 if an error happens.
     */
    public static int register(Color cl) throws ArrayException
    {
	if (colorArray == null)
	    {
		colorArray = new ArrayList<Color>();

		// El 0 sera el color del fondo del tablero.
		if (!colorArray.add(cFondo))
		    {
			throw(new ArrayException());
		    }
		// El 1 sera el color del fondo del juego.
		if (!colorArray.add(cFondoImagen))
		    {
			throw(new ArrayException());
		    }
	    }
	int result = colorArray.size();
	
	if (!colorArray.add(cl))
	    {
		throw(new ArrayException());
	    }

	return result;
    }

    /**
     * Returns a color when passing an index.
     * @param index from colorArray.
     * @return Color from the array.
     */
    public static Color get(int index)
    {
	return colorArray.get(index);
    }

    // Colores predefinidos:
    public final static Color negro = new Color(0,0,0);
    public final static Color cFondo = new Color(0,100,200);
    public final static Color cFondoImagen = new Color(200,200,255);

    public final static int ID_FONDO = 0;
    public final static int ID_FONDO_IMAGEN = 1;

}
