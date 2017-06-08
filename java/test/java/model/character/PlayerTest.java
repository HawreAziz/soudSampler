package model.character;

import framework.fx.FXFramework;
import junit.framework.Assert;
import model.DynamicObject;
import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import model.item.spell.BasicBulletSpell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.security.Key;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Hawre
 */



public class PlayerTest {
    @Rule public ExpectedException exception = ExpectedException.none();
    private Player.Keys keys;
    private static final String TILE_PATH = "images/other/tiles.png";
    private PlayerImpl player;
    private Level level;
    private byte[][] tileTypes1;
    private int left, right, dash, jump, attack;
    private FXFramework framework;


    @Before
    public void setup(){
        tileTypes1 = new byte[][]{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
        level = new LevelImpl(new TileMap(24, 24, tileTypes1, tileTypes1, TILE_PATH));
        left = 0; right = 1; dash = 4; jump=2; attack = 3;
        player = new PlayerImpl(null, null, keys);
        player.setPosition(5, 4);
    }



    @Test
    public void testDamage(){
        int amount = 1, playerHealth = player.getCurrentHealth();
        player.damage(amount);
        assertEquals(playerHealth-amount, player.getCurrentHealth());
        playerHealth = player.getCurrentHealth();
        amount = 3;
        player.setInvUpdatesLeft(0);
        player.damage(amount);
        assertEquals(playerHealth-amount, player.getCurrentHealth());
    }


    @Test
    public void noDamage(){
        int amount = 1, playerHealth = player.getCurrentHealth();
        player.setInvUpdatesLeft(1);
        player.damage(amount);
        player.damage(amount);
        player.damage(amount);
        assertEquals(playerHealth, player.getCurrentHealth());
    }



    @Test
    public void testJumpUpAndMoveHorizontally(){
        player.setInvUpdatesLeft(1);
        player.setMoving(true);
        DynamicObject.Direction direction = player.getNextDirection();
        int walk = player.getX() + player.getXWalkVelocity() * player.getNextDirection().getDeltaX();
        player.update(level);
        assertEquals(walk, player.getX());
    }


    /**
     * To avoid long methodes/redudancy I test x and y separatelty
     */
    @Test
    public void shouldNotJumpUp(){
        player.setDirection(DynamicObject.Direction.LEFT);
        int beforeY = player.getY();
        int beforeX = player.getX();
        player.update(level);
        assertEquals(beforeY, player.getY());
        assertEquals(beforeX, player.getX());
        assertEquals(DynamicObject.Direction.RIGHT, player.getDirection());
    }




    @Test
    public void noMoveHorizontally(){
        player.setInvUpdatesLeft(1);
        player.setMoving(false);
        DynamicObject.Direction direction = player.getNextDirection();
        int walk = player.getX();
        player.update(level);
        assertEquals(walk, player.getX());
        assertEquals(direction, player.getDirection());
    }




    @Test
    public void moveDashUpdate(){
        player.setDashUpdatesLeft(1);
        int before = player.getY();
        DynamicObject.Direction direction = player.getNextDirection();
        int walk = player.getX() + player.getXDashVelocity() * player.getDirection().getDeltaX();
        player.update(level);
        assertEquals(before,player.getY());
        assertEquals(walk, player.getX());
        assertEquals(direction, player.getDirection());
    }



    @Test
    public void moveFireUpdate(){
        player.setFireUpdatesLeft(1);
        player.setMoving(true);
        DynamicObject.Direction direction = player.getNextDirection();
        int walk = player.getX() + player.getXWalkVelocity() * player.getDirection().getDeltaX();
        player.update(level);
        assertEquals(walk, player.getX());
        assertEquals(direction, player.getDirection());
    }



    @Test
    public void noMoveFireUpdate(){
        player.setFireUpdatesLeft(1);
        player.setMoving(false);
        DynamicObject.Direction direction = player.getNextDirection();
        int walk = player.getX();
        player.update(level);
        assertEquals(walk, player.getX());
        assertEquals(direction, player.getDirection());
    }



    @Test
    public void noMoveIsMovingUpdate(){
        player.setMoving(true);
        DynamicObject.Direction direction = player.getNextDirection();
        int walk = player.getX() + player.getXWalkVelocity() * player.getDirection().getDeltaX();
        player.update(level);
        assertEquals(walk, player.getX());
        assertEquals(direction, player.getDirection());
    }


    @Test
    public void testInternalIndex(){
        assertEquals(player.getInternalIndex(), DynamicObject.INDEX_PLAYER);
    }
}
