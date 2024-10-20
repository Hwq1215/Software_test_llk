package Adamin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserAdministratorTest {
    UserAdministrator
            userAdmin;
    @Before
    public void setUp() {
        this.userAdmin = new UserAdministrator();
//        System.out.println("Set up:delete data file");
//        File dataFile =new File(userAdmin.storeFile.getAbsolutePath());
//        if(dataFile.exists()) dataFile.delete();
    }
    @After
    public void cleanUp(){
        System.out.println("Clean up:delete data file");
        File dataFile =new File(userAdmin.storeFile.getAbsolutePath());
        if(dataFile.exists()) dataFile.delete();
    }


    @Test
    public void testLoginSuccess() {
        // 注册一个用户以供测试
        this.userAdmin.resigter("testUser", "testPass123");

        // 测试登录成功
        User user = userAdmin.login("testUser", "testPass123");
        assertNotNull("登录失败，用户应该已经存在", user);
        assertEquals("账户名不匹配", "testUser", user.account);
    }
    @Test
    public void testLoginFail() {

        // 注册一个用户以供测试
        userAdmin.resigter("testUser", "testPass123");

        // 测试使用错误密码登录
        User user = userAdmin.login("testUser", "wrongPass");
        assertNull("登录失败，密码不匹配", user);

        // 测试使用未注册的账号登录
        user = userAdmin.login("nonExistingUser", "testPass123");
        assertNull("登录失败，账号不存在", user);

        user =userAdmin.login("","");
        assertNull("登录失败，输入不能为空",user);

        user =userAdmin.login("","testPass123");
        assertNull("登录失败，账户输入不能为空",user);

        user =userAdmin.login("testUser","");
        assertNull("登录失败，密码输入不能为空",user);
    }


    @Test
    public void testResigterCheck() {

        // 测试账号符合规则的情况
        assertEquals("注册检查应该通过",
                UserAdministrator.ResigterError.No_Error,
                userAdmin.resigterCheck("validUser", "validPass123"));

        // 测试账号长度小于3
        assertEquals("账号太短，注册检查应该失败",
                UserAdministrator.ResigterError.ACCOUNT_ILLEGAL,
                userAdmin.resigterCheck("a","1234567"));
        //测试账号长度等于3
        assertEquals("注册检查应该通过",
                UserAdministrator.ResigterError.No_Error,
                userAdmin.resigterCheck("abc","1234567"));

        //测试账号长度大于3小于13
        assertEquals("注册检查应该通过",
                UserAdministrator.ResigterError.No_Error,
                userAdmin.resigterCheck("abcd","1234567"));
        //测试账号长度等于13
        assertEquals("注册检查应该通过",
                UserAdministrator.ResigterError.No_Error,
                userAdmin.resigterCheck("abcdefghijklm","1234567"));
        // 测试账号包含特殊字符空格
        assertEquals("账号有特殊字符，注册应该失败",
                UserAdministrator.ResigterError.ACCOUNT_ILLEGAL,
                userAdmin.resigterCheck("abc defg","123456"));


        // 测试密码长度小于7
        assertEquals("密码太短，注册检查应该失败",
                UserAdministrator.ResigterError.PASSWORD_ILLEGAL,
                userAdmin.resigterCheck("abcdefg","1234"));
        // 测试密码长度等于7
        assertEquals("账户密码符合要求，注册检查应该成功",
                UserAdministrator.ResigterError.No_Error,
                userAdmin.resigterCheck("abcdefg","1234567"));
        // 测试密码长度大于7小于19
        assertEquals("账户密码符合要求，注册检查应该成功",
                UserAdministrator.ResigterError.No_Error,
                userAdmin.resigterCheck("abcdefg","1122334455667788990"));
        // 测试密码长度大于19
        assertEquals("账户密码过长，注册检查应该失败",
                UserAdministrator.ResigterError.PASSWORD_ILLEGAL,
                userAdmin.resigterCheck("abcdefg","11223344556677889900"));


        // 测试密码包含非法字符
        assertEquals("密码含有非法字符，注册检查应该失败",
                UserAdministrator.ResigterError.PASSWORD_ILLEGAL,
                userAdmin.resigterCheck("abcdefg","123 456"));

        // 测试账户和密码为空
        assertEquals("输入为空，注册检查应该失败",
                UserAdministrator.ResigterError.ACCOUNT_ILLEGAL,
                userAdmin.resigterCheck("", ""));
    }


    @Test
    public void testResigter() {
        // 测试注册成功
        User user = userAdmin.resigter("newUser", "newPass123");
        assertNotNull("注册失败，应该成功注册", user);

        // 测试重复注册
        User user2 = userAdmin.resigter("ExistedUser", "newPass123");
        assertEquals("账户已存在，注册检查应该失败",
                UserAdministrator.ResigterError.ACCOUNT_EXIST,
                userAdmin.resigterCheck("ExistedUser", "anotherPass123"));
    }
    @Test
    public void testUserAdministratorConstructor() {
        UserAdministrator userAdmin2=new UserAdministrator();
        userAdmin2.TData=null;
        // 检查是否成功创建了 UserAdministrator 对象
        assertNotNull("UserAdministrator 对象应该被成功创建", userAdmin2);

        // 检查构造函数是否正确初始化了 users 列表
        ArrayList<User> users = userAdmin2.getUsers();
        assertNotNull("users 列表应该被初始化", users);
        //assertTrue("初始化时，users 列表应该为空", users.isEmpty());

        // 检查 fileName 和 storeFile 是否正确初始化
        String expectedFileName = "UserAdministrator.dat";
        assertEquals("fileName 应该被正确设置", expectedFileName, userAdmin2.fileName);

        //File expectedStoreFile = new File("data", expectedFileName);
        assertTrue("storeFile 应该被正确创建",  userAdmin2.storeFile.exists());
        // 检查 TData 是否正确初始化 todo
        assertNull("TData 应该在没有数据时为 null", userAdmin2.getCurrentData());
    }




}