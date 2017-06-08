package model.character.enemy;

import model.DynamicObject;
import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.PlayerImpl;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Hawre
 */
public class BatTest {

    private static final String TILE_PATH = "images/other/tiles.png";
    private Bat bird;
    private LevelImpl level;
    private PlayerImpl player;
    private byte[][] tileTypes;
    private byte[][] tileTypes1;

    @Before
    public void setup(){
        tileTypes = new byte[][]{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
        tileTypes1 = new byte[][]{{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
        bird = new Bat(null);
        player = new PlayerImpl(null, null, null);
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes, TILE_PATH));
        level.addDynamicObject(Level.ObjectIndex.ENEMIES, bird);
    }


    @Test
    public void playerDeadBirdStillSameDirection(){
        int dx = 10, dy = 10;
        bird.setPosition(dx, dy);
        DynamicObject.Direction direction = bird.getDirection();
        bird.update(level);
        assertEquals(direction, bird.getDirection());
        assertEquals(dx+1, bird.getX());
        assertEquals(dy , bird.getY());
        assertFalse(bird.getCurrentAnimation().isFlipped());
    }


    @Test
    public void playerDeadBirdTestFilpped(){
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes1, TILE_PATH));
        int dx = 10, dy = 10;
        bird.setPosition(dx, dy);
        bird.setDirection(DynamicObject.Direction.LEFT);
        DynamicObject.Direction direction = DynamicObject.Direction.RIGHT;
        bird.update(level);
        assertTrue(bird.getCurrentAnimation().isFlipped());
    }


    @Test
    public void birdNormalFlyAndFlip(){
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        int birdx = 350, birdy = 5;
        bird.setPosition(birdx, birdy);
        player.setPosition(3, 3);
        bird.update(level);
        assertEquals(birdx-1, bird.getX());
        assertEquals(birdy, bird.getY());
        assertTrue(bird.getCurrentAnimation().isFlipped());
    }

    @Test
    public void NormalFlyMoveRight(){
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        int birdx = 3, birdy = 3;
        bird.setPosition(birdx, birdy);
        player.setPosition(350, 5);
        bird.update(level);
        assertEquals(birdx+1, bird.getX());
        assertEquals(birdy, bird.getY());
        assertFalse(bird.getCurrentAnimation().isFlipped());
    }



    @Test
    public void flyDownTest(){
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes1, TILE_PATH));
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        int birdX = 340, birdY = 10;
        bird.setPosition(birdX, birdY);
        player.setPosition(10, 15);
        bird.setDiving(true);
        bird.update(level);
        assertTrue(bird.isFlyingUp());
        assertEquals(birdX, bird.getX());
        assertEquals(birdY-1, bird.getY());
        assertTrue(bird.getCurrentAnimation().isFlipped());
    }



    @Test
    public void BirdflyHorizontalMoveRight() {
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes1, TILE_PATH));
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        int birdX = 5, birdY = 10;
        bird.setPosition(birdX, birdY);
        player.setPosition(210, 15);
        bird.setFlyingUp(true);
        bird.update(level);
        assertFalse(bird.isFlyingUp());
        assertEquals(birdX+1, bird.getX());
        assertEquals(birdY, bird.getY());
        assertFalse(bird.getCurrentAnimation().isFlipped());
    }


    @Test
    public void BirdflyHorizontalMoveLeft() {
        level = new LevelImpl(new TileMap(24, 24, tileTypes, tileTypes1, TILE_PATH));
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        int birdX = 220, birdY = 10;
        bird.setPosition(birdX, birdY);
        player.setPosition(210, 15);
        bird.update(level);
        assertFalse(bird.isFlyingUp());
        assertEquals(birdX-1, bird.getX());
        assertEquals(birdY, bird.getY());
        assertTrue(bird.getCurrentAnimation().isFlipped());
    }

}












