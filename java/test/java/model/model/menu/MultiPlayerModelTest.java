package model.model.menu;

import org.junit.Before;
import org.junit.Test;
import java.util.Random;

/**
 * @author Hawre
 */
public class MultiPlayerModelTest {


    private MultiplayerMenuModel multiplayerMenuModel;
    private String ip;

    @Before
    public void setup(){
        ip = "127.0.0.1";
        multiplayerMenuModel = new MultiplayerMenuModel(ip);
    }


   @Test
    public void testConstructor() {
       assertEquals(4, multiplayerMenuModel.getAmount());
       assertEquals(0, multiplayerMenuModel.getPosition());
   }



    @Test
    public void addCharTest() {
        multiplayerMenuModel = new MultiplayerMenuModel("");
        multiplayerMenuModel.addChar("1");
        multiplayerMenuModel.addChar("2");
        multiplayerMenuModel.addChar("7");
        multiplayerMenuModel.addChar(".");
        multiplayerMenuModel.addChar("0");
        multiplayerMenuModel.addChar(".");
        multiplayerMenuModel.addChar("0");
        multiplayerMenuModel.addChar(".");
        multiplayerMenuModel.addChar("1");
        assertEquals(ip, multiplayerMenuModel.getIP());
    }


    @Test
    public void CanNotAddChar() {
        multiplayerMenuModel.addChar("124");
        multiplayerMenuModel.addChar(".0");
        multiplayerMenuModel.addChar("-");
        multiplayerMenuModel.addChar("0.0");
        assertEquals(ip, multiplayerMenuModel.getIP());
    }


    @Test
    public void removeLastChar() {
        int strLen = multiplayerMenuModel.getIP().length();
        for(int i=0; i < strLen; i++){
            multiplayerMenuModel.removeLastCharInIP();
        }
        assertEquals("", multiplayerMenuModel.getIP());
    }



    @Test
    public void canNotRemove(){
        String ip = "";
        multiplayerMenuModel = new MultiplayerMenuModel(ip);
        multiplayerMenuModel.removeLastCharInIP();
        multiplayerMenuModel.removeLastCharInIP();
        multiplayerMenuModel.removeLastCharInIP();
        assertEquals(ip, multiplayerMenuModel.getIP());
    }
}
