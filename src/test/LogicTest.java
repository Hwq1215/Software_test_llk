package test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import Base.Config;
import static org.junit.Assert.*;

import javax.swing.ImageIcon;

public class LogicTest implements Config {
    private String imgPath; 
    private String img0;
    private String img1;

    @BeforeClass
    public void doSet(){
        imgPath = "imgs/";
        img0 = imgPath + "0.jpeg";
        img1 = imgPath + "1.jpeg";
    }

    @Before
    public void setUp(){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ICONS[i][j] = null; // 显式设置为 null
            }
        }
    }


    @Test  
    public void isRemovableSimple(){
        ICONS[2][2] = new ImageIcon("")
    }

    @Test
    public void isRemovableSimpleDiff(){

    }

    @Test
    public void isRemovableOneRow(){

    }

    @Test
    public void isRemovableOneRowSep(){

    }

    @Test
    public void isRemovableOneCol(){

    }

    @Test
    public void isRemovableOneColSep(){

    }

    @Test
    public void isRemovableDiffentRW(){

    }
    
    public void isRemovableDiffentRWSep(){

    }
}
