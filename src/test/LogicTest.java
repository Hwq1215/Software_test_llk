package test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import Base.Config;
import Base.MatchResult;
import Base.Utils;
import Frame.LLKListener;
import Frame.MainJFrame;
import Frame.PlayJPanel;
import Frame.TimePanel;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.matches;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.rmi.CORBA.Util;
import javax.swing.ImageIcon;

public class LogicTest implements Config {
    private static String imgPath; 
    private static String img0;
    private static String img1;
    private static LLKListener llk;

    @BeforeClass
    public static void doSet(){
        imgPath = "imgs/";
        img0 = imgPath + "0.jpeg";
        img1 = imgPath + "1.jpeg";
        MainJFrame mainJFrame = new MainJFrame(0,null);
        mainJFrame.initUI(0);
        mainJFrame.setVisible(false);
        
        llk = new LLKListener(mainJFrame);
        llk.clearResourse();
        mainJFrame.cleanupResources();
    }

    @Before
    public void setUp(){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ICONS[i][j] = null; // 显式设置为 null
            }
        }
    }

    public void ICONSsetup(int r1,int c1,int r2,int c2,String img1,String img2){
        ICONS[r1][c1] = new ImageIcon(img1);
        ICONS[r2][c2] = new ImageIcon(img2);
    }

    // 相邻两个相同的图标消除
    @Test  
    public void isRemovableSimple(){
        ICONSsetup(2, 2, 2, 3, img0, img0);
        assertEquals("相邻两个相同的图标应该被消除，但未能消除", true, llk.isRemovable(2, 2, 2, 3)); 
    }

    // 同一行中间相隔的图标消除
    @Test
    public void isRemovableOneRow(){
        ICONSsetup(2, 2, 2, 4, img0, img0);
        // 初始化第 3 列，确保中间的图标不为空
        for (int i = 0; i < ROWS; i++) {
            ICONS[i][3] = new ImageIcon(img1); // 中间的图标不为空
        }
        assertEquals("同一行中间相隔的图标有阻隔，应该无法消除", false, llk.isRemovable(2, 2, 2, 4));
    }

    // 同一列中间相隔的图标消除
    @Test
    public void isRemovableOneCol(){
        ICONSsetup(1, 2, 3, 2, img0, img0);
        // 初始化第 2 行，确保中间的图标不为空
        for (int j = 0; j < COLS; j++) {
            ICONS[2][j] = new ImageIcon(img1); // 中间的图标不为空
        }
        assertEquals("同一列中间相隔的图标有阻隔，应该无法消除", false, llk.isRemovable(1, 2, 3, 2));
    }

    // 同一行中间不相隔的图标消除
    @Test
    public void isRemovableOneRowSep(){
        ICONSsetup(2, 2, 2, 5, img0, img0);
        assertEquals("同一行中间不相隔的图标应该被消除，但未能消除", true, llk.isRemovable(2, 2, 2, 5));
    }

    // 同一列中间不相隔的图标消除
    @Test
    public void isRemovableOneColSep(){
        ICONSsetup(1, 4, 4, 4, img0, img0);
        assertEquals("同一列中间不相隔的图标应该被消除，但未能消除", true, llk.isRemovable(1, 4, 4, 4));
    }

    // 不同行不同列中间相隔图标消除
    @Test
    public void isRemovableDiffentRW(){
        ICONSsetup(1, 2, 2, 5, img0, img0);
        // 初始化中间位置，确保中间的图标不为空
        for (int i = 0; i < ROWS; i++) {
            ICONS[i][3] = new ImageIcon(img1); // 中间的图标不为空
        }
        assertEquals("不同行不同列中间有阻隔，应该无法消除", false, llk.isRemovable(1, 2, 2, 5));
    }

    // 不同行不同列中间不相隔图标消除
    @Test
    public void isRemovableDiffentRWSep(){
        ICONSsetup(1, 2, 2, 6, img0, img0);
        assertEquals("不同行不同列中间不相隔的图标应该被消除，但未能消除", true, llk.isRemovable(1, 2, 2, 6));
    }


    @Test
    public void giveActSimple(){
        ICONSsetup(2, 2, 2, 4, img0, img0);
        MatchResult match = new MatchResult(2,2,2,4);
        assertEquals(match,PlayJPanel.giveAct());
    }

    @Test
    public void giveActOneRow(){
        ICONSsetup(2, 2, 2, 5, img0, img0);
        MatchResult match = new MatchResult(2, 2, 2, 5);
        assertEquals(match, PlayJPanel.giveAct());
    }
    
    @Test
    public void giveActOneCol(){
        ICONSsetup(1, 4, 4, 4, img0, img0);
        MatchResult match = new MatchResult(1, 4, 4, 4);
        assertEquals(match, PlayJPanel.giveAct());
    }
    
    @Test
    public void giveActDiff(){
        ICONSsetup(1, 2, 3, 4, img0, img0);
        MatchResult match = new MatchResult(1, 2, 3, 4); // 假设这些图标可以消除
        assertEquals(match, PlayJPanel.giveAct());
    }

    @Test
    public void testGetImgLocationValid() {
        ICONSsetup(1, 1, 1, 2, img0, img1);
        ImageIcon result = llk.getImgLocation(X0 + SIZE * 1 + SIZE / 2, Y0 + SIZE * 1 + SIZE / 2);
        assertNotNull("有效区域中的图片图标应该返回非 null", result);
    }

    @Test
    public void testGetImgLocationBlankArea() {
        ImageIcon result = llk.getImgLocation(X0 + SIZE * 0 + SIZE / 2, Y0 + SIZE * 0 + SIZE / 2);
        assertNull("空白区域时应返回 null", result);
    }

    @Test
    public void testGetImgLocationOutOfBounds() {
        ImageIcon result = llk.getImgLocation(-1, -1);
        assertNull("坐标超出边界时应返回 null", result);
    }

    @Test
    public void testGetImgLocationOnBoundary() {
        ICONSsetup(0, 0, 0, 1, img0, img1);
        ImageIcon result = llk.getImgLocation(X0 + SIZE * 0 + SIZE / 2, Y0 + SIZE * 0 + SIZE / 2);
        assertNotNull("在边界上的图标应该返回非 null", result);
    }

    @Test
    public void testGetImgLocationEmptyArray() {
        // 确保 ICONS 数组为空
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ICONS[i][j] = null;
            }
        }
        ImageIcon result = llk.getImgLocation(X0 + SIZE * 0 + SIZE / 2, Y0 + SIZE * 0 + SIZE / 2);
        assertNull("空 ICONS 数组时应返回 null", result);
    }

    //全图片区域时返回对应的 ImageIcon
    @Test
    public void testGetImageLocationFull(){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ICONS[i][j] = new ImageIcon(img0);
            }
        }
        // 验证每个有效位置的图标
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ImageIcon result = llk.getImgLocation(X0 + SIZE * j + SIZE / 2, Y0 + SIZE * i + SIZE / 2);
                assertNotNull("位置 (" + i + ", " + j + ") 应该返回非 null 的图标", result);
                assertEquals("位置 (" + i + ", " + j + ") 的图标应为 img0", img0, result.getDescription());
            }
        }
    }


    @Test
    public void testUpdateMarksPositiveScore() {
        llk.marks = 50; // 当前分数设为 50
        int addmarks = 20; // 要增加的正分数
        llk.updateMarks(addmarks); // 执行更新分数的方法
        assertEquals("分数应增加正分数", 70, llk.marks); // 验证分数增加
    }
    
    @Test
    public void testUpdateMarksZeroScore() {
        llk.marks = 30; // 当前分数设为 30
        int addmarks = 0; // 要增加的分数为 0
        llk.updateMarks(addmarks); // 执行更新分数的方法
        assertEquals("分数应保持不变", 30, llk.marks); // 验证分数不变
    }
    
    @Test
    public void testUpdateMarksNegativeScore() {
        llk.marks = 30; // 当前分数设为 30
        int addmarks = -15; // 要减少的负分数
        llk.updateMarks(addmarks); // 执行更新分数的方法
        assertEquals("分数应减少负分数", 15, llk.marks); // 验证分数减少
    }
    
    @Test
    public void testUpdateMarksAboveMaxValue() {
        llk.marks = 89; // 当前分数设为 89
        int addmarks = 20; // 要增加的分数
        llk.updateMarks(addmarks); // 执行更新分数的方法
        assertEquals("分数应为原来的值", 89, llk.marks); // 验证分数为最大值
    }
    
    @Test
    public void testUpdateMarksBelowMinValue() {
        llk.marks = 0; // 当前分数设为 0
        int addmarks = -10; // 要减少的负分数
        llk.updateMarks(addmarks); // 执行更新分数的方法
        assertEquals("分数应为最小值", 0, llk.marks); // 验证分数为最小值
    }

    @Test
    public void initPicDataTest(){
        Utils.initPicData();
        Utils.initWireLine();
        Map<ImageIcon, Integer> iconCountMap = new HashMap<>();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                assertNotNull("位置 (" + i + ", " + j + ") 应该有图标", ICONS[i][j]);
                iconCountMap.put(ICONS[i][j], iconCountMap.getOrDefault(ICONS[i][j], 0) + 1);
            }
        }
        // 验证每种图标的数量
        for (Map.Entry<ImageIcon, Integer> entry : iconCountMap.entrySet()) {
            int count = entry.getValue();
            assertEquals("每种图标的数量应为偶数", 0, count % 2);
        }
    }

    @Test
    public void TestIsGameEndLeisurePostive(){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ICONS[i][j] = null;
            }
        }
        assertEquals(true,llk.isGameEnd(ICONS));
    }
    
    @Test
    public void TestIsGameEndTestLeisureNegative() {
        // 设置游戏状态为未结束
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ICONS[i][j] = new ImageIcon(); // 初始化为非空图标
            }
        }
        assertEquals(false, llk.isGameEnd(ICONS)); // 游戏未结束
    }

    @Test
    public void TestIsGameEndTestTimeOverAndFinished(){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ICONS[i][j] = null;
            }
        }
        assertEquals(true,llk.isGameEnd(ICONS,0));
    }

    @Test 
    public void TestIsGameEndTestTimeOverAndNotFinished(){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ICONS[i][j] = new ImageIcon();
            }
        }
        assertEquals(false,llk.isGameEnd(ICONS,100));
    }

    @Test
    public void TestIsGameEndTestTimeEnoughAndFinished() {
        // 设置游戏状态为已完成
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ICONS[i][j] = null; // 所有图标都已消除
            }
        }
        assertEquals(true, llk.isGameEnd(ICONS, 100)); // 游戏应结束，时间充足
    }
    
    @Test
    public void TestIsGameEndTestTimeEnoughAndNotFinished() {
        // 设置游戏状态为未完成
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                ICONS[i][j] = new ImageIcon(); // 初始化为非空图标
            }
        }
        assertEquals(false, llk.isGameEnd(ICONS, 100)); // 游戏未结束，时间充足
    }

    @Test
    public void testTime() throws InterruptedException {
        TimePanel timePanel = new TimePanel(1000); // 初始化为 1000 毫秒
        Thread.sleep(1000);
        double remainingTime = timePanel.getRemainingTime();
        // 使用容忍度来判断
        System.out.println(remainingTime);
        assertTrue("剩余时间应在 0ms 到 100ms 之间", remainingTime >= 0 && remainingTime <= 100);
    }
}
