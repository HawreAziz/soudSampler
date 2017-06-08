package utils;

import framework.Framework;
import lombok.val;
import model.character.enemy.*;
import model.item.spell.*;
import model.platform.*;
import model.*;
import java.util.HashMap;
import model.DynamicObject.Direction;
import model.item.HealthPotion;

/**
 * @author Hawre
 */
class DynamicObjectCollection {

  private final HashMap<String, DynamicObject.Factory> map = new HashMap<>();

  public DynamicObjectCollection(Framework framework) {
    map.put(Beetle.getInternalName(), ()
            -> new Beetle(framework.getSheet(DynamicObject.INDEX_BEETLE)));

    map.put(Frog.getInternalName(), ()
            -> new Frog(framework.getSheet(DynamicObject.INDEX_FROG)));

    map.put(Bat.getInternalName(), ()
            -> new Bat(framework.getSheet(DynamicObject.INDEX_BAT)));

    map.put(Andromalius.getInternalName(), ()
            -> new Andromalius(framework.getSheet(DynamicObject.INDEX_ANDROMALIUS),
            		framework.getSheet(DynamicObject.INDEX_RAPID_BULLET),
            		framework.getSheet(DynamicObject.INDEX_BEETLE)));

    map.put(Spider.getInternalName(), ()
            -> new Spider(framework.getSheet(DynamicObject.INDEX_SPIDER)));

    map.put(Wolf.getInternalName(), ()
            -> new Wolf(framework.getSheet(DynamicObject.INDEX_WOLF)));

    map.put(CrumblingPlatform.getInternalName(), ()
            -> new CrumblingPlatform(framework.getSheet(
                    DynamicObject.INDEX_CRUMBLING_PLATFORM)));

    map.put(FallingPlatform.getInternalName(), ()
            -> new FallingPlatform(framework.getSheet(
                    DynamicObject.INDEX_FALLING_PLATFORM)));

    map.put(PowerBulletSpell.getInternalName(), ()
            -> new Pickup(new PowerBulletSpell(framework.getSheet(
                    DynamicObject.INDEX_POWER_BULLET))));

    map.put(BasicBulletSpell.getInternalName(), ()
            -> new Pickup(new BasicBulletSpell(framework.getSheet(
                    DynamicObject.INDEX_BASIC_BULLET))));

    map.put(RapidBulletSpell.getInternalName(), ()
            -> new Pickup(new RapidBulletSpell(framework.getSheet(
                    DynamicObject.INDEX_RAPID_BULLET))));

    map.put(Teleporter.getInternalName(), ()
            -> new Teleporter(framework.getSheet(
                    DynamicObject.INDEX_TELEPORTER)));

    map.put(Spikes.getInternalName(), ()
            -> new Spikes(framework.getSheet(
                    DynamicObject.INDEX_SPIKES)));

    map.put(Treadmill.getInternalName() + "Right", ()
            -> new Treadmill(framework.getSheet(
                    DynamicObject.INDEX_TREADMILL), Direction.RIGHT));

    map.put(Treadmill.getInternalName() + "Left", ()
            -> new Treadmill(framework.getSheet(
                    DynamicObject.INDEX_TREADMILL), Direction.LEFT));

    map.put(HealthPotion.getInternalName(), ()
            -> new Pickup(new HealthPotion(framework.getSheet(
                    DynamicObject.INDEX_HEALTH_POTION))));
  }

  public DynamicObject get(String str) {
  	val factory = map.get(str);
  	if (factory == null) return null;
  	return factory.getInstance();
  }
}