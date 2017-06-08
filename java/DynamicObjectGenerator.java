package utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Hawre
 */
class DynamicObjectGenerator {

  private Scanner rowScan;
  private Scanner columnScan;
  private final InputStream inputStream;
  private final BufferedInputStream buffStream;
  private final ArrayList<ArrayList<String>> levelObjects;

  public DynamicObjectGenerator(String path) {
  	levelObjects = new ArrayList<>();
    inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
    buffStream = new BufferedInputStream(inputStream);
    buffStream.mark(Integer.MAX_VALUE);
  }

  public void loadDynamicObject() {
    resetInputStream();
    rowScan = new Scanner(buffStream);
    while (rowScan.hasNextLine()) {
      String row = rowScan.nextLine();
      if (row.length() == 0) {
        continue;
      }
      addDynamicObject(row);
    }
  }

  private void addDynamicObject(String row) {
    columnScan = new Scanner(row);
    ArrayList<String> temp = new ArrayList<>();
    while (columnScan.hasNext()) {
      temp.add(columnScan.next());
    }
    levelObjects.add(temp);
    columnScan.close();
  }

  public ArrayList<ArrayList<String>> getLevelObjects() {
    return levelObjects;
  }

  private void resetInputStream() {
    try {
      buffStream.reset();
    } catch (IOException e) {
    	base.ErrorManager.sendUnknownError(e);
    }
  }
}
