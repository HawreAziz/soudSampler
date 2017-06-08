package model.item.spell;

import framework.fx.FXFramework;
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
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * @author Hawre
 */
public class BasicBulletSpellTest {
    @Rule public ExpectedException expectedException = ExpectedException.none();
    private BasicBulletSpell basicBulletSpell;
    private Level level;
    private Player player;


    @Before
    public void setup() {
        basicBulletSpell = new BasicBulletSpell(null);
        level = new LevelImpl(new TileMap(100, 100, new byte[][]{}, new byte[][]{}, ""));
        player = new PlayerImpl(null, basicBulletSpell, null);
    }


    @Test
    public void testFireWithStartTime(){
        ArrayList<DynamicObject> empty = (ArrayList<DynamicObject>)level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES)
                .clone();
        basicBulletSpell.fire(player, level);
        assertEquals(empty, new ArrayList<DynamicObject>(){});
        assertEquals(1, level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES).size());
    }


    @Test
    public void testFireNullPointer(){
        BasicBulletSpell powerBulletSpell = new BasicBulletSpell(null);
        expectedException.expect(NullPointerException.class);
        powerBulletSpell.fire(null, null);
    }


    @Test
    public void testFireWithSystemNano(){
        ArrayList<DynamicObject> empty = (ArrayList<DynamicObject>)level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES)
                .clone();
        basicBulletSpell.setStartTime(5);
        basicBulletSpell.fire(player, level);
        assertEquals(empty, new ArrayList<DynamicObject>(){});
        assertEquals(1, level.getDynamicObjectList(Level.ObjectIndex.PROJECTILES).size());
    }



}
