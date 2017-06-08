package model.character.enemy;

import framework.fx.FXFramework;
import model.DynamicObject;
import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Hawre
 */

public class BeetleTest {
    private Level level;
    private PlayerImpl player;
    private Beetle slime;


    @Before
    public void setup() {
        level = new LevelImpl(new TileMap(24, 24, new byte[][]{}, new byte[][]{{0, 0}, {0, 0}, {0, 0}}, "images/other/tiles.png"));
        slime = new Beetle(null);
        player = new PlayerImpl(null, null, null);
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
    }


    @Test
    public void slimeFallingDown() {
        int x = 10, y = 2;
        int expected = y + 1;
        slime.setPosition(x, y);
        slime.update(level);
        assertEquals(x, slime.getX());
        assertEquals(expected, slime.getY());
    }


    @Test
    public void slimeCollidesPlayer() {
        slime.setPosition(0, 0);
        player.setPosition(0, 0);
        int playerHealth = player.getCurrentHealth();
        slime.update(level);
        assertEquals(playerHealth-1, player.getCurrentHealth());
        assertEquals(0, slime.getX());
        assertEquals(1, slime.getY()); // since slime moves vertically, its y position updated with y+1
    }
}








