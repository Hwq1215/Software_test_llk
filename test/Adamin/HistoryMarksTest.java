package Adamin;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

public class HistoryMarksTest {
    
    private HistoryMarks historyMarks;

    @Before
    public void setUp() {
        // 设置一个测试账户和分数
        String account = "testUser";
        int marks = 100;
        historyMarks = new HistoryMarks(new Date(), account, marks);
    }

    @Test
    public void testHistoryMarksConstructorWithDateAccountMarks() {
        assertNotNull(historyMarks);
        assertNotNull(historyMarks.date);
        assertEquals("testUser", historyMarks.account);
        assertEquals(100, historyMarks.marks);
    }

    @Test
    public void testHistoryMarksConstructorWithDateAccount() {
        HistoryMarks historyMarksWithoutMarks = new HistoryMarks(new Date(), "testUser2");
        assertNotNull(historyMarksWithoutMarks);
        assertNotNull(historyMarksWithoutMarks.date);
        assertEquals("testUser2", historyMarksWithoutMarks.account);
        assertEquals(0, historyMarksWithoutMarks.marks); // 默认分数为0
    }

    @Test
    public void testHistoryMarksConstructorWithAccountMarks() {
        HistoryMarks historyMarksWithDefaultDate = new HistoryMarks("testUser3", 85);
        assertNotNull(historyMarksWithDefaultDate);
        assertNotNull(historyMarksWithDefaultDate.date);
        assertEquals("testUser3", historyMarksWithDefaultDate.account);
        assertEquals(85, historyMarksWithDefaultDate.marks); // 默认分数为0
    }


}
