package model.platform;

import model.CollisionManager;
import model.Level;
import base.graphics.Image;
import base.animation.ConstAnimation;
import base.animation.RepeatAnimation;
import lombok.val;
import model.character.Player;


/**
 * @author  Hawre
 */
public class Treadmill extends Platform {
    private Direction direction;

    public Treadmill(Image sheet, Direction direction) {
        this.direction = direction;
        val anim = new RepeatAnimation(sheet, 96, 16, 1, 2, 1000);
        val animCm = new CollisionManager(0, 0, 96, 16);
        setStates(new State(anim, animCm));
    }

    @Override
    public boolean shouldBeRemoved() {
        return false;
    }

    @Override
    public void update(Level level) {
        for (val player : level.getDynamicObjectList(Level.ObjectIndex.PLAYERS)) {
            int dx = ((Player) player).getDirection().getDeltaX();
            if (direction == Direction.LEFT) {
                if(((Player) player).getDirection() == Direction.RIGHT){
                    dx = -1*(dx);
                }
            }else if(direction == Direction.RIGHT){
                if(((Player) player).getDirection() == Direction.LEFT){
                    dx = Math.abs(dx);
                }
            }
            if (isBoxDirectlyAbove(player.getGroundCollisionBox())) {
                ((Player) player).setPosition(player.getX() + dx, player.getY());
            }
        }
    }

    @Override
    public int getInternalIndex() {
        return INDEX_TREADMILL;
    }


    public static String getInternalName(){
        return "treadmill";
    }

    public static ConstAnimation[] getConstAnimations() {
        return new ConstAnimation[]{
                new ConstAnimation(96, 16, 1)
        };
    }
}
