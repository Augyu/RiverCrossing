package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import river.GameEngine.Item;
import river.GameEngine.Location;

public class GameEngineTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testObjects() {
        GameObject farmer = new Farmer();
        Assert.assertEquals("Farmer", farmer.getName());
        Assert.assertEquals(Location.START, farmer.getLocation());
        Assert.assertEquals("", farmer.getSound());
        /* Check getters for wolf, goose, and beans */
        GameObject wolf = new Wolf();
        Assert.assertEquals("Wolf", wolf.getName());
        Assert.assertEquals(Location.START, wolf.getLocation());
        Assert.assertEquals("Howl", wolf.getSound());
        GameObject goose = new Goose();
        Assert.assertEquals("Goose", goose.getName());
        Assert.assertEquals(Location.START, goose.getLocation());
        Assert.assertEquals("Honk", goose.getSound());
        GameObject beans = new Beans();
        Assert.assertEquals("Beans", beans.getName());
        Assert.assertEquals(Location.START, beans.getLocation());
        Assert.assertEquals("", beans.getSound());
    }

    @Test
    public void testMidTransport() {
        GameEngine engine = new GameEngine();
        Assert.assertEquals(Location.START, engine.getLocation(Item.MID));

        /*
         * TODO Transport the goose to the other side, unload it, and check that it has
         * the appropriate location
         */
        engine.loadBoat(Item.MID);
        engine.loadBoat(Item.PLAYER);
        engine.rowBoat();
        engine.unloadBoat(Item.MID);
        Assert.assertEquals(Location.FINISH, engine.getLocation(Item.MID));
    }

    @Test
    public void testWinningGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.MID);
        engine.loadBoat(Item.PLAYER);
        engine.rowBoat();
        engine.unloadBoat(Item.MID);
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
        engine.loadBoat(Item.TOP);
        engine.rowBoat();
        engine.unloadBoat(Item.TOP);
        engine.loadBoat(Item.MID);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport beans
        engine.unloadBoat(Item.MID);
        engine.loadBoat(Item.BOTTOM);
        engine.rowBoat();
        engine.unloadBoat(Item.BOTTOM);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport goose
        engine.loadBoat(Item.MID);
        engine.rowBoat();
        engine.unloadBoat(Item.MID);
        engine.unloadBoat(Item.PLAYER);
        Assert.assertTrue(engine.gameIsWon());
        Assert.assertFalse(engine.gameIsLost());
    }

    @Test
    public void testLosingGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.MID);
        engine.rowBoat();
        engine.unloadBoat(Item.MID);
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
        engine.loadBoat(Item.TOP);
        engine.rowBoat();
        engine.unloadBoat(Item.TOP);
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
        engine.loadBoat(Item.MID);
        engine.rowBoat();
        engine.unloadBoat(Item.MID);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        GameEngine.Location topLoc = engine.getLocation(Item.TOP);
        GameEngine.Location midLoc = engine.getLocation(Item.MID);
        GameEngine.Location bottomLoc = engine.getLocation(Item.BOTTOM);
        GameEngine.Location playerLoc = engine.getLocation(Item.PLAYER);

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(Item.TOP);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getLocation(Item.TOP));
        Assert.assertEquals(midLoc, engine.getLocation(Item.MID));
        Assert.assertEquals(bottomLoc, engine.getLocation(Item.BOTTOM));
        Assert.assertEquals(playerLoc, engine.getLocation(Item.PLAYER));
    }
}
