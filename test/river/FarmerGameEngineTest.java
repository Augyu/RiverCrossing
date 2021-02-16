package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class FarmerGameEngineTest {

    public static final Item FARMER = Item.ITEM_3;
    public static final Item WOLF = Item.ITEM_2;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item BEANS = Item.ITEM_0;
    private GameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new FarmerGameEngine();
    }

    private void transport(Item item) {
        engine.loadBoat(item);
        engine.rowBoat();
        engine.unloadBoat(item);
    }

    @Test
    public void testObjectCallThroughs() {
        Assert.assertEquals("", engine.getItemLabel(FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(FARMER));

        Assert.assertEquals("W", engine.getItemLabel(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(WOLF));

        Assert.assertEquals("G", engine.getItemLabel(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(GOOSE));

        Assert.assertEquals("B", engine.getItemLabel(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(BEANS));
    }

    @Test
    public void testMidTransport() {
        /*
         * Transport the goose to the other side, unload it, and check that it has
         * the appropriate location
         */
        engine.loadBoat(FARMER);
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        transport(GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(GOOSE));
    }

    @Test
    public void testWinningGame() {

        engine.loadBoat(FARMER);

        // transport the goose
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        /*
         * The steps above are the first two steps in a winning game, complete the
         * steps and check that the game is won.
         */
        transport(WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(GOOSE);
        engine.unloadBoat(FARMER);
        Assert.assertTrue(engine.gameIsWon());
        Assert.assertFalse(engine.gameIsLost());
    }

    @Test
    public void testLosingGame() {

        engine.loadBoat(FARMER);
        // transport the goose
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        /*
         * Once you transport the goose, go back alone, pick up the wolf, transport
         * the wolf, then go back alone again. After you go back alone the second time,
         * check that the game is lost.
         */
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport wolf
        transport(WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // leave wolf and goose alone
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {

        engine.loadBoat(FARMER);
        // transport the goose
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location wolfLocation = engine.getItemLocation(WOLF);
        Location gooseLocation = engine.getItemLocation(GOOSE);
        Location beansLocation = engine.getItemLocation(BEANS);
        Location farmerLocation = engine.getItemLocation(FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(WOLF);

        // check that the state has not changed
        Assert.assertEquals(wolfLocation, engine.getItemLocation(WOLF));
        Assert.assertEquals(gooseLocation, engine.getItemLocation(GOOSE));
        Assert.assertEquals(beansLocation, engine.getItemLocation(BEANS));
        Assert.assertEquals(farmerLocation, engine.getItemLocation(FARMER));
    }

    @Test
    public void testRestGame() {
        transport(FARMER);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(FARMER));

        engine.resetGame();
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
        Assert.assertEquals(Location.START, engine.getBoatLocation());
    }
}
