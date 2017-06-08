package model.projectile;

import model.DynamicObject;
import model.Level;
import model.projectile.Projectile;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Hawre
 */
public class ProjectileTest {


    private Projectile projectile;

    @Before
    public void setup(){
        projectile = new Projectile() {
            @Override
            public void update(Level level) {}

            @Override
            public int getInternalIndex() {return 0;}
        };
    }
    @Test
    public void testDirection(){
        projectile.setDirection(DynamicObject.Direction.LEFT);
        DynamicObject.Direction LEFT = projectile.getDirection();
        projectile.setDirection(DynamicObject.Direction.RIGHT);
        assertEquals(LEFT, DynamicObject.Direction.LEFT);
        assertEquals(DynamicObject.Direction.RIGHT, projectile.getDirection());
    }


    @Test
    public void shouldBeRemovedTest(){
        projectile.setDestroyed(false);
        assertEquals(false, projectile.shouldBeRemoved());
        projectile.setDestroyed(true);
        assertEquals(true, projectile.shouldBeRemoved());

    }
}
