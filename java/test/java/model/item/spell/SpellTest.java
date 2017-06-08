package model.item.spell;

import framework.fx.FXFramework;
import model.DynamicObject;
import model.Level;
import model.character.Player;
import model.character.PlayerImpl;
import model.item.spell.BasicBulletSpell;
import model.item.spell.Spell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Hawre
 */
public class SpellTest {
    private PlayerImpl player;


    @Before
    public void setup(){
        player = new PlayerImpl(null, null, null);
    }
    @Test
    public void takeTest(){
        Spell spell = new Spell() {
            @Override
            public int getInternalIndex() {return 0;}
            @Override
            public void fire(Player player, Level level) {}
            @Override
            public boolean canFire() {return false;}};
        spell.take(player);
        assertEquals(spell, player.getSpell());
        assertTrue(spell.shouldBeRemovedFromLevel());
    }
}
