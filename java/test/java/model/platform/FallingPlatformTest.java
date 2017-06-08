package model.platform;

import framework.fx.FXFramework;
import model.DynamicObject;
import model.Level;
import model.LevelImpl;
import model.TileMap;
import model.character.Player;
import model.character.PlayerImpl;
import model.character.enemy.Beetle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Hawre
 */
public class FallingPlatformTest {
    private FallingPlatform fallingPlatform;
    private Level level;
    private PlayerImpl player;
    private Beetle slime;

    @Before
    public void setup(){
        fallingPlatform = new FallingPlatform(null);
        slime = new Beetle(null);
        level = new LevelImpl(new TileMap(24, 24, new byte[][]{}, new byte[][]{}, "images/other/tiles.png"));
        player = new PlayerImpl(null, null, null);
        level.addDynamicObject(Level.ObjectIndex.PLAYERS, player);
        level.addDynamicObject(Level.ObjectIndex.PLATFORMS, fallingPlatform);
        level.addDynamicObject(Level.ObjectIndex.ENEMIES, slime);

    }



    @Test
    public void enemyOnPlatform2(){
        player.setPosition(220, 206);
        int dy = 250;
        fallingPlatform.setPosition(200, dy);
        fallingPlatform.update(level);
        assertEquals(dy+1, fallingPlatform.getY());
        player.setPosition(220, 207);
        fallingPlatform.update(level);
        assertEquals(dy+2, fallingPlatform.getY());
        player.setPosition(220, 208);
        fallingPlatform.update(level);
        assertEquals(dy+3, fallingPlatform.getY());
    }

    @Test
    public void enemyOnPlatform(){
        slime.setPosition(220, 219);
        int dy = 250;
        fallingPlatform.setPosition(200, dy);
        fallingPlatform.update(level);
        slime.update(level);
        fallingPlatform.update(level);
        assertEquals(dy, fallingPlatform.getY());
        slime.update(level);
        fallingPlatform.update(level);
        assertEquals(221, slime.getY());
    }

    @Test
    public void playerOnPlatformEnemyUnder(){
        int orginPlatformY = 262, orginPlayerY= 218;
        int orginSlimeY = 266;
        player.setPosition(220, orginPlayerY);
        fallingPlatform.setPosition(200, orginPlatformY);
        slime.setPosition(220, orginSlimeY);
        fallingPlatform.update(level);
        fallingPlatform.update(level);
        fallingPlatform.update(level);
        assertEquals(orginPlatformY+1, fallingPlatform.getY());
        assertEquals(orginPlayerY, player.getY());
        assertEquals(orginSlimeY, slime.getY());
    }




    @Test
    public void enemyOnPlatformPlayerUnder(){
        int orgPl = 270, orgSl = 224, orgPlat=255;
        slime.setPosition(220, 224);
        fallingPlatform.setPosition(200, orgPlat);
        player.setPosition(220, orgPl);
        fallingPlatform.update(level);
        fallingPlatform.update(level);
        fallingPlatform.update(level);
        fallingPlatform.update(level);
        assertEquals(orgPlat, fallingPlatform.getY());
        assertEquals(orgPl, player.getY());
        assertEquals(orgSl, slime.getY());
    }


    @Test
    public void playerOnPlatformEnemyMovesUnder(){
        int orginPlatformY = 262, orginPlayerY= 218;
        int orginSlimeY = 266;
        player.setPosition(220, orginPlayerY);
        fallingPlatform.setPosition(200, orginPlatformY);
        slime.setPosition(220, orginSlimeY);
        fallingPlatform.update(level);
        assertEquals(orginPlayerY, player.getY());
        assertEquals(orginSlimeY, slime.getY());
        slime.setPosition(300, orginSlimeY);
        fallingPlatform.update(level);
        assertEquals(orginPlatformY, fallingPlatform.getY());
    }


    @Test
    public void PlatfromUpAndDownTest(){
        int orginPlatformY = 262, orginPlayerY= 218;
        player.setPosition(220, orginPlayerY);
        fallingPlatform.setPosition(200, orginPlatformY);
        fallingPlatform.update(level);
        assertEquals(orginPlatformY+1, fallingPlatform.getY());
        assertEquals(orginPlayerY, player.getY());
        fallingPlatform.update(level);
        assertEquals(orginPlatformY, fallingPlatform.getY());
    }



}
