package model.item.spell;

import framework.fx.FXFramework;
import model.DynamicObject;
import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import model.item.spell.PowerBulletSpell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Hawre
 */
public class PowerBulletSpellTest {
    @Rule public ExpectedException exception = ExpectedException.none();
    private PowerBulletSpell powerBulletSpell;
    private Level level;
    private Player player;


    @Before
    public void setup() {
        powerBulletSpell = new PowerBulletSpell(null);
        level = new LevelImpl(new TileMap(100, 100, new byte[][]{}, new byte[][]{}, "images/other/tiles.png"));
        player = new PlayerImpl(null, powerBulletSpell, null);
    }


    @Test
    public void testFireWithStartTime(){
        ArrayList<DynamicObject> empty = (ArrayList<DynamicObject>)level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES)
                .clone();
        powerBulletSpell.fire(player, level);
        assertEquals(empty, new ArrayList<DynamicObject>(){});
        assertEquals(1, level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES).size());
    }


    @Test
    public void testFireNullPointer(){
        PowerBulletSpell powerBulletSpell = new PowerBulletSpell(null);
        exception.expect(NullPointerException.class);
        powerBulletSpell.fire(null, null);
    }


    @Test
    public void testFireWithSystemNano(){
        ArrayList<DynamicObject> empty = (ArrayList<DynamicObject>)level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES)
                .clone();
        powerBulletSpell.setStartTime(10);
        powerBulletSpell.fire(player, level);
        assertEquals(empty, new ArrayList<DynamicObject>(){});
        assertEquals(1, level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES).size());
    }


    @Test
    public void testCanFire1(){
        assertTrue(powerBulletSpell.canFire());
    }


    @Test
    public void tesftCanFire(){
        powerBulletSpell.setStartTime((long) 7.2480516E10);
        assertTrue(powerBulletSpell.canFire());
    }


    @Test
    public void testCanNotFire(){
        powerBulletSpell.setStartTime((long) 7.2480516E14);
        assertFalse(powerBulletSpell.canFire());
    }


    @Test
    public void testInternalIndex(){
        assertEquals(DynamicObject.INDEX_POWER_BULLET, powerBulletSpell.getInternalIndex());
    }

}
