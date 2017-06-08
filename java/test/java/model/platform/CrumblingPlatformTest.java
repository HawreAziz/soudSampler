package model.platform;

import model.DynamicObject;
import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Hawre
 */

public class CrumblingPlatformTest {
    private Level level;
    private PlayerImpl player;
    private CrumblingPlatform crumblingPlatform;

    @Before
    public void setup(){
        crumblingPlatform = new CrumblingPlatform(null);
        level = new LevelImpl(new TileMap(24, 24, new byte[][]{}, new byte[][]{}, "images/other/tiles.png"));
        player = new PlayerImpl(null, null, null);
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        level.addDynamicObject(Level.ObjectIndex.PLATFORMS, crumblingPlatform);
    }


    @Test
    public void shouldBeRemoved(){
        player.setPosition(220, 206);
        crumblingPlatform.setPosition(200, 250);
        crumblingPlatform.setCount(99);
        crumblingPlatform.update(level);
        assertTrue(crumblingPlatform.shouldBeRemoved());
        assertArrayEquals(new DynamicObject[]{}, level.getDynamicObjectList(Level.ObjectIndex.ENEMIES).toArray());
        assertTrue(!player.shouldBeRemoved());
    }



    @Test
    public void shouldNotBeRemoved(){
        player.setPosition(220, 206);
        crumblingPlatform.setPosition(200, 250);
        crumblingPlatform.setCount(10);
        crumblingPlatform.update(level);
        assertTrue(!crumblingPlatform.shouldBeRemoved());
        assertTrue(!player.shouldBeRemoved());
    }
}
