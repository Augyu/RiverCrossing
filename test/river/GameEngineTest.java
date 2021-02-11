package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import river.GameEngine.Item;

public class GameEngineTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testObjects() {
        GameObject farmer = new GameObject("Farmer","");
        Assert.assertEquals("Farmer", farmer.getName());
        Assert.assertEquals(Location.START, farmer.getLocation());
        Assert.assertEquals("", farmer.getSound());
        /* Check getters for wolf, goose, and beans */
        GameObject wolf = new GameObject("Wolf", "Howl");
        Assert.assertEquals("Wolf", wolf.getName());
        Assert.assertEquals(Location.START, wolf.getLocation());
        Assert.assertEquals("Howl", wolf.getSound());
        GameObject goose = new GameObject("Goose", "Honk");
        Assert.assertEquals("Goose", goose.getName());
        Assert.assertEquals(Location.START, goose.getLocation());
        Assert.assertEquals("Honk", goose.getSound());
        GameObject beans = new GameObject("Beans", "");
        Assert.assertEquals("Beans", beans.getName());
        Assert.assertEquals(Location.START, beans.getLocation());
        Assert.assertEquals("", beans.getSound());
    }

    @Test
    public void testMidTransport() {
        GameEngine engine = new GameEngine();
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.GOOSE));

        /*
         * Transport the goose to the other side, unload it, and check that it has
         * the appropriate location
         */
        engine.loadBoat(Item.GOOSE);
        engine.loadBoat(Item.FARMER);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(Item.GOOSE));
    }

    @Test
    public void testWinningGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.loadBoat(Item.FARMER);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
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
        // transport the wolf
        // upload the wolf
        // transport the goose
        engine.loadBoat(Item.WOLF);
        engine.rowBoat();
        engine.unloadBoat(Item.WOLF);
        engine.loadBoat(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport beans
        engine.unloadBoat(Item.GOOSE);
        engine.loadBoat(Item.BEANS);
        engine.rowBoat();
        engine.unloadBoat(Item.BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
        engine.unloadBoat(Item.FARMER);
        Assert.assertTrue(engine.gameIsWon());
        Assert.assertFalse(engine.gameIsLost());
    }

    @Test
    public void testLosingGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
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
        engine.loadBoat(Item.WOLF);
        engine.rowBoat();
        engine.unloadBoat(Item.WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // leave wolf and goose alone
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.GOOSE);
        engine.rowBoat();
        engine.unloadBoat(Item.GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getItemLocation(Item.WOLF);
        Location midLoc = engine.getItemLocation(Item.GOOSE);
        Location bottomLoc = engine.getItemLocation(Item.BEANS);
        Location playerLoc = engine.getItemLocation(Item.FARMER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.WOLF);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getItemLocation(Item.WOLF));
        Assert.assertEquals(midLoc, engine.getItemLocation(Item.GOOSE));
        Assert.assertEquals(bottomLoc, engine.getItemLocation(Item.BEANS));
        Assert.assertEquals(playerLoc, engine.getItemLocation(Item.FARMER));
    }
}
