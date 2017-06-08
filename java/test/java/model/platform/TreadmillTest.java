package model.platform;

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

public class TreadmillTest {
    private Level level;
    private PlayerImpl player;
    private Treadmill treadmill;
    private int x;
    private int y;

    @Before
    public void setup(){
        treadmill = new Treadmill(null, DynamicObject.Direction.LEFT);
        level = new LevelImpl(new TileMap(24, 24, new byte[][]{}, new byte[][]{}, "images/other/tiles.png"));
        player = new PlayerImpl(null, null, null);
        level.addDynamicObject(Level.ObjectIndex.PLAYERS,  player);
        treadmill.setPosition(20, 90);
        x = 22; y = 46;
        player.setPosition(x, y);

    }


    @Test
    public void treadmillLeftPlayerRight(){
        int playerX = player.getX();
        treadmill.update(level);
        assertEquals(playerX-1, player.getX());
        assertEquals(y, player.getY());
    }


    @Test
    public void treadmillleftPlayerLeft(){
        player.setDirection(DynamicObject.Direction.LEFT);
        int playerX = player.getX();
        treadmill.update(level);
        assertEquals(playerX-1, player.getX());
        assertEquals(y, player.getY());
    }



    @Test
    public void treadmillRightPlayerRight(){
        treadmill = new Treadmill(null, DynamicObject.Direction.RIGHT);
        treadmill.setPosition(20, 90);
        int playerX = player.getX();
        treadmill.update(level);
        assertEquals(playerX+1, player.getX());
        assertEquals(y, player.getY());
    }


    @Test
    public void treadmillRightPlayerLeft(){
        treadmill = new Treadmill(null, DynamicObject.Direction.RIGHT);
        treadmill.setPosition(20, 90);
        player.setDirection(DynamicObject.Direction.LEFT);
        int playerX = player.getX();
        treadmill.update(level);
        assertEquals(playerX+1, player.getX());
        assertEquals(y, player.getY());
    }

    @Test
    public void testInternalIndex(){
        assertEquals(DynamicObject.INDEX_TREADMILL, treadmill.getInternalIndex());
    }

}












