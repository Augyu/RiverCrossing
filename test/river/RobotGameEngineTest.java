package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.print.attribute.Size2DSyntax;
import java.awt.*;

import static org.junit.Assert.*;

public class RobotGameEngineTest {

    public static final Item SMALLBOT_1 = Item.ITEM_0;
    public static final Item SMALLBOT_2 = Item.ITEM_1;
    public static final Item TALLBOT_1 = Item.ITEM_2;
    public static final Item TALLBOT_2 = Item.ITEM_3;
    private GameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new RobotGameEngine();
    }

    private void transport(Item item) {
        engine.loadBoat(item);
        engine.rowBoat();
        engine.unloadBoat(item);
    }

    private void transport(Item item1, Item item2) {
        engine.loadBoat(item1);
        engine.loadBoat(item2);
        engine.rowBoat();
        engine.unloadBoat(item1);
        engine.unloadBoat(item2);
    }

    @Test
    public void testObjectCallThroughs() {
        Assert.assertEquals("S1", engine.getItemLabel(SMALLBOT_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_1));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(SMALLBOT_1));

        Assert.assertEquals("S2", engine.getItemLabel(SMALLBOT_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_2));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(SMALLBOT_2));

        Assert.assertEquals("T1", engine.getItemLabel(TALLBOT_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(TALLBOT_1));
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(TALLBOT_1));

        Assert.assertEquals("T2", engine.getItemLabel(TALLBOT_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(TALLBOT_2));
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(TALLBOT_2));
    }

    @Test
    public void testMidTransport() {
        /*
         * Transport the goose to the other side, unload it, and check that it has
         * the appropriate location
         */
        engine.loadBoat(SMALLBOT_1);
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_2));
        transport(SMALLBOT_2);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(SMALLBOT_2));
    }

    @Test
    public void testWinningGame() {
        transport(SMALLBOT_1, SMALLBOT_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(SMALLBOT_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(TALLBOT_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(SMALLBOT_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(SMALLBOT_1, SMALLBOT_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(SMALLBOT_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(TALLBOT_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(SMALLBOT_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(SMALLBOT_1, SMALLBOT_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testError() {

        transport(SMALLBOT_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location s1Location = engine.getItemLocation(SMALLBOT_1);
        Location s2Location = engine.getItemLocation(SMALLBOT_2);
        Location t1Location = engine.getItemLocation(TALLBOT_1);
        Location t2Location = engine.getItemLocation(TALLBOT_2);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(TALLBOT_1);

        // check that the state has not changed
        Assert.assertEquals(s1Location, engine.getItemLocation(SMALLBOT_1));
        Assert.assertEquals(s2Location, engine.getItemLocation(SMALLBOT_2));
        Assert.assertEquals(t1Location, engine.getItemLocation(TALLBOT_1));
        Assert.assertEquals(t2Location, engine.getItemLocation(TALLBOT_2));
    }

    @Test
    public void testRestGame() {
        transport(TALLBOT_1);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(TALLBOT_1));

        engine.resetGame();
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(SMALLBOT_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(TALLBOT_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(TALLBOT_2));
        Assert.assertEquals(Location.START, engine.getBoatLocation());
    }
}