package model;

import base.Rect;
import model.TileMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Hawre
 */
public class TileMapTest {
    @Rule public ExpectedException thro = ExpectedException.none();
    private String tilepath;

    @Test
    public void testConstructor(){
        tilepath = "images/other/tiles.png";
        int expectedWidth = 200, expectedHeight = 200;
        TileMap tileMap = new TileMap(expectedWidth, expectedHeight, new byte[][]{}, new byte[][]{}, tilepath);
        assertEquals(expectedWidth, tileMap.getTileWidth());
        assertEquals(expectedHeight, tileMap.getTileHeight());
    }

    @Test
    public void testTileMapAmount(){
        TileMap tileMap = new TileMap(100, 100, new byte[][]{{1,1}}, new byte[][]{{}}, tilepath);
        int expectedSheetLength = 2;
        assertEquals(expectedSheetLength, tileMap.getTileAmountX());
        assertEquals(1, tileMap.getTileAmountY());
    }


    @Test
    public void getTileAmountOuntOfBount(){
        TileMap tileMap = new TileMap(24, 24, new byte[][]{}, new byte[][]{}, tilepath);
        thro.expect(IndexOutOfBoundsException.class);
        assertEquals(0, tileMap.getTileAmountX());
        tileMap.getTileAmountY();
    }


    @Test
    public void getTilemapNullPointer(){
        TileMap tileMap = new TileMap(24, 24, null, null, tilepath);
        thro.expect(NullPointerException.class);
        tileMap.getTileAmountX();
        tileMap.getTileAmountY();
    }

    /**
     * getSheetIndex fetches [y][x]
     */
    @Test
    public void testGetSheetIndices(){
        TileMap tileMap = new TileMap(24, 24, new byte[][]{{1,2},{3,4}}, new byte[][]{}, tilepath);
        assertEquals(1, tileMap.getSheetIndex(0,0));
        assertEquals(2, tileMap.getSheetIndex(1,0));
        assertEquals(3, tileMap.getSheetIndex(0,1));
        assertEquals(4, tileMap.getSheetIndex(1,1));
    }


    @Test
    public void testGetSheetOutOfBount(){
        TileMap tileMap = new TileMap(24, 24, new byte[][]{{1,2}}, new byte[][]{}, tilepath);
        assertEquals(1, tileMap.getSheetIndex(0,0));
        assertEquals(2, tileMap.getSheetIndex(1,0));
        thro.expect(IndexOutOfBoundsException.class);
        assertEquals(3, tileMap.getSheetIndex(0,1));
        assertEquals(4, tileMap.getSheetIndex(1,1));
    }



    /**
     * if no collision occurs then false must be returned
     */
    @Test
    public void isCollidingTestNoCollision(){
        TileMap tileMap = new TileMap(24, 24, new byte[][]{{1,1},{1,1},{1,1},{1,1}},new byte[][]{{1,1},{1,1},{1,1},{1,1}}, tilepath);
        Rect cBox = new Rect(0, 0, 0, 0);
        assertFalse(tileMap.isColliding(cBox));
        assertEquals(false, tileMap.isColliding(cBox));
    }



    /**
     * if no collision occurs then false must be returned
     */
    @Test
    public void isCollidingTestCollision(){
        TileMap tileMap = new TileMap(24, 24, new byte[][]{{1,1},{1,1},{1,1},{1,1}},new byte[][]{{1,1},{1,1},{1,1},{1,1}}, tilepath);
        Rect cBox = new Rect(24,24,24,24);
        assertTrue(tileMap.isColliding(cBox));
        assertEquals(true, tileMap.isColliding(cBox));
    }

    /**
     * Collision occurs, returns true
     */
    @Test
    public void isCollidingTestBodyOfInnerFor(){
        TileMap tileMap = new TileMap(24, 24, new byte[][]{},new byte[][]{{1,0},{1,1}}, tilepath);
        Rect cBox = new Rect(2,2,2,200);
        assertTrue(tileMap.isColliding(cBox));
        assertEquals(true, tileMap.isColliding(cBox));
    }

    /**
     * tests where tile is on ground.
     */
    @Test
    public void notOnGroundTest(){
        TileMap tileMap = new TileMap(24, 24, new byte[][]{{0,0},{0,0}},new byte[][]{{1,0},{1,1}}, tilepath);
        Rect cBox = new Rect(2,2,2,2);
        assertFalse(tileMap.isOnGround(cBox));
    }

    /**
     * tile is on ground, this method should return true
     */
    @Test
    public void isONGroundTestTrue(){
        TileMap tileMap = new TileMap(24, 24, new byte[][]{{0,0}},new byte[][]{{1,1},{1,1},{1,1},{1,1}}, tilepath);
        Rect cBox = new Rect(2,2,24,22);
        assertEquals(true, tileMap.isOnGround(cBox));
    }


    @Test
    public void notOnGournd(){
        TileMap tileMap = new TileMap(24, 24, new byte[][]{{3,3}},new byte[][]{{0,0},{0,0},{0,0}}, tilepath);
        Rect cBox = new Rect(2,2,24,22);
        assertEquals(false, tileMap.isOnGround(cBox));
    }
}
