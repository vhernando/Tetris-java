

/**
 * The test class BoxTest.
 *
 * @author  Vicente Hernando Ara
 * @version 2011.05.12
 */
public class BoxTest extends junit.framework.TestCase
{
    /**
     * Default constructor for test class BoxTest
     */
    public BoxTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }

    public void testGetFueraX()
    {
	Window window1 = new Window(null);
	Box box1 = new Box(window1, 12, 25);
	assertEquals(15, box1.getFueraX());
    }
}

