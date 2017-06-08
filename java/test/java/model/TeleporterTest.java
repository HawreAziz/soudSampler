package model;

import framework.fx.FXFramework;
import model.DynamicObject;
import model.Level;
import model.Teleporter;
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
public class TeleporterTest {
    private Level level;
    private Teleporter teleporter;
    private PlayerImpl player;


    @Before
    public void setup(){
        level = new LevelImpl(new TileMap(24, 24, new byte[][]{}, new byte[][]{}, "images/other/tiles.png"));
        teleporter = new Teleporter(null);
        player = new PlayerImpl(null, null, null);
    }

    @Test
    public void updateTestLevelCompleted(){
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        player.setPosition(0, 0);
        teleporter.update(level);
        assertTrue(teleporter.isLevelComplete());
    }


    @Test
    public void updateTestLevelNotCompleted(){
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        player.setPosition(100, 100);
        teleporter.update(level);
        assertFalse(teleporter.isLevelComplete());
    }
}
