package test;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import Frame.MainJFrame;

public class UITest {

    @Before
    public void setup(){

    }
    @Test
    public void chooseModeTest() throws Exception {
        MainJFrame frame1 = new MainJFrame(0, null);
        assertNotNull("初始化失败，Frame不应为空", frame1);
        assertEquals("初始化错误，模式参数应该相等", frame1.initMode, 0);

        MainJFrame frame2 = new MainJFrame(1, null);
        assertNotNull("初始化失败，Frame不应为空", frame2);
        assertEquals("初始化错误，模式参数应该相等", frame2.initMode, 1);

        // 测试无效模式
        int[] invalidModes = {-1, Integer.MAX_VALUE, (int) (Integer.MAX_VALUE + 1L), Integer.MIN_VALUE, (int) (Integer.MIN_VALUE - 1L)};
        for (int mode : invalidModes) {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new MainJFrame(mode, null); // 这行代码期望抛出异常
            });
            assertEquals("无效的模式参数", exception.getMessage());
        }
    }
}
