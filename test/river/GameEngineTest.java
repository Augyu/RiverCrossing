package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import river.GameEngine.Item;

import java.util.function.IntBinaryOperator;

public class GameEngineTest {

    private GameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new GameEngine();
    }

    private void transport(Item item) {
        engine.loadBoat(item);
        engine.rowBoat();
        engine.unloadBoat(item);
    }

    @Test
    public void testObjectCallThroughs() {
        Assert.assertEquals("Farmer", engine.getItemName(Item.FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.FARMER));
        Assert.assertEquals("", engine.getItemSound(Item.FARMER));

        Assert.assertEquals("Wolf", engine.getItemName(Item.WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals("Howl", engine.getItemSound(Item.WOLF));

        Assert.assertEquals("Goose", engine.getItemName(Item.GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals("Honk", engine.getItemSound(Item.GOOSE));

        Assert.assertEquals("Beans", engine.getItemName(Item.BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals("", engine.getItemSound(Item.BEANS));
    }

    @Test
    public void testMidTransport() {
        /*
         * Transport the goose to the other side, unload it, and check that it has
         * the appropriate location
         */
        engine.loadBoat(Item.FARMER);
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));
        transport(Item.GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(Item.GOOSE));
    }

    @Test
    public void testWinningGame() {

        engine.loadBoat(Item.FARMER);

        // transport the goose
        transport(Item.GOOSE);
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
        transport(Item.WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(Item.BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transport(Item.GOOSE);
        engine.unloadBoat(Item.FARMER);
        Assert.assertTrue(engine.gameIsWon());
        Assert.assertFalse(engine.gameIsLost());
    }

    @Test
    public void testLosingGame() {

        engine.loadBoat(Item.FARMER);
        // transport the goose
        transport(Item.GOOSE);
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
        transport(Item.WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // leave wolf and goose alone
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {

        engine.loadBoat(Item.FARMER);
        // transport the goose
        transport(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location wolfLocation = engine.getItemLocation(Item.WOLF);
        Location gooseLocation = engine.getItemLocation(Item.GOOSE);
        Location beansLocation = engine.getItemLocation(Item.BEANS);
        Location farmerLocation = engine.getItemLocation(Item.FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.WOLF);

        // check that the state has not changed
        Assert.assertEquals(wolfLocation, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals(gooseLocation, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals(beansLocation, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals(farmerLocation, engine.getItemLocation(Item.FARMER));
    }
}
