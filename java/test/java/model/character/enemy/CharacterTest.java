package model.character.enemy;

import model.Level;
import model.character.Character;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Hawre
 */
public class CharacterTest {

    @Test
    public void damageTest(){
        Character character = new Character(4) {
            @Override
            public void update(Level level) {}
            @Override
            public int getInternalIndex() {
                return 0;}};
        int expected = character.getCurrentHealth() - 2;
        character.damage(2);
        assertEquals(expected, character.getCurrentHealth());
    }


    @Test
    public void damageTestNegative(){
        Character character = new Character(-4) {
            @Override
            public void update(Level level) {}
            @Override
            public int getInternalIndex() {return 0;}};
        character.damage(1);
        assertEquals(0, character.getCurrentHealth());
    }


    @Test
    public void damageTestZero(){
        Character character = new Character(0) {
            @Override
            public void update(Level level) {}
            @Override
            public int getInternalIndex() {return 0;}};
        character.damage(0);
        assertEquals(0, character.getCurrentHealth());
    }



    @Test
    public void damageTestGreaterAmount(){
        Character character = new Character(2) {
            @Override
            public void update(Level level) {}
            @Override
            public int getInternalIndex() {return 0;}
        };
        int expected = 0;
        character.damage(4);
        character.damage(4);
        assertEquals(expected, character.getCurrentHealth());
    }

    @Test
    public void noDamage(){
        Character character = new Character(2) {
            @Override
            public void update(Level level) {}
            @Override
            public int getInternalIndex() {return 0;}
        };
        int old = character.getCurrentHealth();
        character.damage(0);
        assertEquals(old, character.getCurrentHealth());
    }

    @Test
    public void testDamageAndRestore(){
        Character character = new Character(4) {
            @Override
            public void update(Level level) {}

            @Override
            public int getInternalIndex() {
                return 0;
            }
        };
        int amount = 2;
        int maxHealth = character.getCurrentHealth();
        character.restoreHealth(amount);
        int reduced = character.getCurrentHealth() - amount;
        assertEquals(maxHealth, character.getCurrentHealth());
        character.damage(amount);
        assertEquals(reduced, character.getCurrentHealth());
    }


    @Test
    public void testRestoreNegative(){
        Character character = new Character(2) {
            @Override
            public void update(Level level) {}
            @Override
            public int getInternalIndex() {return 0;}};
        character.restoreHealth(-2);
        assertEquals(0, character.getCurrentHealth());
    }


    @Test
    public void testFullyRestoreHealth(){
        int maxHealth = 5;
        Character character = new Character(maxHealth) {
            @Override
            public void update(Level level) {}
            @Override
            public int getInternalIndex() {return 0;}};
        character.damage(maxHealth);
        character.fullyRestoreHealth();
        assertEquals(maxHealth, character.getCurrentHealth());
    }



    @Test
    public void souldBeRemovedTest(){
        Character character = new Character(4) {
            @Override
            public void update(Level level) {
            }
            @Override
            public int getInternalIndex() {return 0;}};
        assertFalse(character.shouldBeRemoved());
        character.damage(4);
        assertTrue(character.shouldBeRemoved());

    }
}
