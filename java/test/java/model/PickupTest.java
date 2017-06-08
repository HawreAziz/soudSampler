package model;

import framework.fx.FXFramework;
import model.DynamicObject;
import model.Level;
import model.Pickup;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import model.item.spell.BasicBulletSpell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Hawre
 */
public class PickupTest {
    private BasicBulletSpell basicBulletSpell;
    private Pickup pickup;
    private Level level;
    private PlayerImpl player;


    @Before
    public void setup(){
        basicBulletSpell = new BasicBulletSpell(null);
        pickup = new Pickup(basicBulletSpell);
        level = new LevelImpl(new TileMap(24, 24, new byte[][]{}, new byte[][]{}, "images/other/tiles.png"));
        player = new PlayerImpl(null,basicBulletSpell, null);
    }


    /**
     * Player picks up a spell, spell should be removed from the level, and player has the spell
     */
    @Test
    public void updatePlayerPicksSpell(){
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        level.addDynamicObject(Level.ObjectIndex.PICKUPS, pickup);
        player.setPosition(200,400);
        pickup.setPosition(200,400);
        pickup.update(level);
        level.update();
        assertTrue(pickup.shouldBeRemoved());
        assertFalse(level.getDynamicObjectList(Level.ObjectIndex.PICKUPS).contains(pickup));
    }


    @Test
    public void updatePlayerNotPicksSpell(){
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        level.addDynamicObject(Level.ObjectIndex.PICKUPS, pickup);
        player.setPosition(100,400);
        pickup.setPosition(200,400);
        pickup.update(level);
        level.update();
        assertFalse(pickup.shouldBeRemoved());
        assertTrue(level.getDynamicObjectList(Level.ObjectIndex.PICKUPS).contains(pickup));
    }
}
