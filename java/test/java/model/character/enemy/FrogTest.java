package model.character.enemy;

import framework.Framework;
import framework.fx.FXFramework;
import model.DynamicObject;
import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import model.character.enemy.Frog;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

/**
 * @author Hawre
 */
public class FrogTest {
    private PlayerImpl player;
    private static final String TILE_PATH = "other/other/tiles.png";
    private byte[][] tileTypes;
    private byte[][] tileTypes1;
    private Frog frog;
    private Level level;
    private int dy;


    @Before
    public void setup(){
        tileTypes = new byte[][]{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
        tileTypes1 = new byte[][]{{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                ,{0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, {1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1}};
        frog = new Frog(null);
        player = new PlayerImpl(null, null, null);
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes, TILE_PATH));
        level.addDynamicObject(Level.ObjectIndex.ENEMIES, frog);
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        dy = -(int) Math.round(Math.cos(frog.getTimer() / frog.getJUMP_LENGTH()) * frog.getJUMP_SPEED());
    }



    @Test
    public void frogMovingDown(){
        int x =2, y = 2;
        frog.setPosition(x, y);
        DynamicObject.Direction expected = frog.getDirection();
        frog.update(level);
        assertEquals(x, frog.getX());
        assertEquals(y+1, frog.getY());
        assertEquals(expected, frog.getDirection());
        assertFalse(frog.getCurrentAnimation().isFlipped());
    }


    @Test
    public void testJumpRight(){
        int x = 5, y = 5;
        frog.setPosition(x, y);
        frog.setJumping(true);
        frog.update(level);
        x += 1; // since isGoingRight == true
        y += dy;
        assertEquals(x, frog.getX());
        assertEquals(y, frog.getY());
    }



    @Test
    public void testJumpLeft(){
        int x = 5, y = 5;
        frog.setPosition(x, y);
        frog.setJumping(true);
        frog.setGoingRight(false);
        frog.update(level);
        x -= 1; // since isGoingRight == false
        y += dy;
        assertEquals(x, frog.getX());
        assertEquals(y, frog.getY());
    }


    /**
     * tile over frog, frog tries to jump
     */
    @Test
    public void FronCanNotJump(){
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes1, TILE_PATH));
        int x = 5, y = 5;
        frog.setPosition(x, y);
        frog.update(level);
        assertEquals(x, frog.getX());
        assertEquals(y, frog.getY());
    }


    @Test
    public void FrogCanJump(){
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes1, TILE_PATH));
        int x = 5, y = 5;
        frog.setPosition(x, y);
        frog.setJumping(true);
        frog.update(level);
        assertEquals(x, frog.getX());
        assertEquals(y, frog.getY());
        assertTrue(frog.isGoingRight());
    }


    @Test
    public void collisionWithPlayer(){
        frog.setPosition(10,10);
        player.setPosition(10,10);
        int playerHealth = player.getCurrentHealth();
        int frogHealth = frog.getCurrentHealth();
        frog.update(level);
        assertEquals(frogHealth, frog.getCurrentHealth());
        assertEquals(playerHealth-frog.getDAMAGE() , player.getCurrentHealth()); // since health can not be negative
    }


}
