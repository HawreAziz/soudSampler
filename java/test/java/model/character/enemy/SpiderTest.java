package model.character.enemy;

import framework.fx.FXFramework;
import model.DynamicObject;
import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import model.character.enemy.Spider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import sun.security.pkcs11.wrapper.Constants;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Hawre
 */


public class SpiderTest {

    private byte[][] tileTypes;
    private static final String TILE_PATH = "images/other/tiles.png";
    private byte[][] tileTypes1;
    private LevelImpl level;
    private PlayerImpl player;
    private Spider spider;

    @Before
    public void setup(){
        tileTypes = new byte[][]{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
        tileTypes1 = new byte[][]{{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
        spider = new Spider(null);
        player = new PlayerImpl(null, null, null);
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes, TILE_PATH));
        level.addDynamicObject(Level.ObjectIndex.ENEMIES, spider);
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
    }


    @Test
    public void spiderMovingDown(){
        int y = 0;
        spider.setPosition(0, y);
        spider.update(level);
        assertEquals(y+1, spider.getY());
        spider.update(level);
        assertEquals(y+2, spider.getY());
        spider.update(level);
        assertEquals(y+3, spider.getY());
    }


    @Test
    public void spiderCanMoveUp(){
        int y = 0;
        spider.setPosition(0, y);
        spider.setClimbingDown(false);
        spider.update(level);
        assertEquals(y-1, spider.getY());
        spider.update(level);
        assertEquals(y-2, spider.getY());
        spider.update(level);
        assertEquals(y-3, spider.getY());
        assertFalse(spider.isClimbingDown());
    }




    @Test
    public void spiderMoveUp(){
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes1, TILE_PATH));
        int y = 0;
        spider.setDirection(DynamicObject.Direction.RIGHT);
        spider.setPosition(0, y);
        spider.setClimbingDown(true);
        spider.update(level);
        assertEquals(y, spider.getY());
        assertEquals(DynamicObject.Direction.LEFT, spider.getDirection());
    }


    @Test
    public void SpiderCollision(){
        player.setPosition(0,0);
        spider.setPosition(0,0);
        int playerHealth = player.getCurrentHealth();
        spider.update(level);
        assertEquals(playerHealth-spider.getDAMAGE(), player.getCurrentHealth());
    }

}
