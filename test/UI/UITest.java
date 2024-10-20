package UI;

import Frame.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.swing.*;

public class UITest {

    @Before
    public void setup() {

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

    @Test
    public void initUITest() throws Exception {
//        有计时器
        MainJFrame frame1 = new MainJFrame(0, null);
        frame1.initUI(1);
        assertTrue(componentIsAdded(frame1, TimePanel.class));
//        没有计时器
        MainJFrame frame2 = new MainJFrame(0, null);
        frame2.initUI(0);
        assertFalse(componentIsAdded(frame2, TimePanel.class));
//        存在PlayPanel组件
        MainJFrame frame3 = new MainJFrame(0, null);
        frame3.initUI(0);
        assertTrue(componentIsAdded(frame3, PlayJPanel.class));
//        存在LLKListener
        MainJFrame frame4 = new MainJFrame(0, null);
        frame4.initUI(0);
        assertTrue(mouseListenerIsAdded(frame4.playJPanel, LLKListener.class));
//        UI不可调整
        MainJFrame frame5 = new MainJFrame(0, null);
        frame5.initUI(0);
        assertFalse(frame5.isResizable());
//        LLKListener.marks应被正确重置为0，并且visableLabel应正确显示"0"。
        MainJFrame frame6 = new MainJFrame(0, null);
        frame6.initUI(0);
        assertEquals(0, frame6.lis.marks);
        assertEquals(0 + "", frame6.visableLabel.getText());
//        mainFrame的鼠标监听器应为null，
        MainJFrame frame7 = new MainJFrame(0, null);
        frame7.initUI(0);
        frame7.cleanupResources();
        assertNull(frame7.playJPanel);
    }

    private <T> Boolean componentIsAdded(MainJFrame frame, Class<T> targetClass) {
        java.awt.Component[] components = frame.getContentPane().getComponents();
        boolean componentAdded = false;
        for (java.awt.Component component : components) {
            if (targetClass.isInstance(component)) {
                componentAdded = true;
                break;
            }
        }
        return componentAdded;
    }

    private <T> Boolean mouseListenerIsAdded(JPanel panel, Class<T> targetClass) {
        java.awt.event.MouseListener[] listeners = panel.getMouseListeners();
        boolean listenerAdded = false;
        for (java.awt.event.MouseListener listener : listeners) {
            if (targetClass.isInstance(listener)) {
                listenerAdded = true;
                break;
            }
        }
        return listenerAdded;
    }

}
