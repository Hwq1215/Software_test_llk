package Adamin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MarksAdministratorTest {
    private MarksAdministrator marksAdmin;
    @Before
    public void setUp() throws Exception {
        marksAdmin = new MarksAdministrator();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetUsersHistoryMarks_marksIsNull() {
        // 测试marks为null时，返回的历史列表应为空而不是null
        MarksAdministrator.setMarks(null);
        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("user123");
        assertNotNull("当 marks 为 null 时，应返回一个空列表，而不是 null", result);
        assertTrue("当 marks 为 null 时，返回的列表应为空", result.isEmpty());
    }

    @Test
    public void testGetUsersHistoryMarks_marksIsEmptyList() {
        // 测试marks已经初始化，为空列表时
        HashMap<String, ArrayList<HistoryMarks>> marksMap = new HashMap<>();
        marksMap.put("user123", new ArrayList<>());
        MarksAdministrator.setMarks(marksMap);

        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("user123");
        assertNotNull("当用户存在但没有分数时，应返回一个空列表，而不是 null", result);
        assertTrue("当用户存在但没有分数时，返回的列表应为空", result.isEmpty());
    }

    @Test
    public void testGetUsersHistoryMarks_marksHasData() {
        // 测试marks 包含有效数据
        HashMap<String, ArrayList<HistoryMarks>> marksMap = new HashMap<>();
        ArrayList<HistoryMarks> historyMarks = new ArrayList<>();
        historyMarks.add(new HistoryMarks(new Date(), "user123", 50));
        marksMap.put("user123", historyMarks);
        MarksAdministrator.setMarks(marksMap);

        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("user123");
        assertNotNull("当用户存在且有分数时，应返回一个非空列表", result);
        assertEquals("返回的列表中应包含正确数量的记录", 1, result.size());
        assertEquals("返回的分数应与插入的分数匹配", 50, result.get(0).marks);
    }

    @Test
    public void testGetUsersHistoryMarks_userNotExists_emptyString() {
        // 设置 marks 已初始化，但用户为空字符串时不存在
        MarksAdministrator.setMarks(new HashMap<>());

        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("");
        assertNull("当用户不存在（空字符串）时，应返回一个null", result);
    }

    @Test
    public void testGetUsersHistoryMarks_userNotExists_otherUser() {
        // 设置 marks 已初始化，但不存在的用户
        MarksAdministrator.setMarks(new HashMap<>());

        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("user321");
        assertNull("当用户不存在时，应返回null", result);
    }

    @Test
    public void testGetUsersHistoryMarks_sortByMarks_marksIsNull() {
        // 设置 marks 为 null
        MarksAdministrator.setMarks(null);
        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("user123", MarksAdministrator.SortBy.MARKS);
        assertNotNull("当 marks 为 null 时，应返回一个空列表，而不是 null", result);
        assertTrue("当 marks 为 null 时，返回的列表应为空", result.isEmpty());
    }

    @Test
    public void testGetUsersHistoryMarks_sortByMarks_marksIsEmptyList() {
        // 设置 marks 已初始化，但列表为空
        HashMap<String, ArrayList<HistoryMarks>> marksMap = new HashMap<>();
        marksMap.put("user123", new ArrayList<>());
        MarksAdministrator.setMarks(marksMap);

        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("user123", MarksAdministrator.SortBy.MARKS);
        assertNotNull("当用户存在但没有分数时，应返回一个空列表，而不是 null", result);
        assertTrue("当用户存在但没有分数时，返回的列表应为空", result.isEmpty());
    }

    @Test
    public void testGetUsersHistoryMarks_sortByMarks_marksHasData() {
        // 设置 marks 包含有效数据并按分数排序
        HashMap<String, ArrayList<HistoryMarks>> marksMap = new HashMap<>();
        ArrayList<HistoryMarks> historyMarks = new ArrayList<>();
        historyMarks.add(new HistoryMarks(new Date(), "user123", 30));
        historyMarks.add(new HistoryMarks(new Date(), "user123", 50));
        historyMarks.add(new HistoryMarks(new Date(), "user123", 20));
        marksMap.put("user123", historyMarks);
        MarksAdministrator.setMarks(marksMap);

        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("user123", MarksAdministrator.SortBy.MARKS);
        assertNotNull("当用户存在且有分数时，应返回一个非空列表", result);
        assertEquals("返回的列表中应包含正确数量的记录", 3, result.size());

        // 验证返回的列表是否按照分数从高到低排序
        assertEquals("第一条记录的分数应为 50", 50, result.get(0).marks);
        assertEquals("第二条记录的分数应为 30", 30, result.get(1).marks);
        assertEquals("第三条记录的分数应为 20", 20, result.get(2).marks);
    }


    @Test
    public void testGetUsersHistoryMarks_sortByDate_marksIsNull() {
        // 设置 marks 为 null
        MarksAdministrator.setMarks(null);
        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("user123", MarksAdministrator.SortBy.DATE);
        assertNotNull("当 marks 为 null 时，应返回一个空列表，而不是 null", result);
        assertTrue("当 marks 为 null 时，返回的列表应为空", result.isEmpty());
    }

    @Test
    public void testGetUsersHistoryMarks_sortByDate_marksIsEmptyList() {
        // 设置 marks 已初始化，但列表为空
        HashMap<String, ArrayList<HistoryMarks>> marksMap = new HashMap<>();
        marksMap.put("user123", new ArrayList<>());
        MarksAdministrator.setMarks(marksMap);

        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("user123", MarksAdministrator.SortBy.DATE);
        assertNotNull("当用户存在但没有分数时，应返回一个空列表，而不是 null", result);
        assertTrue("当用户存在但没有分数时，返回的列表应为空", result.isEmpty());
    }

    @Test
    public void testGetUsersHistoryMarks_sortByDate_marksHasData() {
        // 设置 marks 包含有效数据并按日期排序
        HashMap<String, ArrayList<HistoryMarks>> marksMap = new HashMap<>();
        ArrayList<HistoryMarks> historyMarks = new ArrayList<>();

        // 创建不同日期的记录
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.OCTOBER, 1); // 2023-10-01
        historyMarks.add(new HistoryMarks(calendar.getTime(), "user123", 30));

        calendar.set(2023, Calendar.OCTOBER, 3); // 2023-10-03
        historyMarks.add(new HistoryMarks(calendar.getTime(), "user123", 50));

        calendar.set(2023, Calendar.SEPTEMBER, 30); // 2023-09-30
        historyMarks.add(new HistoryMarks(calendar.getTime(), "user123", 20));

        marksMap.put("user123", historyMarks);
        MarksAdministrator.setMarks(marksMap);

        ArrayList<HistoryMarks> result = marksAdmin.getUsersHistoryMarks("user123", MarksAdministrator.SortBy.DATE);
        assertNotNull("当用户存在且有分数时，应返回一个非空列表", result);
        assertEquals("返回的列表中应包含正确数量的记录", 3, result.size());

        // 验证返回的列表是否按照日期从新到旧排序
        assertEquals("第一条记录的日期应为 2023-10-03", 2023, result.get(0).date.getYear() + 1900);
        assertEquals("第二条记录的日期应为 2023-10-01", 2023, result.get(1).date.getYear() + 1900);
        assertEquals("第三条记录的日期应为 2023-09-30", 2023, result.get(2).date.getYear() + 1900);
    }

    //-----------------------------------------------
    @Test
    public void testSetUsersHistoryMarks_ValidMarks_init() {
        String account = "user123";
        // 确保用户初始化
        marksAdmin.setUsersHistoryMarks(account, 10);
        // 定义有效的分数值，包括边界值
        int[] validMarks = {0, 1000, 66};

        for (int mark : validMarks) {
            boolean result = marksAdmin.setUsersHistoryMarks(account, mark);
            assertTrue(result);
        }
    }
    @Test
    public void testSetUsersHistoryMarks_InvalidMarks_init() {
        String account = "user123";
        // 确保用户初始化
        marksAdmin.setUsersHistoryMarks(account, 10);
        // 定义无效的分数值，包括溢出
        int[] invalidMarks = {-1, 1001};

        for (int mark : invalidMarks) {
            boolean result = marksAdmin.setUsersHistoryMarks(account, mark);
            assertFalse(result);
        }
    }
    @Test
    public void testSetUsersHistoryMarks_ValidMarks() {
        String account = "user123";
        // 定义有效的分数值，包括边界值
        int[] validMarks = {0, 1000, 66};

        for (int mark : validMarks) {
            boolean result = marksAdmin.setUsersHistoryMarks(account, mark);
            assertTrue(result);
            //确保未初始化
            marksAdmin.clean();
        }

    }
    @Test
    public void testSetUsersHistoryMarks_InvalidMarks() {
        String account = "user123";
        // 定义无效的分数值，包括溢出
        int[] invalidMarks = {-1, 1001};

        for (int mark : invalidMarks) {
            boolean result = marksAdmin.setUsersHistoryMarks(account, mark);
            assertFalse(result);
            //确保未初始化
            marksAdmin.clean();
        }
    }







}