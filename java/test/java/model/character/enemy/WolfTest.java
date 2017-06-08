package model.character.enemy;

import framework.fx.FXFramework;
import model.DynamicObject;
import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import model.character.enemy.Wolf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;


/**
 * @author Hawre
 */
public class WolfTest {
    private PlayerImpl player;
    private static final String TILE_PATH = "images/other/tiles.png";
    private byte[][] tileTypes;
    private Wolf wolf;
    private LevelImpl level;
    private byte[][] tileTypes1;

    @Before
    public void setup(){
        tileTypes = new byte[][]{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
        tileTypes1 = new byte[][]{{0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                ,{0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1}, {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
        wolf = new Wolf(null);
        player = new PlayerImpl(null, null, null);
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes, TILE_PATH));
        level.addDynamicObject(Level.ObjectIndex.ENEMIES, wolf);

    }





    @Test
    public void wolfWhenPlayersDead(){
        int x = 5, y=5;
        wolf.setPosition(x,y);
        wolf.update(level);
        assertEquals(x+wolf.getDx(), wolf.getX());
        assertEquals(y+wolf.getDy(), wolf.getY());
    }



    @Test
    public void wolfMovment(){
        int x = 2, y=5;
        wolf.setPosition(x,y);
        wolf.update(level);
        assertEquals(x+1, wolf.getX());
        assertEquals(y+1, wolf.getY());
    }



    @Test
    public void wolfMoveVertically(){
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        player.setPosition(155, 155);
        int x = 5, y=5;
        wolf.setPosition(x,y);
        wolf.update(level);
        assertEquals(x, wolf.getX());
        assertEquals(y+1, wolf.getY());
    }



    @Test
    public void wolfMoveAndAttck(){
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        player.setPosition(10, 10);
        int x = 5, y=5;
        wolf.setPosition(x,y);
        wolf.update(level);
        int expectedX = x + wolf.getDx();
        assertEquals(expectedX, wolf.getX());
        assertEquals(y, wolf.getY());
        assertTrue(wolf.isAttacking());
    }




    @Test
    public void wolfWalkTest(){
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        player.setPosition(5, 5);
        int x = 10, y=10;
        wolf.setAttacking(false);
        wolf.setDy(1);
        wolf.setPosition(x,y);
        wolf.setAttackTimer(100);
        wolf.update(level);
        assertEquals(x, wolf.getX());
        assertEquals(y+wolf.getDy(), wolf.getY());
    }



    @Test
    public void wolfWalk(){
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes1, TILE_PATH));
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        player.setPosition(5, 5);
        int x = 10, y=16;
        wolf.setAttacking(false);
        wolf.setPosition(x,y);
        wolf.setAttackTimer(100);
        wolf.setDy(0);
        wolf.update(level);
        assertEquals(x+wolf.getDx(), wolf.getX());
        assertEquals(y, wolf.getY());
    }




    @Test
    public void wolfEndAttack(){
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes1, TILE_PATH));
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        player.setPosition(5,5);
        int x = 10, y = 16;
        wolf.setPosition(x, 16);
        wolf.setAttackTimer(100);
        wolf.setDy(0);
        wolf.setAttacking(true);
        wolf.update(level);
        assertFalse(wolf.isAttacking());
        assertEquals(x+wolf.getDx(), wolf.getX());
        assertEquals(y, wolf.getY());

    }


    @Test
    public void CollistinoWithPlayer(){
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        player.setPosition(10, 10);
        wolf.setPosition(10,10);
        int playerHealth = player.getCurrentHealth();
        int wolfHealth = wolf.getCurrentHealth();
        wolf.update(level);
        assertEquals(playerHealth-wolf.getDamage(), player.getCurrentHealth());
        assertEquals(wolfHealth, wolf.getCurrentHealth());
    }

}




