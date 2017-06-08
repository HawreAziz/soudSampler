package levels.base.timer;

import base.Rect;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Hawre
 */
public class RectTest {



    @Test
    public void testConstrctor(){
        int x = 2, y = 3, w = 4, h=5;
        Rect rect = new Rect(x, y, w, h);
        assertEquals(x, rect.x);
        assertEquals(y, rect.y);
        assertEquals(w, rect.width);
        assertEquals(h, rect.height);
    }


    @Test
    public void testSetPostion(){
        Rect rect = new Rect(1, 2, 1, 2);
        int newX = 2, newY= 4;
        rect.setPosition(newX, newY);
        assertEquals(newX, rect.x);
        assertEquals(newY, rect.y);
    }


    /**
     * false, since x less than Rect.x
     */
    @Test
    public void NotContainsXTest() {
        Rect rect = new Rect(50, 100, 200, 200);
        int x = 40, y = 100;
        assertFalse(rect.contains(x, y));
        assertTrue(false == rect.contains(x, y));
    }

    /**
     * returns false, since y less than Rect.y
     */
    @Test
    public void NotContainsYTest() {
        Rect rect = new Rect(50, 100, 200, 200);
        int x = 60, y = 10;
        assertFalse(rect.contains(x, y));
        assertTrue(false == rect.contains(x, y));
    }

    /**
     * x y is not in the rectangle, returns false
     */

    @Test
    public void NotContainsXYTest() {
        Rect rect = new Rect(50, 100, 200, 200);
        int x = 40, y = 10;
        assertFalse(rect.contains(x, y));
        assertEquals(false, rect.contains(x, y));
    }


    /**
     * returns, because (x,y) coordinate is in the rectangle.
     */
    @Test
    public void ContainsTest() {
        Rect rect = new Rect(50, 100, 200, 200);
        int x = 51, y = 101;
        assertTrue(rect.contains(x, y));
        assertNotEquals(false, rect.contains(x, y));
    }


    /**
     * tests whether an object is moved dx, dy pixels
     */
    @Test
    public void moveTestPositive() {
        Rect rect = new Rect(2, 2, 12, 12);
        int dx = 2, dy = 2;
        int expectedX = rect.x + dx;
        int expectedY = rect.y + dy;
        rect.move(dx, dy);
        assertEquals(expectedX, rect.x);
        assertEquals(expectedY, rect.y);
    }


    @Test
    public void moveTestNegative() {
        Rect rect = new Rect(2, 2, 12, 12);
        int dx = -4, dy = -4;
        int expectedX = rect.x + dx;
        int expectedY = rect.y + dy;
        rect.move(dx, dy);
        assertEquals(expectedX, rect.x);
        assertEquals(expectedY, rect.y);
    }


    @Test
    public void moveTestNoMovement() {
        Rect rect = new Rect(2, 2, 12, 12);
        int dx = 0, dy = 0;
        int previousX = rect.x;
        int previousY = rect.y;
        rect.move(dx, dy);
        assertEquals(previousX, rect.x);
        assertEquals(previousY, rect.y);
    }


    @Test
    public void testmoveByTestPositive() {
        Rect rect = new Rect(2, 2, 200, 200);
        int dx = 2, dy = 2;
        int expectedX = rect.x + dx;
        int expectedY = rect.y + dy;
        Rect newRect = rect.movedBy(dx, dy);
        assertEquals(expectedX, newRect.x);
        assertEquals(expectedY, newRect.y);
        assertEquals(rect.width, newRect.width);
        assertEquals(rect.height, newRect.height);
    }

    @Test
    public void testmoveByTestNegative() {
        Rect rect = new Rect(2, 2, 200, 200);
        int dx = -4, dy = -4;
        int expectedX = rect.x + dx;
        int expectedY = rect.y + dy;
        Rect newRect = rect.movedBy(dx, dy);
        assertEquals(expectedX, newRect.x);
        assertEquals(expectedY, newRect.y);
        assertEquals(rect.width, newRect.width);
        assertEquals(rect.height, newRect.height);
    }


    @Test
    public void testmoveByTestNoMovement() {
        Rect rect = new Rect(2, 2, 200, 200);
        int dx = 0, dy = 0;
        Rect newRect = rect.movedBy(dx, dy);
        assertEquals(rect.x, newRect.x);
        assertEquals(rect.y, newRect.y);
        assertEquals(rect.width, newRect.width);
        assertEquals(rect.height, newRect.height);
    }


    /**
     * collision test, where two objects collides.
     */
    @Test
    public void boxesShoudCollide() {
        Rect rect1 = new Rect(2, 2, 2, 2);
        Rect rect2 = new Rect(2, 2, 2, 2);
        assertTrue(rect1.collidesWith(rect2));
        assertEquals(true, rect1.collidesWith(rect2));
    }

    /**
     * return false when two objects do not collide
     */
    @Test
    public void NoCollision() {
        Rect rect1 = new Rect(2, 6, 2, 2);
        Rect rect2 = new Rect(2, 2, 2, 2);
        assertFalse(rect1.collidesWith(rect2));
    }

}
