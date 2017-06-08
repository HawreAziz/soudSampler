package model;

import base.Rect;
import framework.fx.FXFramework;
import model.Camera;
import model.DynamicObject;
import model.character.Player;
import model.character.PlayerImpl;
import model.item.spell.BasicBulletSpell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Hawre
 */
public class CameraTest {
    private PlayerImpl player;
    private Camera camera;

    @Before
    public void setUp() {
        player = new PlayerImpl(null, null, null);
        camera = new Camera(100, 100, 200, 200);
    }

    @Test
    public void testConstructor(){
        int camWidth = 100, camHeight = 100, levelWidth = 200, levelHeight = 200;
        Camera camera = new Camera(camWidth, camHeight, levelWidth, levelHeight);
        int expectedCamMaxX = levelWidth - camWidth;
        int expectedCamMaxY = levelHeight - camHeight;
        Rect expectedRect = new Rect(0, 0, camWidth, camHeight);
        assertEquals(expectedCamMaxX, camera.getMaxCamX());
        assertEquals(expectedCamMaxY, camera.getMaxCamY());
        assertEquals(expectedRect.x, camera.getCamRect().x);
        assertEquals(expectedRect.y, camera.getCamRect().y);
        assertEquals(expectedRect.width, camera.getCamRect().width);
        assertEquals(expectedRect.height, camera.getCamRect().height);
    }


    /**
     * MIN_(X,Y) of camRect should not be the less than MIN_CAM_(X,Y) of the camera
     */
    @Test
    public void updateTestMinOfCamRect(){
        player.setPosition(50, 50);
        assertEquals(camera.getCamRect().x, camera.getMIN_CAM_X());
        assertEquals(camera.getCamRect().y, camera.getMIN_CAM_Y());
    }


    /**
     * MAX_(X,Y) of camRect should not be greater than MAX_CAM_(X,Y) of the camera
     */
    @Test
    public void updateTestMaxOfCamRect(){
        player.setPosition(150, 150);
        camera.update(player);
        assertEquals(camera.getCamRect().x, camera.getMaxCamX());
        assertEquals(camera.getCamRect().y, camera.getMaxCamY());
    }


    @Test
    public void shakeCamTest(){
        assertTrue(true); // nothing to test
    }


    @Test
    public void updateTestMinXMaxYOfCamRect(){
        player.setPosition(50, 150);
        camera.update(player);
        assertEquals(camera.getCamRect().x, camera.getMIN_CAM_X());
        assertEquals(camera.getCamRect().y, camera.getMaxCamY());
    }



    @Test
    public void updateTestMaxXMinYOfCamRect(){
        player.setPosition(150, 50);
        camera.update(player);
        assertEquals(camera.getCamRect().x, camera.getMaxCamX());
        assertEquals(camera.getCamRect().y, camera.getMIN_CAM_Y());
    }


    @Test
    public void shouldDrawTest(){
        player.setPosition(0, 0);
        Camera camera = new Camera(25, 63, 6, 50);
        assertTrue(camera.shouldDraw(player));

    }


    @Test
    public void shouldDrawTestShouldNotBeDrawn(){
        player.setPosition(24, 64);
        Camera camera = new Camera(24, 64, 64, 50);
        assertFalse(camera.shouldDraw(player));

    }


    @Test
    public void getNewXTest(){
        int newX = player.getX() - camera.getCamRect().x;
        assertEquals(newX, camera.getNewX(player));
    }


    @Test
    public void getNewYTest(){
        int newY = player.getY() - camera.getCamRect().y;
        assertEquals(newY, camera.getNewY(player));
    }

}
