package model.item.spell;

import framework.Framework;
import framework.fx.FXFramework;
import model.DynamicObject;
import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import model.item.spell.RapidBulletSpell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * @author Hawre
 */
public class RapidBulletSpellTest {
    @Rule public ExpectedException expectedException = ExpectedException.none();
    private RapidBulletSpell rapidBulletSpell;
    private Player player;
    private Level level;


    @Before
    public void setup() {
        rapidBulletSpell = new RapidBulletSpell(null);
        level = new LevelImpl(new TileMap(100, 100, new byte[][]{}, new byte[][]{}, "images/other/tiles.png"));
        player = new PlayerImpl(null, rapidBulletSpell, null);
    }




    @Test
    public void fireTest(){
        assertEquals(level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES).size(), 0);
        rapidBulletSpell.fire(player, level);
        assertEquals(level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES).size(), 1);
    }

    @Test
    public void testFireNullPointer(){
        RapidBulletSpell rapidBulletSpell = new RapidBulletSpell(null);
        expectedException.expect(NullPointerException.class);
        rapidBulletSpell.fire(null, null);
    }


    @Test
    public void testFireWithSystemNano(){
        ArrayList<DynamicObject> empty = (ArrayList<DynamicObject>)level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES)
                .clone();
        rapidBulletSpell    .setStartTime(10);
        rapidBulletSpell.fire(player, level);
        assertEquals(empty, new ArrayList<DynamicObject>(){});
        assertEquals(1, level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES).size());
    }



    @Test
    public void testCanFire1(){
        assertTrue(rapidBulletSpell.canFire());
    }


    @Test
    public void tesftCanFire(){
        rapidBulletSpell.setStartTime((long) 7.2480516E10);
        assertTrue(rapidBulletSpell.canFire());
    }


    @Test
    public void testCanNotFire(){
        rapidBulletSpell.setStartTime((long) 7.2480516E14);
        assertFalse(rapidBulletSpell.canFire());
    }



    @Test
    public void testInernal(){
        assertEquals(DynamicObject.INDEX_RAPID_BULLET, rapidBulletSpell.getInternalIndex());
    }

 }
