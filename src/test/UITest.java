package test;
import org.junit.*;

import Frame.MainJFrame;

import static org.junit.Assert.*;

class UITest{
    

    @Test
    public void chooseModeTest(){
        MainJFrame frame1 = new MainJFrame(0, null);
        assertNotNull("初始化失败，Frame不应为空",frame1);
        assertEquals("初始化错误，模式参数应该相等",frame1.initMode, 0);
        MainJFrame frame2 = new MainJFrame(1, null);
    }
}
