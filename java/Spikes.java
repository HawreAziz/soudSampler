package model.platform;

import base.animation.ConstAnimation;
import base.animation.RepeatAnimation;
import base.graphics.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import model.CollisionManager;
import model.Level;
import model.character.Player;
import model.character.enemy.Enemy;

/**
 * @author Hawre
 */
public class Spikes extends Platform {

    @Setter private int count = 0;
    @Getter
    private static final int Y_SPEED = 1, DELAY = 30;
    private boolean upwards = true;

    public Spikes(Image sheet) {
        val animation = new RepeatAnimation(sheet, 24, 85, 1, 2, 1000);
        val cm = new CollisionManager(0, 0, 24, 85);
        setStates(new State(animation, cm));
    }

    private void resetCounter() {
        if (count == DELAY) {
            upwards = !upwards;
            count = 0;
        }
    }

    @Override
    public void update(Level level) {
        resetCounter();
        if (upwards) {
            setPosition(getX(), getY() - Y_SPEED);
        } else {
            move(0, Y_SPEED);
        }
        count++;
        for (val player : level.getDynamicObjectList(Level.ObjectIndex.PLAYERS)) {
            if ((((Player) player).collidesWith(this))) {
                ((Player) player).damage(((Player)player).getCurrentHealth());
            }
        }

        for (val enemy : level.getDynamicObjectList(Level.ObjectIndex.ENEMIES)) {
            if ((((Enemy) enemy).collidesWith(this))) {
                ((Enemy) enemy).damage(1);
            }
        }
    }

    @Override
    public int getInternalIndex() {
        return INDEX_SPIKES;
    }

    public static String getInternalName() {
        return "spikes";
    }

    public static ConstAnimation[] getConstAnimations() {
        return new ConstAnimation[]{
                new ConstAnimation(24, 85, 1)
        };
    }
}

