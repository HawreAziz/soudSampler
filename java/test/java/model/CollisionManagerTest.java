package model;

import base.Rect;
import model.CollisionManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Hawre
 */
public class CollisionManagerTest {
    @Rule public ExpectedException exception = ExpectedException.none();
    private Rect rect;


    @Before
    public void setup(){
        rect = new Rect(1,2,1,2);
    }


    @Test
    public void moveBox(){
        CollisionManager collisionManager = new CollisionManager(new Rect[]{rect});
        Rect resBox =  collisionManager.getBox(0, 2, 2);
        assertEquals(rect.x+2, resBox.x);
        assertEquals(rect.y+2, resBox.y);
    }




    @Test
    public void testGroundBox(){
        int offX = 2, offY = 2;
        Rect rect = new Rect(offX,offY,1,2);
        Rect rect1 = new Rect(2,4,2,4);
        CollisionManager collisionManager = new CollisionManager(new Rect[]{rect, rect1});
        Rect resBox =  collisionManager.getGroundBox(offX, offY);
        assertEquals(rect1.x+offX, resBox.x);
        assertEquals(rect1.y+offY, resBox.y);
        assertEquals(rect.x, offX);
        assertEquals(rect.y, offY);

    }


    @Test
    public void getBoxOutOfboundException(){
        CollisionManager collisionManager = new CollisionManager(new Rect[]{});
        exception.expect(ArrayIndexOutOfBoundsException.class);
        assertNull(collisionManager.getBox(0, 2, 2));
    }



    @Test
    public void getBoxNull(){
        CollisionManager collisionManager = new CollisionManager(new Rect[]{});
        exception.expect(ArrayIndexOutOfBoundsException.class);
        assertNull(collisionManager.getGroundBox(2, 2));
    }



    @Test
    public void getBoxWidth(){
        CollisionManager collisionManager = new CollisionManager(new Rect[]{rect});
        assertEquals(rect.width, collisionManager.getBoxWidth(0));
    }

    @Test
    public void getBoxWidthOutOffBount(){
        CollisionManager collisionManager = new CollisionManager(new Rect[]{});
        exception.expect(ArrayIndexOutOfBoundsException.class);
        assertEquals(rect.width, collisionManager.getBoxWidth(0));
    }


    @Test
    public void getBoxHeight(){
        CollisionManager collisionManager = new CollisionManager(new Rect[]{rect});
        assertEquals(rect.height, collisionManager.getBoxHeight(0));
    }

    @Test
    public void getBoxHeightOutOffBount(){
        CollisionManager collisionManager = new CollisionManager(new Rect[]{});
        exception.expect(ArrayIndexOutOfBoundsException.class);
        assertEquals(rect.width, collisionManager.getBoxHeight(0));
    }
}
