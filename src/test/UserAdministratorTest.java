package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Adamin.*;

public class UserAdministratorTest {
    private UserAdministrator userAdministrator;

    @Before
    public void setUp() {
        // 初始化UserAdministrator对象
        userAdministrator = new UserAdministrator();
    }

    // 测试登录功能
    @Test
    public void testLoginSuccess() {
        // 设置测试用户
        User testUser = new User("testuser", "password123");
        userAdministrator.register(testUser.account, testUser.password);

        // 测试登录
        User loggedInUser = userAdministrator.login(testUser.account, testUser.password);
        assertNotNull("登录失败，用户应该存在", loggedInUser);
        assertEquals("账户不匹配", testUser.account, loggedInUser.account);
        assertEquals("密码不匹配", testUser.password, loggedInUser.password);
    }

    @Test
    public void testLoginFail() {
        // 尝试登录一个不存在的用户
        User loggedInUser = userAdministrator.login("nonexistent", "wrongpassword");
        assertNull("登录失败，用户不应该存在", loggedInUser);
    }

    // 测试注册功能
    @Test
    public void testRegisterSuccess() {
        // 执行注册操作
        User registeredUser = userAdministrator.register("newuser", "password123");
        
        // 检查注册是否成功
        assertNotNull("注册失败，用户应该成功注册", registeredUser);
        assertEquals("注册账户不匹配", "newuser", registeredUser.account);
        assertEquals("注册密码不匹配", "password123", registeredUser.password);
        
        // 检查是否能通过login登录成功
        User loggedInUser = userAdministrator.login("newuser", "password123");
        assertNotNull("登录失败，应该能成功登录", loggedInUser);
    }

    @Test
    public void testRegisterFailAccountExists() {
        // 设置测试用户并注册
        userAdministrator.register("existingUser", "password123");

        // 再次注册相同账号，应该返回null
        User registeredUser = userAdministrator.register("existingUser", "newpassword123");
        assertNull("注册失败，账户已存在", registeredUser);
    }
}

