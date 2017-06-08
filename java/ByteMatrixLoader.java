
package utils;

import base.ErrorManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Hawre
 */
public class ByteMatrixLoader {

  private final byte[][]map;
  private final InputStream inputStream;
  private final BufferedInputStream buffStream;
  private Scanner sc;

  public ByteMatrixLoader(String filename){
    inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
    buffStream = new BufferedInputStream(inputStream);
    buffStream.mark(Integer.MAX_VALUE);
    sc = new Scanner(buffStream);
    map = new byte[rowLength()][colLength()];
  }

  private int colLength(){
    resetInputStream();
    sc = new Scanner(buffStream);
    int col = 0;
    if(sc.hasNextLine()){
      col = sc.nextLine().length();
    }
    return col;
  }

  private int rowLength(){
    resetInputStream();
    int r = 0;
    while(sc.hasNextLine()){
      sc.nextLine();
      r++;
    }
    return r;
  }

  private void loadmapByte(){
    resetInputStream();
    sc = new Scanner(buffStream);
    int row = 0;
    while(sc.hasNextLine()){
      String rowStr = sc.nextLine();
      map[row] = getByteArray(rowStr);
      row++;
    }
  }

  private byte[] getByteArray(String rowStr){
    int col = 0;
    rowStr = rowStr.replace("", " ");
    Scanner colSc = new Scanner(rowStr);
    byte[] tmpArr = new byte[map[0].length];
    while(colSc.hasNext()){
      tmpArr[col] = colSc.nextByte();
      col++;
    }
    colSc.close();
    return tmpArr;
  }

  public byte[][]createMap(){
    loadmapByte();
    return map;
  }

  private void resetInputStream(){
    try{
      buffStream.reset();
    }catch(IOException e){
      ErrorManager.sendUnknownError(e);
    }
  }
}
