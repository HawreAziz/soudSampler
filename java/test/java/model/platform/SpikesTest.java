package model.platform;

import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import model.character.enemy.Beetle;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Hawre
 */

public class SpikesTest {

    private Level level;
    private Player player;
    private Spikes spikes;

    @Before
    public void setup(){
        spikes = new Spikes(null);
        level = new LevelImpl(new TileMap(24, 24, new byte[][]{}, new byte[][]{}, "images/other/tiles.png"));
        player = new PlayerImpl(null, null, null);
        level.addDynamicObject(Level.ObjectIndex.PLATFORMS, spikes);
    }



    @Test
    public void SpikesUp(){
        int x = 5, y=5;
        int first = x -      spikes.getY_SPEED();
        int second = x - 2 * spikes.getY_SPEED();
        int third = x - 3*   spikes.getY_SPEED() ;
        spikes.setPosition(x, y);
        spikes.update(level);
        assertEquals(first, spikes.getY());
        spikes.update(level);
        assertEquals(second, spikes.getY());
        spikes.update(level);
        assertEquals(third, spikes.getY());
        assertEquals(x, spikes.getX());
    }



    @Test
    public void SpikesDown(){
        int x = 5, y=5;
        spikes.setCount(30);
        int first = x  +      spikes.getY_SPEED();
        int second = x + 2 *  spikes.getY_SPEED();
        int third = x  + 3  * spikes.getY_SPEED() ;
        spikes.setPosition(x, y);
        spikes.update(level);
        assertEquals(first, spikes.getY());
        spikes.update(level);
        assertEquals(second, spikes.getY());
        spikes.update(level);
        assertEquals(third, spikes.getY());
        assertEquals(x, spikes.getX());
    }


    @Test
    public void SpikeEnemyCollision(){
        Beetle slime = new Beetle(null);
        spikes.setPosition(5, 5);
        slime.setPosition(5, 5);
        level.addDynamicObject(Level.ObjectIndex.ENEMIES, slime);
        int slimeHealth = slime.getCurrentHealth();
        System.out.println(slime.getCurrentHealth());
        spikes.update(level);
        assertEquals(slimeHealth-1, slime.getCurrentHealth());
    }


    @Test
    public void SpikePlayerCollision(){
        PlayerImpl pl = new PlayerImpl(null, null, null);
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, pl);
        spikes.setPosition(5, 5);
        pl.setPosition(5, 5);
        spikes.update(level);
        assertEquals(0, pl.getCurrentHealth());
    }
}



