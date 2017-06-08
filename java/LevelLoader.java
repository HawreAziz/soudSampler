package utils;

import framework.Framework;
import java.util.ArrayList;
import lombok.val;
import model.*;

/**
 * @author Hawre
 */
public class LevelLoader {

  public static Level loadLevel(int number, Framework framework) {
    byte[][] sheetIndices = new ByteMatrixLoader(
            "levels/SHEET_INDICES_" + number).createMap(),
            tileTypes = new ByteMatrixLoader(
                    "levels/TILE_TYPES_" + number).createMap();
    DynamicObjectGenerator levelObjectsGen
            = new DynamicObjectGenerator("levels/OBJECTS_" + number);

    levelObjectsGen.loadDynamicObject();

    Level level = new LevelImpl(new TileMap(24, 24, sheetIndices, tileTypes
            , "images/other/tiles.png"));
    loadDynamicObjects(levelObjectsGen, level, framework);
    return level;
  }

  private static void loadDynamicObjects(DynamicObjectGenerator dog, Level level
          , Framework framework) {
    val doc = new DynamicObjectCollection(framework);
    val objects = dog.getLevelObjects();

    for (int i = 0; i < objects.size(); i++) {
      val args = objects.get(i);
      String internalName = args.get(0);
      int levelObjectIndex = getNextInt(args, 1)
              , x = getNextInt(args, 2)
              , y = getNextInt(args, 3);

      val obj = doc.get(internalName);
      level.addDynamicObject(levelObjectIndex, obj);
      obj.setPosition(x, y);
    }
  }

  private static int getNextInt(ArrayList<String> levelObjects, int i) {
    int number = 0;
    try {
      number = Integer.parseInt(levelObjects.get(i));
    } catch (Exception e) {
    	base.ErrorManager.sendUnknownError(e);
    }
    return number;
  }
}
